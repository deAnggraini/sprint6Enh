package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.client.ApiResponseWrapper;
import id.co.bca.pakar.be.doc.client.PakarOauthClient;
import id.co.bca.pakar.be.doc.client.PakarWfClient;
import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dao.ArticleContentRepository;
import id.co.bca.pakar.be.doc.dao.ArticleEditRepository;
import id.co.bca.pakar.be.doc.dao.ArticleMyPagesRepository;
import id.co.bca.pakar.be.doc.dao.ArticleRepository;
import id.co.bca.pakar.be.doc.dto.MyPageDto;
import id.co.bca.pakar.be.doc.dto.RequestTaskDto;
import id.co.bca.pakar.be.doc.dto.SearchMyPageDto;
import id.co.bca.pakar.be.doc.dto.TaskDto;
import id.co.bca.pakar.be.doc.exception.AccesDeniedDeleteContentException;
import id.co.bca.pakar.be.doc.exception.MinValuePageNumberException;
import id.co.bca.pakar.be.doc.model.Article;
import id.co.bca.pakar.be.doc.model.ArticleEdit;
import id.co.bca.pakar.be.doc.model.ArticleState;
import id.co.bca.pakar.be.doc.service.ArticleContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static id.co.bca.pakar.be.doc.common.Constant.Headers.BEARER;
import static id.co.bca.pakar.be.doc.common.Constant.Roles.ROLE_READER;

@Service
public class ArticleContentServiceImpl implements ArticleContentService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PakarWfClient pakarWfClient;

    @Autowired
    private PakarOauthClient pakarOauthClient;

    @Autowired
    private ArticleMyPagesRepository articleMyPagesRepository;

    @Autowired
    private ArticleEditRepository articleEditRepository;

    @Autowired
    private ArticleRepository articleRepository;
    /**
     * @param searchDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = {Exception.class, MinValuePageNumberException.class})
    public Page<MyPageDto> searchContent(SearchMyPageDto searchDto) throws Exception {
        try {
            logger.info("search my page dto");
            ResponseEntity<ApiResponseWrapper.RestResponse<List<String>>> restResponse = pakarOauthClient.getRoles(BEARER + searchDto.getToken(), searchDto.getUsername());
            List<String> roles = restResponse.getBody().getData();
            String role = roles.get(0);

            Page<Article> searchResultPage = null;
            if (searchDto.getPage() == null) {
                searchDto.setPage(0L);
            }

            if (searchDto.getPage() == 0) {
                searchDto.setPage(0L);
            }

            int pageNum = searchDto.getPage().intValue() - 1;
            if (pageNum < 0)
                throw new MinValuePageNumberException("page number smaller than 0");
            String reqSortColumnName = searchDto.getSorting().getColumn();
            searchDto.getSorting().setColumn(new TodoMapperMyPages().convertColumnNameforSort(reqSortColumnName));
            Sort sort = searchDto.getSorting().getSort().equals("asc") ? Sort.by(searchDto.getSorting().getColumn()).ascending() : Sort.by(searchDto.getSorting().getColumn()).descending();
            Pageable pageable = PageRequest.of(pageNum, searchDto.getSize().intValue(), sort);

            if (searchDto.getType().equals(Constant.JenisHalaman.All) || searchDto.getType().equals(Constant.JenisHalaman.Artikel)) {
                if(role.equals(Constant.Roles.ROLE_ADMIN)) {
                    searchResultPage = articleRepository.findContentArticleForAdmin(searchDto.getKeyword(), pageable);
                } else {
                    searchResultPage = articleRepository.findContentArticle(searchDto.getKeyword(), pageable);
                }
            } else {
                searchResultPage = null;
            }

            return new TodoMapperMyPages().mapEntityPageIntoDTOPage(pageable, searchResultPage);
        } catch (MinValuePageNumberException e) {
            logger.error("exception", e);
            throw new MinValuePageNumberException("exception", e);
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("exception", e);
        }
    }

    /**
     * mapper for contents
     */
    private class TodoMapperMyPages {
        public List<MyPageDto> mapEntitiesIntoDTOs(Iterable<Article> entities) {
            List<MyPageDto> dtos = new ArrayList<>();
            entities.forEach(e -> dtos.add(mapEntityIntoDTO(e)));
            return dtos;
        }

        public MyPageDto mapEntityIntoDTO(Article entity) {
            MyPageDto dto = new MyPageDto();
            String locTemp = articleMyPagesRepository.findLocation(entity.getStructure().getId());
            List<ArticleEdit> articleEdits = articleEditRepository.findArticleInEditingStatus(entity.getId());

            dto.setId(entity.getId());
            dto.setType(Constant.JenisHalaman.Artikel);
            dto.setTitle(entity.getJudulArticle());
            dto.setLocation(locTemp);
            dto.setEffectiveDate(null);
            dto.setModifiedBy(entity.getFullNameModifier());
            int i = 0;
            StringBuffer currentEdit = new StringBuffer();
            for(ArticleEdit articleEdit : articleEdits) {
                currentEdit.append(articleEdit.getEditorName());
                if(i < articleEdits.size() - 1)
                    currentEdit.append(",");
                i++;
            }
            dto.setCurrentBy(currentEdit.toString());
            return dto;
        }

        public Page<MyPageDto> mapEntityPageIntoDTOPage(Pageable pageRequest, Page<Article> source) {
            List<MyPageDto> dtos = mapEntitiesIntoDTOs(source.getContent());
            return new PageImpl<>(dtos, pageRequest, source.getTotalElements());
        }

        public String convertColumnNameforSort(String reqColumn) {
            if (reqColumn.equals("title")) {
                return "judulArticle";
            } else if (reqColumn.equals("modified_by")) {
                return "fullNameModifier";
            } else if (reqColumn.equals("location")) {
                return "structure.location_text";
            }
            return "";
        }
    }
}
