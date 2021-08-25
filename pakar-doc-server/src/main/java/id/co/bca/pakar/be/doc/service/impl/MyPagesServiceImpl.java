package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.client.ApiResponseWrapper;
import id.co.bca.pakar.be.doc.client.PakarOauthClient;
import id.co.bca.pakar.be.doc.client.PakarWfClient;
import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dao.ArticleEditRepository;
import id.co.bca.pakar.be.doc.dao.ArticleStateRepository;
import id.co.bca.pakar.be.doc.dao.MyPagesRepository;
import id.co.bca.pakar.be.doc.dto.MyPageDto;
import id.co.bca.pakar.be.doc.dto.RequestTaskDto;
import id.co.bca.pakar.be.doc.dto.SearchMyPageDto;
import id.co.bca.pakar.be.doc.dto.TaskDto;
import id.co.bca.pakar.be.doc.exception.MinValuePageNumberException;
import id.co.bca.pakar.be.doc.model.ArticleEdit;
import id.co.bca.pakar.be.doc.model.MyPages;
import id.co.bca.pakar.be.doc.service.MyPagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static id.co.bca.pakar.be.doc.common.Constant.Headers.BEARER;

@Service
public class MyPagesServiceImpl implements MyPagesService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ArticleEditRepository articleEditRepository;

    @Autowired
    private ArticleStateRepository articleStateRepository;

    @Autowired
    private MyPagesRepository myPagesRepository;

    @Autowired
    private PakarOauthClient pakarOauthClient;

    @Autowired
    private PakarWfClient pakarWfClient;

    @Value("${upload.path.article}")
    private String pathCategory;
    @Value("${upload.path.base}")
    private String basePath;

    /**
     * @param searchDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = {Exception.class, MinValuePageNumberException.class})
    public Page<MyPageDto> searchMyPages(SearchMyPageDto searchDto) throws Exception {
        try {
            logger.info("search my page dto");
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
            searchDto.getSorting().setColumn(new TodoMapperMyPages().convertColumnNameforSort(reqSortColumnName));
            Sort sort = searchDto.getSorting().getSort().equals("asc") ? Sort.by(searchDto.getSorting().getColumn()).ascending() : Sort.by(searchDto.getSorting().getColumn()).descending();
            Pageable pageable = PageRequest.of(pageNum, searchDto.getSize().intValue(), sort);
            RequestTaskDto requestTaskDto = new RequestTaskDto();
            requestTaskDto.setAssigne(searchDto.getUsername());
            requestTaskDto.setPic(searchDto.getUsername());
            requestTaskDto.setState(searchDto.getState());
            ResponseEntity<ApiResponseWrapper.RestResponse<List<TaskDto>>> restResponse = pakarWfClient
                    .getTasksWithState(BEARER + searchDto.getToken(), searchDto.getUsername(), requestTaskDto);
            List<Long> ids = new ArrayList<>();
            for (TaskDto task : restResponse.getBody().getData()) {
                ids.add(task.getArticleId());
                logger.debug("user {} has article id {} in state {}", new Object[]{searchDto.getUsername(), task.getArticleId(), searchDto.getState()});
            }

            if (searchDto.getType().equals(Constant.JenisHalaman.All) || searchDto.getType().equals(Constant.JenisHalaman.Artikel)) {
                if (searchDto.getState().equalsIgnoreCase(Constant.ArticleWfState.DRAFT))
                    searchResultPage = myPagesRepository.findMyPagesDratfArticle(ids, searchDto.getKeyword(), searchDto.getUsername(), searchDto.getState(), pageable);
                else if (searchDto.getState().equalsIgnoreCase(Constant.ArticleWfState.PENDING))
                    searchResultPage = myPagesRepository.findMyPagesPendingArticle(ids, searchDto.getKeyword(), searchDto.getUsername(), searchDto.getState(), pageable);
                else if (searchDto.getState().equalsIgnoreCase(Constant.ArticleWfState.PUBLISHED)) {
                    searchResultPage = myPagesRepository.findMyPagesPublishedArticle(ids, searchDto.getKeyword(), searchDto.getUsername(), searchDto.getState(), pageable);
                }
            } else {
                return new TodoMapperMyPages().emptypage(pageable);
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
     * mapper for my page
     */
    private class TodoMapperMyPages {
        public List<MyPageDto> mapEntitiesIntoDTOs(Iterable<MyPages> entities) {
            List<MyPageDto> dtos = new ArrayList<>();
            entities.forEach(e -> dtos.add(mapEntityIntoDTO(e)));
            return dtos;
        }

        public MyPageDto mapEntityIntoDTO(MyPages entity) {
            MyPageDto dto = new MyPageDto();
            List<ArticleEdit> articleEdits = articleEditRepository.findArticleInEditingStatus(entity.getId());

            dto.setId(entity.getId());
            dto.setTitle(entity.getJudulArticle());
            dto.setIsNew(entity.getNewArticle());
            dto.setState(entity.getArticleState());
            dto.setModifiedBy(entity.getFullNameModifier());
            dto.setModifiedDate(entity.getModifyDate());
            dto.setType(Constant.JenisHalaman.Artikel);
            dto.setLocation(entity.getLocation());
            int i = 0;
            StringBuffer currentEdit = new StringBuffer();
            for (ArticleEdit articleEdit : articleEdits) {
                currentEdit.append(articleEdit.getEditorName());
                if (i < articleEdits.size() - 1)
                    currentEdit.append(",");
                i++;
            }
            dto.setCurrentBy(currentEdit.toString());
            dto.setSendTo(entity.getFullNameReceiver());

            if(entity.getSenderState() != null) {
                if (entity.getSenderState().equalsIgnoreCase(Constant.ArticleWfState.PUBLISHED)) {
                    dto.setApproved_by(entity.getFullNameReceiver());
//                dto.setApprovedDate(entity.getModifyDate());
//                dto.setEffectiveDate();
                }
            }

            return dto;
        }

        public Page<MyPageDto> mapEntityPageIntoDTOPage(Pageable pageRequest, Page<MyPages> source) {
            List<MyPageDto> dtos = mapEntitiesIntoDTOs(source.getContent());
            return new PageImpl<>(dtos, pageRequest, source.getTotalElements());
        }

        public Page<MyPageDto> emptypage(Pageable pageRequest) {
            List<MyPageDto> dtos = new ArrayList<>();
            return new PageImpl<>(dtos, pageRequest, 0);
        }

        public String convertColumnNameforSort(String reqColumn) {
            if (reqColumn.equals("title")) {
                return "judulArticle";
            } else if (reqColumn.equals("modified_date")) {
                return "modifyDate";
            } else if (reqColumn.equals("modified_by")) {
                return "fullNameModifier";
            } else if (reqColumn.equals("location")) {
                return "location";
            }
            return "judulArticle";
        }
    }
}