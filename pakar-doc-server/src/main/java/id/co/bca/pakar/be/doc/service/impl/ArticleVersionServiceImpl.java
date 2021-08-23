package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dao.ArticleVersionRepository;
import id.co.bca.pakar.be.doc.dto.ArticleDto;
import id.co.bca.pakar.be.doc.dto.SearchPublishedArticleDto;
import id.co.bca.pakar.be.doc.exception.MinValuePageNumberException;
import id.co.bca.pakar.be.doc.exception.SavingArticleVersionException;
import id.co.bca.pakar.be.doc.model.Article;
import id.co.bca.pakar.be.doc.model.ArticleContent;
import id.co.bca.pakar.be.doc.model.ArticleContentVersion;
import id.co.bca.pakar.be.doc.model.ArticleVersion;
import id.co.bca.pakar.be.doc.service.ArticleVersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 */
@Service
public class ArticleVersionServiceImpl implements ArticleVersionService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ArticleVersionRepository articleVersionRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class, SavingArticleVersionException.class})
    public ArticleVersion saveArticle(Article article) throws Exception {
        try {
            logger.info("save to article version");
            ArticleVersion av = new ArticleVersionHelper().populateArticleVersion(article);
            List<ArticleContentVersion> articleContentVersions = new ArticleVersionHelper().populateArticleContentVersion(article.getArticleContents(), av);
            av.setArticleContents(articleContentVersions);
            av = articleVersionRepository.save(av);
            if (av == null) {
                logger.error("could not save article version");
                throw new SavingArticleVersionException("could not save article version");
            }
            return av;
        } catch (SavingArticleVersionException e) {
            logger.error("exception", e);
            throw new SavingArticleVersionException("", e);
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception(e);
        }
    }

    /**
     * search published article from article version
     * @param searchDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchPublishedArticle(SearchPublishedArticleDto searchDto) throws Exception {
        try {
            logger.info("search published article ");
            Page<ArticleVersion> searchResultPage = null;
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
            logger.debug("column name for approved date {}", reqSortColumnName);
            searchDto.getSorting().setColumn(new ArticleVersionHelper().convertColumnNameforSort(reqSortColumnName));
            Sort sort = searchDto.getSorting().getSort().equals("asc") ? Sort.by(searchDto.getSorting().getColumn()).ascending() : Sort.by(searchDto.getSorting().getColumn()).descending();
            Pageable pageable = PageRequest.of(pageNum, searchDto.getSize().intValue(), sort);
            if(!searchDto.getIsLatest().booleanValue())
                searchResultPage = articleVersionRepository.findPublishedArticles(searchDto.getStructureId(), searchDto.getKeyword(), pageable);
            else {
                logger.info("get latests published article ");
                searchResultPage = articleVersionRepository.findPublishedArticles(pageable);
            }
            return new ArticleVersionHelper().mapEntityPageIntoDTOPage(pageable, searchResultPage);
        } catch (MinValuePageNumberException e) {
            logger.error("exception", e);
            throw new MinValuePageNumberException("exception", e);
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("exception", e);
        }
    }

    /**
     * helper class for article version
     */
    private class ArticleVersionHelper {
        ArticleVersion populateArticleVersion(Article article) {
            logger.info("populate article to article version");
            ArticleVersion articleVersion = new ArticleVersion();
            articleVersion.setArticleId(article.getId());
            articleVersion.setCreatedBy(article.getModifyBy());
            articleVersion.setJudulArticle(article.getJudulArticle());
            articleVersion.setNewArticle(articleVersion.getNewArticle());
            articleVersion.setFullNameModifier(article.getFullNameModifier());
            articleVersion.setArticleState(article.getArticleState());
            articleVersion.setArticleUsedBy(article.getArticleUsedBy());
            articleVersion.setShortDescription(article.getShortDescription());
            articleVersion.setUseEmptyTemplate(article.getUseEmptyTemplate());
            articleVersion.setArticleTemplate(article.getArticleTemplate());
            articleVersion.setStructure(article.getStructure().getId());
            articleVersion.setVideoLink(article.getVideoLink());
            articleVersion.setReleaseVersion(UUID.randomUUID().toString());
            articleVersion.setTimeStampVersion(new Date());
            return articleVersion;
        }

        /**
         * populate list of article content
         *
         * @param contents
         * @return
         */
        List<ArticleContentVersion> populateArticleContentVersion(List<ArticleContent> contents, ArticleVersion articleVersion) {
            logger.info("populate article content to article content version");
            List<ArticleContentVersion> articleContentVersions = new ArrayList<>();
            contents.forEach(e -> {
                ArticleContentVersion articleContentVersion = new ArticleContentVersion();
                articleContentVersion.setId(e.getId());
                articleContentVersion.setArticleVersion(articleVersion);
                articleContentVersion.setCreatedBy(e.getCreatedBy());
                articleContentVersion.setCreatedDate(e.getCreatedDate());
                articleContentVersion.setModifyBy(e.getModifyBy());
                articleContentVersion.setModifyDate(e.getModifyDate());
                articleContentVersion.setLevel(e.getLevel());
                articleContentVersion.setSort(e.getSort());
                articleContentVersion.setParent(e.getParent());
                articleContentVersion.setName(e.getName());
                articleContentVersion.setDescription(e.getDescription());
                articleContentVersion.setTopicCaption(e.getTopicCaption());
                articleContentVersion.setTopicContent(e.getTopicContent());

                articleContentVersions.add(articleContentVersion);
            });

            return articleContentVersions;
        }

        public List<ArticleDto> mapEntitiesIntoDTOs(Iterable<ArticleVersion> entities) {
            List<ArticleDto> dtos = new ArrayList<>();
            entities.forEach(e -> dtos.add(mapEntityIntoDTO(e)));
            return dtos;
        }

        public ArticleDto mapEntityIntoDTO(ArticleVersion entity) {
            ArticleDto dto = new ArticleDto();
            dto.setId(entity.getArticleId());
            dto.setTitle(entity.getJudulArticle());
            dto.setShortDescription(entity.getShortDescription());
            dto.setVideoLink(entity.getVideoLink());
            dto.setStructureId(entity.getStructure());
            //dto.setImage(entity.getArt);
            dto.setPublished(entity.getArticleState().equalsIgnoreCase(Constant.ArticleWfState.PUBLISHED) ? Boolean.TRUE : Boolean.FALSE);
            return dto;
        }

        public Page<ArticleDto> mapEntityPageIntoDTOPage(Pageable pageRequest, Page<ArticleVersion> source) {
            List<ArticleDto> dtos = mapEntitiesIntoDTOs(source.getContent());
            return new PageImpl<>(dtos, pageRequest, source.getTotalElements());
        }

        public Page<ArticleDto> emptypage(Pageable pageRequest) {
            List<ArticleDto> dtos = new ArrayList<>();
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
            } else if (reqColumn.equals("approved_date")) {
                return "approvedDate";
            }
            return "judulArticle";
        }
    }
}
