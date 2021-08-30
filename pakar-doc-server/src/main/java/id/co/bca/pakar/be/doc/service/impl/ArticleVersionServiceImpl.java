package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.dao.ArticleContentVersionRepository;
import id.co.bca.pakar.be.doc.dao.ArticleRepository;
import id.co.bca.pakar.be.doc.dao.ArticleVersionRepository;
import id.co.bca.pakar.be.doc.dao.StructureRepository;
import id.co.bca.pakar.be.doc.dto.ArticleDto;
import id.co.bca.pakar.be.doc.dto.SearchPublishedArticleDto;
import id.co.bca.pakar.be.doc.exception.MinValuePageNumberException;
import id.co.bca.pakar.be.doc.exception.SavingArticleVersionException;
import id.co.bca.pakar.be.doc.model.*;
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

/**
 *
 */
@Service
public class ArticleVersionServiceImpl implements ArticleVersionService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ArticleVersionRepository articleVersionRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleContentVersionRepository articleContentVersionRepository;

    @Autowired
    private StructureRepository structureRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class, SavingArticleVersionException.class})
    public ArticleVersion saveArticle(Article article, Boolean isReleased) throws Exception {
        try {
            logger.info("save to article version");
            ArticleVersion av = new ArticleVersionHelper().populateArticleVersion(article, isReleased);
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
     *
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
            if (!searchDto.getIsLatest().booleanValue())
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
     * reload article version to article
     *
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public Article reloadPublishedArticleVersion(String judulArticle, String copier, Article destination) throws Exception {
        try {
            logger.info("loading article {} from article version by {}", judulArticle, copier);
            ArticleVersion version = articleVersionRepository.findLastPublished(judulArticle);
            destination = new ArticleVersionHelper().copyToArticle(version, destination, copier);
            return destination;
        } catch (Exception e) {
            logger.error("failed to reload");
            return null;
        }
    }

    /**
     * helper class for article version
     */
    private class ArticleVersionHelper {
        ArticleVersion populateArticleVersion(Article article, Boolean isReleased) {
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

            if (isReleased.booleanValue()) {
                // get latest published version
                ArticleVersion lastPublished = articleVersionRepository.findLastPublished(article.getJudulArticle());
                if (lastPublished != null)
                    articleVersion.setPublishedVersion(lastPublished.getPublishedVersion() + 1);
            }

            if (!isReleased.booleanValue())
                articleVersion.setTimeStampVersion(new Date());
            articleVersion.setUsername(article.getModifyBy());
            articleVersion.setPublished(article.getPublished());
            articleVersion.setIsAdd(article.getIsAdd());
            articleVersion.setIsClone(article.getIsClone());
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
                articleContentVersion.setArticleId(e.getArticle().getId());
                articleContentVersion.setOriginArticleContentId(e.getId());
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
                articleContentVersion.setArticleVersion(articleVersion);
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
            dto.setIsAdd(entity.getIsAdd());
            dto.setNew(entity.getNewArticle());
            //dto.setImage(entity.getArt);
            dto.setPublished(entity.getPublished());
            dto.setIsClone(entity.getIsClone());
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

        /**
         *
         * @param source
         * @param destination
         * @param copier
         * @return
         */
        public Article copyToArticle(ArticleVersion source, Article destination, String copier) {
            /*
            if article not yet created then create new article from published version,
            if article ever saved or save and send than update article from article version with time stamp
             */
            if (destination == null) {
                destination.setCreatedBy(copier);
                destination.setCreatedDate(new Date());
                destination.setModifyBy(copier);
                destination.setModifyDate(new Date());
                destination.setJudulArticle(source.getJudulArticle());
                destination.setShortDescription(source.getShortDescription());
                destination.setVideoLink(source.getVideoLink());
                destination.setStructure(structureRepository.findStructure(source.getStructure()));
                //TODO dto.setImage(destination.getArt);
                destination.setUseEmptyTemplate(source.getUseEmptyTemplate());
                destination.setArticleState(source.getArticleState());
                destination.setFullNameModifier(source.getFullNameModifier());
                destination.setPublished(source.getPublished());
                destination.setIsAdd(source.getIsAdd());
                destination.setNewArticle(source.getNewArticle());
                destination.setIsClone(source.getIsClone());

                // copy content version to content
                // TODO content version
//                List<ArticleContentVersion> publishedContentVersion = articleContentVersionRepository.
//                List<ArticleContent> articleContents = new ArticleVersionHelper().populateArticleContentVersion(article.getArticleContents(), av);
//
//                articleContents.forEach(e -> {
//                    ArticleContentClone clone = new ArticleContentClone();
//                    clone.setId(e.getId());
//                    clone.setVersion(e.getVersion());
//                    clone.setArticle(article);
//                    clone.setDescription(e.getDescription());
//                    clone.setLevel(e.getLevel());
//                    clone.setName(e.getName());
//                    clone.setParent(e.getParent());
//                    clone.setSort(e.getSort());
//                    clone.setTopicCaption(e.getTopicCaption());
//                    clone.setTopicContent(e.getTopicContent());
//                    clone.setCreatedBy(e.getCreatedBy());
//                    clone.setCreatedDate(e.getCreatedDate());
//                    clone.setDeleted(e.getDeleted());
//                    clone.setModifyBy(e.getModifyBy());
//                    clone.setModifyDate(e.getModifyDate());
//                    articleContentCloneRepository.save(clone);
//                });
                // save to article
                articleRepository.save(destination);
            } else {
                destination.setCreatedBy(copier);
                destination.setCreatedDate(new Date());
                destination.setModifyBy(copier);
                destination.setModifyDate(new Date());
                destination.setJudulArticle(source.getJudulArticle());
                destination.setShortDescription(source.getShortDescription());
                destination.setVideoLink(source.getVideoLink());
                destination.setStructure(structureRepository.findStructure(source.getStructure()));
                //TODO dto.setImage(destination.getArt);
                destination.setUseEmptyTemplate(source.getUseEmptyTemplate());
                destination.setArticleState(source.getArticleState());
                destination.setFullNameModifier(source.getFullNameModifier());
                destination.setPublished(source.getPublished());
                destination.setIsAdd(source.getIsAdd());
                destination.setNewArticle(source.getNewArticle());
            }
            return destination;
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
