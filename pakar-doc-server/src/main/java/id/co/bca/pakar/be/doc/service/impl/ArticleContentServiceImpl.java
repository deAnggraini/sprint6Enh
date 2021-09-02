package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.client.ApiClient;
import id.co.bca.pakar.be.doc.client.PakarOauthClient;
import id.co.bca.pakar.be.doc.client.PakarWfClient;
import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dao.ArticleEditRepository;
import id.co.bca.pakar.be.doc.dao.ArticleMyPagesRepository;
import id.co.bca.pakar.be.doc.dao.ArticleRepository;
import id.co.bca.pakar.be.doc.dao.MyPagesRepository;
import id.co.bca.pakar.be.doc.dto.MyPageDto;
import id.co.bca.pakar.be.doc.dto.SearchMyPageDto;
import id.co.bca.pakar.be.doc.exception.AccesDeniedViewContentsException;
import id.co.bca.pakar.be.doc.exception.MinValuePageNumberException;
import id.co.bca.pakar.be.doc.model.ArticleEdit;
import id.co.bca.pakar.be.doc.model.MyPages;
import id.co.bca.pakar.be.doc.service.ArticleContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static id.co.bca.pakar.be.doc.common.Constant.Roles.ROLE_ADMIN;
import static id.co.bca.pakar.be.doc.common.Constant.Roles.ROLE_READER;

@Service
public class ArticleContentServiceImpl implements ArticleContentService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PakarWfClient pakarWfClient;

    @Autowired
    private PakarOauthClient pakarOauthClient;

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private ArticleMyPagesRepository articleMyPagesRepository;

    @Autowired
    private ArticleEditRepository articleEditRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MyPagesRepository myPagesRepository;

    /**
     * @param searchDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = {Exception.class, MinValuePageNumberException.class})
    public Page<MyPageDto> searchContent(SearchMyPageDto searchDto) throws Exception {
        try {
            logger.info("search Content dto");
            String role = apiClient.getRoles(searchDto.getUsername(), searchDto.getToken());
            if (role.equals(ROLE_READER)) {
                logger.info("role {} has no authorize to see contents", role);
                throw new AccesDeniedViewContentsException("role " + role + " has no authorize to see contents");
            }

            Page<MyPages> searchResultPage = null;
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
            logger.debug("sort column {}", searchDto.getSorting().getColumn());
            Sort sort = null;
            if(reqSortColumnName.equalsIgnoreCase("effective_date")) {
                searchDto.getSorting().setColumn(new TodoMapperMyPages().convertColumnNameforSort(reqSortColumnName));
                sort = searchDto.getSorting().getSort().equals("asc") ? Sort.by(searchDto.getSorting().getColumn()).descending() : Sort.by(searchDto.getSorting().getColumn()).ascending();
            } else {
                searchDto.getSorting().setColumn(new TodoMapperMyPages().convertColumnNameforSort(reqSortColumnName));
                sort = searchDto.getSorting().getSort().equals("asc") ? Sort.by(searchDto.getSorting().getColumn()).ascending() : Sort.by(searchDto.getSorting().getColumn()).descending();
            }
            Pageable pageable = PageRequest.of(pageNum, searchDto.getSize().intValue(), sort);

            if (searchDto.getType().equals(Constant.DocumentType.All)
                    || searchDto.getType().equals(Constant.DocumentType.Artikel)) {
                if (role.equals(ROLE_ADMIN)) {
                    searchResultPage = myPagesRepository.findContentArticleForAdmin(searchDto.getKeyword(), pageable);
                } else if (role.equals(Constant.Roles.ROLE_EDITOR) || role.equals(Constant.Roles.ROLE_PUBLISHER)) {
                    searchResultPage = myPagesRepository.findContentArticle(searchDto.getKeyword(), pageable);
                }
            } else {
//                searchResultPage = articleRepository.findContentExceptArticle(searchDto.getKeyword(), pageable);
                return new TodoMapperMyPages().emptypage(pageable);
            }

            return new TodoMapperMyPages().mapEntityPageIntoDTOPage(pageable, searchResultPage);
        } catch (AccesDeniedViewContentsException e) {
            logger.error("exception", e);
            throw new AccesDeniedViewContentsException("exception", e);
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
        public List<MyPageDto> mapEntitiesIntoDTOs(Iterable<MyPages> entities) {
            List<MyPageDto> dtos = new ArrayList<>();
            entities.forEach(e -> dtos.add(mapEntityIntoDTO(e)));
            return dtos;
        }

        public Page<MyPageDto> emptypage(Pageable pageRequest) {
            List<MyPageDto> dtos = new ArrayList<>();
            return new PageImpl<>(dtos, pageRequest, 0);
        }

        public MyPageDto mapEntityIntoDTO(MyPages entity) {
            MyPageDto dto = new MyPageDto();
            List<ArticleEdit> articleEdits = articleEditRepository.findArticleInEditingStatus(entity.getId());

            dto.setId(entity.getId());
            dto.setType(Constant.JenisHalaman.Artikel);
            dto.setTitle(entity.getJudulArticle());
            dto.setLocation(entity.getLocation());
            dto.setEffectiveDate(null);
            dto.setModifiedBy(entity.getFullNameModifier());
            dto.setModifiedDate(entity.getModifyDate());
            int i = 0;
            StringBuffer currentEdit = new StringBuffer();
            for (ArticleEdit articleEdit : articleEdits) {
                currentEdit.append(articleEdit.getEditorName());
                if (i < articleEdits.size() - 1)
                    currentEdit.append(",");
                i++;
            }
            dto.setCurrentBy(currentEdit.toString());
            return dto;
        }

        public Page<MyPageDto> mapEntityPageIntoDTOPage(Pageable pageRequest, Page<MyPages> source) {
            List<MyPageDto> dtos = mapEntitiesIntoDTOs(source.getContent());
            return new PageImpl<>(dtos, pageRequest, source.getTotalElements());
        }

        public String convertColumnNameforSort(String reqColumn) {
            if (reqColumn.equals("title")) {
                return "judulArticle";
            } else if (reqColumn.equals("modified_by")) {
                return "fullNameModifier";
            } else if (reqColumn.equals("location")) {
                return "location";
            } else if (reqColumn.equals("modified_date")) {
                return "modifyDate";
            } else if (reqColumn.equals("effective_date")) { // dari column table belum ada field effective date
                return "effectiveDate";
            }
            return "judulArticle";
        }
    }
}
