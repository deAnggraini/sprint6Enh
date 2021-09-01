package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.client.ApiResponseWrapper;
import id.co.bca.pakar.be.doc.client.PakarOauthClient;
import id.co.bca.pakar.be.doc.client.PakarWfClient;
import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dao.*;
import id.co.bca.pakar.be.doc.dto.*;
import id.co.bca.pakar.be.doc.dto.auth.UserProfileDto;
import id.co.bca.pakar.be.doc.dto.auth.UserWrapperDto;
import id.co.bca.pakar.be.doc.exception.*;
import id.co.bca.pakar.be.doc.model.*;
import id.co.bca.pakar.be.doc.service.ArticleCloneService;
import id.co.bca.pakar.be.doc.service.ArticleService;
import id.co.bca.pakar.be.doc.service.ArticleVersionService;
import id.co.bca.pakar.be.doc.util.FileUploadUtil;
import id.co.bca.pakar.be.doc.util.TreeArticleContents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static id.co.bca.pakar.be.doc.common.Constant.ArticleWfState.NEW;
import static id.co.bca.pakar.be.doc.common.Constant.ArticleWfState.PUBLISHED;
import static id.co.bca.pakar.be.doc.common.Constant.Headers.BEARER;
import static id.co.bca.pakar.be.doc.common.Constant.Roles.ROLE_ADMIN;
import static id.co.bca.pakar.be.doc.common.Constant.Roles.ROLE_READER;
import static id.co.bca.pakar.be.doc.common.Constant.Workflow.*;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageSource messageSource;

    @Value("${spring.article.param-tag:[]}")
    private String paramtTag;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleFaqRepository articleFaqRepository;

    @Autowired
    private ArticleTemplateRepository articleTemplateRepository;

    @Autowired
    private ArticleTemplateStructureRepository articleTemplateStructureRepository;

    @Autowired
    private ArticleTemplateContentRepository articleTemplateContentRepository;

    @Autowired
    private ArticleContentRepository articleContentRepository;

    @Autowired
    private SuggestionArticleRepository suggestionArticleRepository;

    @Autowired
    private StructureRepository structureRepository;

    @Autowired
    private SkReffRepository skReffRepository;

    @Autowired
    private ArticleImageRepository articleImageRepository;

    @Autowired
    private ArticleRelatedRepository articleRelatedRepository;

    @Autowired
    private ArticleSkReffRepository articleSkReffRepository;

    @Autowired
    private RelatedArticleRepository relatedArticleRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ArticleMyPagesRepository articleMyPagesRepository;

    @Autowired
    private ArticleEditRepository articleEditRepository;

    @Autowired
    private ArticleStateRepository articleStateRepository;

    @Autowired
    private ArticleNotificationRepository articleNotificationRepository;

    @Autowired
    private ArticleVersionService articleVersionService;

    @Autowired
    private ArticleCloneService articleCloneService;

    @Autowired
    private ArticleContentCloneRepository articleContentCloneRepository;

    @Autowired
    private PakarOauthClient pakarOauthClient;

    @Autowired
    private PakarWfClient pakarWfClient;

    @Autowired
    private ArticleVersionRepository articleVersionRepository;

    @Value("${upload.path.article}")
    private String pathCategory;
    @Value("${upload.path.base}")
    private String basePath;

    /**
     * @param title
     * @return
     */
    @Override
    @Transactional
    public Boolean existArticle(String title, Long articleId) {
        try {
            logger.info("verify existence of article title ---> {}", title);
            Boolean exist = articleRepository.existByArticleTitle(title, articleId);
            logger.info("title {} {}", title, exist.booleanValue() ? "exist in database" : "not exist in database");
            return exist;
        } catch (Exception e) {
            logger.error("exception", e);
            return Boolean.FALSE;
        }
    }

    /**
     * @param generateArticleDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class
            , NotFoundArticleTemplateException.class
            , DuplicateTitleException.class})
    public ArticleDto generateArticle(GenerateArticleDto generateArticleDto) throws Exception {
        try {
            logger.info("generate article process");
            ArticleDto articleDto = new ArticleDto();
            ArticleTemplateStructure articleTemplateStructure = articleTemplateStructureRepository.findArticleTemplates(generateArticleDto.getTemplateId(), generateArticleDto.getStructureId());
            ArticleTemplate template = null;
            if (articleTemplateStructure == null) {
                Optional<ArticleTemplate> templateOpt = articleTemplateRepository.findById(generateArticleDto.getTemplateId());
                if (templateOpt.isEmpty()) {
                    logger.info("data not found template");
                    throw new NotFoundArticleTemplateException("article template not found");
                }
                template = templateOpt.get();
            } else
                template = articleTemplateStructure.getArticleTemplate();

            /*
             verify existence article
             */
            logger.debug("verify existence article with title {} in database", generateArticleDto.getTitle());
            Boolean duplicateArticle = existArticle(generateArticleDto.getTitle(), 0L);
            if (duplicateArticle.booleanValue()) {
                logger.info("article {} already registered in database", generateArticleDto.getTitle());
                throw new DuplicateTitleException("article title " + generateArticleDto.getTitle() + " already reagistered in database");
            }
            logger.info("populate article");
            Article article = new Article();
            article.setCreatedBy(generateArticleDto.getUsername());
            article.setModifyBy(generateArticleDto.getUsername());

            // get user profile from oauth server
            ResponseEntity<ApiResponseWrapper.RestResponse<UserProfileDto>> restResponse = null;
            try {
                restResponse = pakarOauthClient.getUser(BEARER + generateArticleDto.getToken(), generateArticleDto.getUsername());
                if (!restResponse.getBody().getApiStatus().getCode().equalsIgnoreCase(Constant.OK_ACK)) {
                    throw new OauthApiClientException("call oauth api client is failed");
                }
            } catch (Exception e) {
                logger.error("call oauth server failed", e);
            }

            article.setFullNameModifier(restResponse.getBody() != null ? restResponse.getBody().getData().getFullname() : "");
            article.setJudulArticle(generateArticleDto.getTitle());
            article.setArticleTemplate(template.getId());
            article.setArticleUsedBy(generateArticleDto.getUsedBy());
            Structure structure = structureRepository.findStructure(generateArticleDto.getStructureId());
            article.setStructure(structure);
            article.setArticleState(NEW);
            if (template.getTemplateName().trim().toLowerCase().equalsIgnoreCase("empty".toLowerCase())) {
                article.setUseEmptyTemplate(Boolean.TRUE);
            }

            /*
            copy article template to clone article content
             */
            logger.info("populate article contents clone");
            Iterable<ArticleTemplateContent> templateContents = articleTemplateContentRepository.findByTemplateId(template.getId());
            for (ArticleTemplateContent e : templateContents) {
                logger.debug("articleTemplateContent.getName() value {}", e.getName());
                logger.debug("param key {}", generateArticleDto.getParamKey());
                logger.debug("param value {}", generateArticleDto.getParamValue());
                ArticleContentClone articleContent = new ArticleContentClone();
                // get sequence content id
                Long seqContentId = articleContentRepository.getContentId();
                logger.info("get sequence content id {}", seqContentId);
                articleContent.setId(seqContentId);
                articleContent.setCreatedBy(generateArticleDto.getUsername());
                articleContent.setName(replaceTextByParams(e.getName(), generateArticleDto.getParamKey(), generateArticleDto.getParamValue()));
                articleContent.setLevel(e.getLevel());
                articleContent.setSort(e.getSort());
                articleContent.setTopicCaption(e.getTopicCaption());
                articleContent.setTopicContent(e.getTopicContent());
                article.getArticleContentClones().add(articleContent);
                articleContent.setArticle(article);

                // set article content
                ArticleContent articleContent_ = new ArticleContent();
                articleContent_.setId(seqContentId);
                articleContent_.setCreatedBy(generateArticleDto.getUsername());
                articleContent_.setName(replaceTextByParams(e.getName(), generateArticleDto.getParamKey(), generateArticleDto.getParamValue()));
                articleContent_.setLevel(e.getLevel());
                articleContent_.setSort(e.getSort());
                articleContent_.setTopicCaption(e.getTopicCaption());
                articleContent_.setTopicContent(e.getTopicContent());
                article.getArticleContents().add(articleContent_);
                articleContent_.setArticle(article);
            }


            logger.info("save article and content clone");
            article = articleRepository.save(article);
            logger.info("generate article success");

            // reset parent for article content clone
            for (ArticleContentClone articleContent : article.getArticleContentClones()) {
                for (ArticleTemplateContent articleTemplateContent : templateContents) {
                    if (articleContent.getName().equals(replaceTextByParams(articleTemplateContent.getName(), generateArticleDto.getParamKey(), generateArticleDto.getParamValue()))) {
                        if (articleTemplateContent.getParent() == null) {
                            articleContent.setParent(0L);
                            articleContentCloneRepository.save(articleContent);
                            break;
                        } else {
                            Optional<ArticleTemplateContent> parent = articleTemplateContentRepository.findById(articleTemplateContent.getId());
                            if (!parent.isEmpty()) {
                                ArticleTemplateContent _parent = parent.get();
                                for (ArticleContentClone articleContent1 : article.getArticleContentClones()) {
                                    if (articleContent1.getName().equals(replaceTextByParams(_parent.getName(), generateArticleDto.getParamKey(), generateArticleDto.getParamValue()))) {
                                        articleContent.setParent(articleContent1.getId());
                                        articleContentCloneRepository.save(articleContent);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // reset parent for article content
            for (ArticleContent articleContent : article.getArticleContents()) {
                for (ArticleTemplateContent articleTemplateContent : templateContents) {
                    if (articleContent.getName().equals(replaceTextByParams(articleTemplateContent.getName(), generateArticleDto.getParamKey(), generateArticleDto.getParamValue()))) {
                        if (articleTemplateContent.getParent() == null) {
                            articleContent.setParent(0L);
                            articleContentRepository.save(articleContent);
                            break;
                        } else {
                            Optional<ArticleTemplateContent> parent = articleTemplateContentRepository.findById(articleTemplateContent.getId());
                            if (!parent.isEmpty()) {
                                ArticleTemplateContent _parent = parent.get();
                                for (ArticleContent articleContent1 : article.getArticleContents()) {
                                    if (articleContent1.getName().equals(replaceTextByParams(_parent.getName(), generateArticleDto.getParamKey(), generateArticleDto.getParamValue()))) {
                                        articleContent.setParent(articleContent1.getId());
                                        articleContentRepository.save(articleContent);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            /*********** generate sugggestion article *********
             // TODO suggestion article
             /*************************************************/

            /*
            populate generated article to dto
             */
            articleDto = getArticleById(article.getId(), generateArticleDto.getUsername());
            return articleDto;
        } catch (DuplicateTitleException e) {
            logger.error("", e);
            throw new DuplicateTitleException("duplicate article title");
        } catch (NotFoundArticleTemplateException e) {
            logger.error("", e);
            throw new NotFoundArticleTemplateException("not found article template");
        } catch (Exception e) {
            logger.error("", e);
            throw new Exception("generate article failed");
        }
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    private ArticleDto getArticleById(Long id) throws Exception {
        try {
            Optional<Article> articleOpt = articleRepository.findById(id);

            if (articleOpt.isEmpty()) {
                throw new ArticleNotFoundException("not found article with id --> " + id);
            }

            Article article = articleOpt.get();
            ArticleDto articleDto = new ArticleDto();
            articleDto.setId(article.getId());
            articleDto.setCreatedBy(article.getCreatedBy());
            articleDto.setCreatedDate(article.getCreatedDate());
            articleDto.setModifiedBy(article.getModifyBy());
            articleDto.setModifiedDate(article.getModifyDate());
            articleDto.setTitle(article.getJudulArticle());
            articleDto.setShortDescription(article.getShortDescription());
            articleDto.setVideoLink(article.getVideoLink());
            articleDto.setNew(article.getNewArticle());
            articleDto.setPublished(article.getPublished());
            Iterable<ArticleContentClone> articleContents = articleContentCloneRepository.findsByArticleId(article.getId(), article.getCreatedBy());
            List<ArticleContentDto> articleContentDtos = new TreeArticleContents().menuTree(mapToListArticleContentCloneDto(articleContents));
            articleDto.setContents(articleContentDtos);
            Iterable<SkRefference> skRefferenceList = skReffRepository.findByArticleId(id);
            articleDto.setSkReff(mapToSkReffDto(skRefferenceList));
            Optional<Images> imageOpt = articleImageRepository.findByArticleId(article.getId());
            if (!imageOpt.isEmpty()) {
                Images image = imageOpt.get();
                articleDto.setImage(image.getUri());
            }
            Iterable<Article> relatedArticles = articleRelatedRepository.findByArticleId(article.getId());
            articleDto.setRelated(mapToRelatedArticleDto(relatedArticles));
            articleDto.setEmptyTemplate(article.getUseEmptyTemplate());
            articleDto.setStructureId(article.getStructure().getId());

            logger.debug("get parent structure of structure id {}", article.getStructure().getId());
            List<Structure> breadcumbs = structureRepository.findBreadcumbById(article.getStructure().getId());
            breadcumbs.forEach(e -> {
                BreadcumbStructureDto bcDto = new BreadcumbStructureDto();
                bcDto.setId(e.getId());
                bcDto.setName(e.getStructureName());
                bcDto.setLevel(e.getLevel());
                articleDto.getStructureParentList().add(bcDto);
            });

            // sorting bread crumb
            Collections.sort(articleDto.getStructureParentList(), new Comparator<BreadcumbStructureDto>() {
                @Override
                public int compare(BreadcumbStructureDto o1, BreadcumbStructureDto o2) {
                    return o1.getLevel().intValue() - o2.getLevel().intValue();
                }
            });

            return articleDto;
        } catch (ArticleNotFoundException e) {
            logger.error("exception", e);
            throw new ArticleNotFoundException("not found article id " + id);
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("get article failed");
        }
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    private ArticleDto getArticleById(Long id, String username) throws Exception {
        try {
            Optional<Article> articleOpt = articleRepository.findById(id, username);

            if (articleOpt.isEmpty()) {
                throw new ArticleNotFoundException("not found article with id --> " + id);
            }

            Article article = articleOpt.get();
            ArticleDto articleDto = new ArticleDto();
            articleDto.setId(article.getId());
            articleDto.setCreatedBy(article.getCreatedBy());
            articleDto.setCreatedDate(article.getCreatedDate());
            articleDto.setModifiedBy(article.getModifyBy());
            articleDto.setModifiedDate(article.getModifyDate());
            articleDto.setTitle(article.getJudulArticle());
            articleDto.setShortDescription(article.getShortDescription());
            articleDto.setVideoLink(article.getVideoLink());
            articleDto.setNew(article.getNewArticle());
            articleDto.setPublished(article.getPublished());
            Iterable<ArticleContentClone> articleContents = articleContentCloneRepository.findsByArticleId(article.getId(), article.getCreatedBy());
            List<ArticleContentDto> articleContentDtos = new TreeArticleContents().menuTree(mapToListArticleContentCloneDto(articleContents));
            articleDto.setContents(articleContentDtos);
            Iterable<SkRefference> skRefferenceList = skReffRepository.findByArticleId(id);
            articleDto.setSkReff(mapToSkReffDto(skRefferenceList));
            Optional<Images> imageOpt = articleImageRepository.findByArticleId(article.getId());
            if (!imageOpt.isEmpty()) {
                Images image = imageOpt.get();
                articleDto.setImage(image.getUri());
            }
            Iterable<Article> relatedArticles = articleRelatedRepository.findByArticleId(article.getId());
            articleDto.setRelated(mapToRelatedArticleDto(relatedArticles));
            articleDto.setEmptyTemplate(article.getUseEmptyTemplate());
            articleDto.setStructureId(article.getStructure().getId());

            logger.debug("get parent structure of structure id {}", article.getStructure().getId());
            List<Structure> breadcumbs = structureRepository.findBreadcumbById(article.getStructure().getId());
            breadcumbs.forEach(e -> {
                BreadcumbStructureDto bcDto = new BreadcumbStructureDto();
                bcDto.setId(e.getId());
                bcDto.setName(e.getStructureName());
                bcDto.setLevel(e.getLevel());
                articleDto.getStructureParentList().add(bcDto);
            });

            // sorting bread crumb
            Collections.sort(articleDto.getStructureParentList(), new Comparator<BreadcumbStructureDto>() {
                @Override
                public int compare(BreadcumbStructureDto o1, BreadcumbStructureDto o2) {
                    return o1.getLevel().intValue() - o2.getLevel().intValue();
                }
            });

            return articleDto;
        } catch (ArticleNotFoundException e) {
            logger.error("exception", e);
            throw new ArticleNotFoundException("not found article id " + id);
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("get article failed");
        }
    }


    /**
     * <p>
     * getting article using article id
     * isEdit is used as flagging that get article proses will edit article
     * </p>
     *
     * @param id
     * @param isEdit
     * @param username
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = {Exception.class
            , StructureNotFoundException.class
            , ArticleNotFoundException.class
            , OauthApiClientException.class})
    public ArticleDto getArticleById(Long id, boolean isEdit, String username, String token) throws Exception {
        try {
            Optional<Article> articleOpt = articleRepository.findById(id);
            if (articleOpt.isEmpty()) {
                throw new ArticleNotFoundException("not found article with id --> " + id);
            }

            Article article = articleOpt.get();
            // TODO add clone published article version
            if(article.getPublished().booleanValue() && !article.getIsClone().booleanValue()) {
                // clone article version to article
//                article = articleVersionService.reloadPublishedArticleVersion(article.getJudulArticle(), username);
            }

            ArticleDto articleDto = new ArticleDto();
            articleDto.setCreatedBy(article.getCreatedBy());
            articleDto.setCreatedDate(article.getCreatedDate());
            articleDto.setModifiedBy(article.getModifyBy());
            articleDto.setModifiedDate(article.getModifyDate());
            articleDto.setId(article.getId());
            articleDto.setTitle(article.getJudulArticle());
            articleDto.setShortDescription(article.getShortDescription());
            articleDto.setVideoLink(article.getVideoLink());
            articleDto.setPublished(article.getPublished());
            articleDto.setNew(article.getNewArticle());
            articleDto.setIsAdd(article.getIsAdd());
            articleDto.setIsClone(articleDto.getIsClone());

            // get user profile from oauth server
            ResponseEntity<ApiResponseWrapper.RestResponse<List<UserProfileDto>>> restResponseUp = null;
            restResponseUp = pakarOauthClient.getListUserProfile(BEARER + token, username, Arrays.asList(new String[]{article.getModifyBy()}));
            if (!restResponseUp.getBody().getApiStatus().getCode().equalsIgnoreCase(Constant.OK_ACK)) {
                throw new OauthApiClientException("call oauth api client is failed");
            }

            articleDto.setModifiedName(restResponseUp.getBody().getData().get(0).getFullname());

            // get main contents
            List<ArticleContentDto> articleContentDtos = new ArrayList<>();
            // article already saved
            List<ArticleContent> articleContents = articleContentRepository.findByArticleId(article.getId());
            logger.debug("main article contents {}", articleContents);
            articleContentDtos = new TreeArticleContents().menuTree(mapToListArticleContentDto(articleContents));

            logger.debug("delete all existing content clones");
            Iterable<ArticleContentClone> contentClones = articleContentCloneRepository.findsByArticleId(article.getId(), username);
            articleContentCloneRepository.deleteAll(contentClones);

            logger.debug("copy article content to clones");
            articleContents.forEach(e -> {
                ArticleContentClone clone = new ArticleContentClone();
                clone.setId(e.getId());
                clone.setVersion(e.getVersion());
                clone.setArticle(article);
                clone.setDescription(e.getDescription());
                clone.setLevel(e.getLevel());
                clone.setName(e.getName());
                clone.setParent(e.getParent());
                clone.setSort(e.getSort());
                clone.setTopicCaption(e.getTopicCaption());
                clone.setTopicContent(e.getTopicContent());
                clone.setCreatedBy(e.getCreatedBy());
                clone.setCreatedDate(e.getCreatedDate());
                clone.setDeleted(e.getDeleted());
                clone.setModifyBy(e.getModifyBy());
                clone.setModifyDate(e.getModifyDate());
                articleContentCloneRepository.save(clone);
            });

            articleDto.setContents(articleContentDtos);
            Iterable<SkRefference> skRefferenceList = skReffRepository.findByArticleId(id);
            articleDto.setSkReff(mapToSkReffDto(skRefferenceList));
            Optional<Images> imageOpt = articleImageRepository.findByArticleId(article.getId());
            if (!imageOpt.isEmpty()) {
                Images image = imageOpt.get();
                articleDto.setImage(image.getUri());
            }
            Iterable<Article> relatedArticles = articleRelatedRepository.findByArticleId(article.getId());
            articleDto.setRelated(mapToRelatedArticleDto(relatedArticles));
            articleDto.setEmptyTemplate(article.getUseEmptyTemplate());
            articleDto.setStructureId(article.getStructure().getId());

            logger.debug("get parent structure of structure id {}", article.getStructure().getId());
            List<Structure> breadcumbs = structureRepository.findBreadcumbById(article.getStructure().getId());
            breadcumbs.forEach(e -> {
                BreadcumbStructureDto bcDto = new BreadcumbStructureDto();
                bcDto.setId(e.getId());
                bcDto.setName(e.getStructureName());
                bcDto.setLevel(e.getLevel());
                articleDto.getStructureParentList().add(bcDto);
            });

            // sorting bread crumb
            Collections.sort(articleDto.getStructureParentList(), new Comparator<BreadcumbStructureDto>() {
                @Override
                public int compare(BreadcumbStructureDto o1, BreadcumbStructureDto o2) {
                    return o1.getLevel().intValue() - o2.getLevel().intValue();
                }
            });

            /*
            if editor etc will edit article, fe apps will send isEdit to true, then article will signed as edit
            editing status for one article will save in database t_article_edit with status TRUE
             */
            if (isEdit) {
                // update article edit to open status if article created <> user login
                if (!article.getCreatedBy().equalsIgnoreCase(username)) {
                    logger.debug("save user that start editing this article");
                    ArticleEdit articleEdit = articleEditRepository.findByUsername(article.getId(), username);
                    if (articleEdit == null) {
                        articleEdit = new ArticleEdit();
                        articleEdit.setCreatedBy(username);
                    } else {
                        articleEdit.setModifyBy(username);
                        articleEdit.setModifyDate(new Date());
                    }
                    articleEdit.setArticle(article);
                    articleEdit.setStatus(Boolean.TRUE);
                    articleEdit.setStartTime(new Date());

                    // get user profile from oauth server
                    ResponseEntity<ApiResponseWrapper.RestResponse<UserProfileDto>> restResponse = null;
                    try {
                        restResponse = pakarOauthClient.getUser(BEARER + token, username);
                    } catch (Exception e) {
                        logger.error("call oauth server failed", e);
                    }
                    articleEdit.setEditorName(restResponse.getBody().getData().getFullname());
                    articleEdit.setUsername(restResponse.getBody().getData().getUsername());
                    articleEditRepository.save(articleEdit);
                }
            }

            return articleDto;
        } catch (ArticleNotFoundException e) {
            logger.error("exception", e);
            throw new ArticleNotFoundException("not found article id " + id);
        } catch (OauthApiClientException e) {
            logger.error("exception", e);
            throw new OauthApiClientException("call oauth api failed ");
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("get article failed");
        }
    }

    /**
     * @param articleDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class
            , DataNotFoundException.class
            , SavingArticleVersionException.class
            , DuplicateTitleException.class
            , OauthApiClientException.class
            , WfApiClientException.class})
    public ArticleResponseDto saveArticle(MultipartArticleDto articleDto) throws Exception {
        ArticleResponseDto articleResponseDto = new ArticleResponseDto();
        try {
            logger.info("save article process");
            /*
             verify existence article
             */
            logger.debug("verify existence article with title {} in database", articleDto.getTitle());
            Boolean duplicateArticle = existArticle(articleDto.getTitle(), articleDto.getId());
            if (duplicateArticle.booleanValue()) {
                logger.info("article {} already registered in database", articleDto.getTitle());
                throw new DuplicateTitleException("article title " + articleDto.getTitle() + " already reagistered in database");
            }

            Optional<Article> articleOpt = articleRepository.findById(articleDto.getId());
            if (articleOpt.isEmpty()) {
                logger.info("not found article data with id {}", articleDto.getId());
                throw new DataNotFoundException("data not found ");
            }

            // TODO validate existence of structure (RMTM create article 3.4 Tambah Artikel - Edit Accordion Level 1)
            Article article = articleOpt.get();

            for (SkReffDto skReffDto : articleDto.getReferences()) {
                ArticleSkReff articleSkReff = new ArticleSkReff();
                articleSkReff.setCreatedBy(articleDto.getUsername());
                articleSkReff.setArticle(article);
                Optional<SkRefference> skRefferenceOpt = skReffRepository.findById(skReffDto.getId());
                articleSkReff.setSkRefference(skRefferenceOpt.isPresent() ? skRefferenceOpt.get() : null);

                articleSkReffRepository.save(articleSkReff);
            }

            for (MultipartArticleDto dto : articleDto.getRelated()) {
                RelatedArticle relatedArticle = new RelatedArticle();
                relatedArticle.setCreatedBy(articleDto.getUsername());
                relatedArticle.setSourceArticle(article);
                Optional<Article> relatedOpt = articleRepository.findById(dto.getId());
                relatedArticle.setRelatedArticle(relatedOpt.isPresent() ? relatedOpt.get() : null);

                relatedArticleRepository.save(relatedArticle);
            }

            /**** save article content *****/
            List<Long> contentDtoIds = new ArrayList<>();
            articleDto.getContents().forEach(e -> contentDtoIds.add(e.getId()));
            logger.debug("delete article content with id not include in {}", contentDtoIds);
            articleContentRepository.deleteByNotInIds(contentDtoIds, article.getId());
            articleDto.getContents()
                    .forEach(e ->
                            new ArticleContentHelper().verifyUpdateAndSaveContent(e, articleDto.getUsername()));

            /*******************************/

            Images _images = null;
            if (articleDto.getImage() != null) {
                if (!articleDto.getImage().isEmpty()) {
                    String location = basePath + pathCategory;
                    logger.debug("folder location {}", location);
                    logger.debug("image file name {}", articleDto.getImage().getOriginalFilename());

                    Path path = Paths.get(location + articleDto.getImage().getOriginalFilename());

                    logger.info("saving image");
                    Images images = new Images();
                    images.setCreatedBy(articleDto.getUsername());
                    logger.debug("save file name to db {}", path.getFileName().toString());
                    images.setImageName(path.getFileName().toString());
                    logger.debug("save path file to db {}", path.toAbsolutePath().toString());

                    Path pathLocation = Paths.get(pathCategory + articleDto.getImage().getOriginalFilename());
                    images.setUri(pathLocation.toAbsolutePath().toString());
                    articleResponseDto.setImage(pathLocation.toAbsolutePath().toString());
                    _images = imageRepository.save(images);

                    // save image to folder
                    logger.info("saving image to share folder");
                    FileUploadUtil.saveFile(location, articleDto.getImage());
                }
            }

            if (_images != null) {
                logger.info("saving article image mapper");
                ArticleImage am = new ArticleImage();
                am.setCreatedBy(articleDto.getUsername());
                am.setArticle(article);
                am.setImage(_images);
                articleImageRepository.save(am);
            }

            String currentState = article.getArticleState();

            /**
             * initialize article state
             */
            ArticleState articleState = null;

            // call to workflow server to set draft, if sendto <> null
            if (article.getIsAdd().booleanValue()) {
                logger.info("save draft article using article id {}", article.getId());

                UserWrapperDto userWrapperDto = new UserWrapperDto();
                userWrapperDto.setUsername(articleDto.getUsername());
                ResponseEntity<ApiResponseWrapper.RestResponse<List<String>>> restResponserRoles = pakarOauthClient
                        .getRolesByUser(BEARER + articleDto.getToken(), articleDto.getUsername(), userWrapperDto);
                List<String> rcvRoles = restResponserRoles.getBody().getData();
                rcvRoles.forEach(e -> logger.debug("user {} have roles {}", articleDto.getUsername(), rcvRoles.get(0)));

                Map<String, Object> wfRequest = new HashMap<>();
                wfRequest.put(PROCESS_KEY, ARTICLE_REVIEW_WF);
                wfRequest.put(TITLE_PARAM, articleDto.getTitle());
                wfRequest.put(ARTICLE_ID_PARAM, articleDto.getId());
                wfRequest.put(GROUP_PARAM, rcvRoles.get(0));

                ResponseEntity<ApiResponseWrapper.RestResponse<TaskDto>> restResponse = pakarWfClient
                        .startProcess(BEARER + articleDto.getToken(), articleDto.getUsername(), wfRequest);
                logger.debug("response api request {}", restResponse);
                logger.debug("reponse status code {}", restResponse.getStatusCode());
                currentState = restResponse.getBody().getData().getCurrentState();
                if (currentState != null) {
                    article.setArticleState(currentState);

                    logger.debug("save article state");
                    articleState = new ArticleState();
                    articleState.setCreatedBy(articleDto.getUsername());
                    articleState.setWfReqId(restResponse.getBody().getData().getRequestId());
                    articleState.setSender(restResponse.getBody().getData().getSender());
                    ResponseEntity<ApiResponseWrapper.RestResponse<List<UserProfileDto>>> restResponseUp = pakarOauthClient
                            .getListUserProfile(BEARER + articleDto.getToken(), articleDto.getUsername(), Arrays.asList(new String[]{articleState.getSender()}));
                    if (!restResponseUp.getBody().getApiStatus().getCode().equalsIgnoreCase(Constant.OK_ACK)) {
                        throw new OauthApiClientException("call oauth api client is failed");
                    }

                    List<UserProfileDto> userProfileDtos = restResponseUp.getBody().getData();
                    articleState.setFnSender(userProfileDtos != null ? userProfileDtos.get(0) != null ? userProfileDtos.get(0).getFullname() : "" : "");
                    articleState.setReceiver(restResponse.getBody().getData().getAssigne());
                    restResponseUp = pakarOauthClient
                            .getListUserProfile(BEARER + articleDto.getToken(), articleDto.getUsername(), Arrays.asList(new String[]{articleState.getReceiver()}));
                    if (!restResponseUp.getBody().getApiStatus().getCode().equalsIgnoreCase(Constant.OK_ACK)) {
                        throw new OauthApiClientException("call oauth api client is failed");
                    }
                    userProfileDtos.clear();
                    userProfileDtos = restResponseUp.getBody().getData();
                    articleState.setFnReceiver(userProfileDtos != null ? userProfileDtos.get(0) != null ? userProfileDtos.get(0).getFullname() : "" : "");
                    articleState.setReceiverState(restResponse.getBody().getData().getCurrentReceiverState());
                    articleState.setSenderState(restResponse.getBody().getData().getCurrentSenderState());
                    articleState.setArticle(article);

                    articleState = articleStateRepository.save(articleState);
                }
            }

//            if (articleState == null) {
////                if (article.getIsAdd().booleanValue())
//                articleState = articleStateRepository.findByArticleId(article.getId());
////                else
////                    articleState = articleStateRepository.findByArticleIdAndReceiver(article.getId(), articleDto.getUsername());
//            }

            if (articleDto.getIsHasSend().booleanValue()) {
                // send to
                logger.info("send article to {}", articleDto.getSendTo().getUsername());
                logger.debug("send note article {}", articleDto.getSendNote());

                UserWrapperDto userWrapperDto = new UserWrapperDto();
                userWrapperDto.setUsername(articleDto.getSendTo().getUsername());
                logger.debug("get roles of task receiver {}", articleDto.getSendTo().getUsername());
                ResponseEntity<ApiResponseWrapper.RestResponse<List<String>>> roleSenderResponse = pakarOauthClient
                        .getRolesByUser(BEARER + articleDto.getToken(), articleDto.getUsername(), userWrapperDto);
                if (!roleSenderResponse.getBody().getApiStatus().getCode().equalsIgnoreCase(Constant.OK_ACK)) {
                    throw new OauthApiClientException("call oauth api client is failed");
                }
                List<String> rcvRoles = roleSenderResponse.getBody().getData();
                rcvRoles.forEach(e -> logger.debug("task receiver {} has role {}", userWrapperDto.getUsername(), e));

                Map<String, Object> wfRequest = new HashMap<>();
                wfRequest.put(PROCESS_KEY, ARTICLE_REVIEW_WF);
                wfRequest.put(TITLE_PARAM, articleDto.getTitle());
                wfRequest.put(ARTICLE_ID_PARAM, articleDto.getId());
                wfRequest.put(TASK_TYPE_PARAM, "APPROVE");
                wfRequest.put(SENDER_PARAM, articleDto.getUsername());
                Map<String, String> assignDto = new HashMap();
                assignDto.put(RECEIVER_PARAM, articleDto.getSendTo().getUsername());
                wfRequest.put(SEND_TO_PARAM, assignDto);
                wfRequest.put(SEND_NOTE_PARAM, articleDto.getSendNote());
                wfRequest.put(GROUP_PARAM, rcvRoles.get(0));

                articleState = articleStateRepository.findByArticleId(article.getId());
                wfRequest.put(WORKFLOW_REQ_ID_PARAM, articleState.getWfReqId());

                ResponseEntity<ApiResponseWrapper.RestResponse<TaskDto>> restResponse = pakarWfClient
                        .completeTask(BEARER + articleDto.getToken(), articleDto.getUsername(), wfRequest);
                if (!restResponse.getBody().getApiStatus().getCode().equalsIgnoreCase(Constant.OK_ACK)) {
                    throw new WfApiClientException("call wf api client is failed");
                }
                logger.debug("response api request sendDraft {}", restResponse);
                logger.debug("reponse status code {}", restResponse.getStatusCode());
                currentState = restResponse.getBody().getData().getCurrentState();
                if (currentState != null) {
                    article.setNewArticle(Boolean.FALSE);
                    article.setArticleState(currentState);
                }

                /*
                get article state from system
                 */

                articleState.setCreatedBy(articleDto.getUsername());
                articleState.setSender(restResponse.getBody().getData().getSender());
                logger.debug("sender from workflow result {}", restResponse.getBody().getData().getSender());
                ResponseEntity<ApiResponseWrapper.RestResponse<List<UserProfileDto>>> restResponseUp = pakarOauthClient
                        .getListUserProfile(BEARER + articleDto.getToken(), articleDto.getUsername(), Arrays.asList(new String[]{articleState.getSender()}));
                if (!restResponseUp.getBody().getApiStatus().getCode().equalsIgnoreCase(Constant.OK_ACK)) {
                    throw new OauthApiClientException("call oauth api client is failed");
                }
                List<UserProfileDto> userProfileDtos = restResponseUp.getBody().getData();
                articleState.setFnSender(userProfileDtos != null ? userProfileDtos.get(0) != null ? userProfileDtos.get(0).getFullname() : "" : "");
                articleState.setReceiver(restResponse.getBody().getData().getAssigne());
                logger.debug("receiver from oauth result {}", restResponse.getBody().getData().getAssigne());
                restResponseUp = pakarOauthClient
                        .getListUserProfile(BEARER + articleDto.getToken(), articleDto.getUsername(), Arrays.asList(new String[]{articleState.getReceiver()}));
                if (!restResponseUp.getBody().getApiStatus().getCode().equalsIgnoreCase(Constant.OK_ACK)) {
                    throw new OauthApiClientException("call oauth api client is failed");
                }
                userProfileDtos.clear();
                userProfileDtos = restResponseUp.getBody().getData();
                articleState.setFnReceiver(userProfileDtos != null ? userProfileDtos.get(0) != null ? userProfileDtos.get(0).getFullname() : "" : "");
                articleState.setReceiverState(restResponse.getBody().getData().getCurrentReceiverState());
                articleState.setSenderState(restResponse.getBody().getData().getCurrentSenderState());
                articleStateRepository.save(articleState);

                // send notification to receiver
                ArticleNotification articleNotification = new ArticleNotification();
                articleNotification.setCreatedBy(articleDto.getUsername());
                articleNotification.setArticle(article);
                articleNotification.setNotifDate(new Date());
                String sendNote = "";
                if (!article.getPublished().booleanValue())
                    sendNote = messageSource.getMessage("article.notification.template"
                            , new Object[]{articleState.getFnSender(), Constant.Notification.TAMBAH_STATUS, articleDto.getSendNote() != null ? articleDto.getSendNote() : ""}, null);
                else
                    sendNote = messageSource.getMessage("article.notification.template"
                            , new Object[]{articleState.getFnSender(), Constant.Notification.EDIT_STATUS, articleDto.getSendNote() != null ? articleDto.getSendNote() : ""}, null);
                articleNotification.setSendNote(sendNote);
                articleNotification.setSender(restResponse.getBody().getData().getSender());
                articleNotification.setReceiver(restResponse.getBody().getData().getAssigne());
                articleNotification.setStatus("Terima");
                articleNotification.setDocumentType("Artikel");

                articleNotificationRepository.save(articleNotification);

                /**
                 * SET new article flag to false, cause article has sent to other
                 */
                article.setNewArticle(Boolean.FALSE);
            }

            // get user profile from oauth server
            ResponseEntity<ApiResponseWrapper.RestResponse<UserProfileDto>> restResponse = null;
            try {
                restResponse = pakarOauthClient.getUser(BEARER + articleDto.getToken(), articleDto.getUsername());
                if (!restResponse.getBody().getApiStatus().getCode().equalsIgnoreCase(Constant.OK_ACK)) {
                    throw new OauthApiClientException("call oauth api client is failed");
                }
            } catch (Exception e) {
                logger.error("call oauth server failed", e);
            }

            // set state
            article.setIsAdd(Boolean.FALSE);
            article.setFullNameModifier(restResponse.getBody() != null ? restResponse.getBody().getData().getFullname() : "");
            article.setModifyBy(articleDto.getUsername());
            article.setModifyDate(new Date());
            article.setShortDescription(articleDto.getDesc());
            article.setVideoLink(articleDto.getVideo());
            article.setJudulArticle(articleDto.getTitle());
            article = articleRepository.save(article);

            /*
            save article to article version
             */
            logger.info("save to article version");
            Boolean isReleased = article.getArticleState().equalsIgnoreCase(PUBLISHED) ? Boolean.TRUE : Boolean.FALSE;
            ArticleVersion av = articleVersionService.saveArticle(article, isReleased);
            if (av == null) {
                logger.error("could not save article version");
                throw new SavingArticleVersionException("could not save article version");
            }
            /**********************************************/

            articleResponseDto.setId(article.getId());
            articleResponseDto.setVideo(article.getVideoLink());
            for (BaseArticleDto dto : articleDto.getRelated()) {
                articleResponseDto.getRelated().add(dto);
            }

            for (ArticleContentDto dto : articleDto.getContents()) {
                articleResponseDto.getContents().add(dto);
            }

            for (SkReffDto dto : articleDto.getReferences()) {
                articleResponseDto.getReferences().add(dto);
            }

            articleResponseDto.setDesc(article.getShortDescription());
            articleResponseDto.setTitle(article.getJudulArticle());
            return articleResponseDto;
        } catch (SavingArticleVersionException e) {
            logger.error("", e);
            throw new SavingArticleVersionException("exception", e);
        } catch (DataNotFoundException e) {
            logger.error("", e);
            throw new DataNotFoundException("exception", e);
        } catch (OauthApiClientException e) {
            logger.error("", e);
            throw new OauthApiClientException("exception", e);
        } catch (WfApiClientException e) {
            logger.error("", e);
            throw new WfApiClientException("exception", e);
        } catch (DuplicateTitleException e) {
            logger.error("", e);
            throw new DuplicateTitleException("exception", e);
        } catch (Exception e) {
            logger.error("", e);
            throw new Exception("exception", e);
        }
    }


    /**
     * @param id
     * @param username
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = {Exception.class, DataNotFoundException.class, DeletePublishedArticleException.class})
    public Boolean cancelArticle(Long id, String username, String token) throws Exception {
        try {
            logger.info("cancel article with article id {}", id);
            Optional<Article> articleOpt = articleRepository.findById(id);
            if (articleOpt.isEmpty()) {
                logger.info("not article with id {}", id);
                throw new DataNotFoundException("not found article with id " + id);
            }

            Article article = articleOpt.get();
            if (article.getArticleState().equalsIgnoreCase(NEW)) {
                logger.info("delete related article with article id {}", article.getId());
                Iterable<RelatedArticle> relatedArticles = articleRelatedRepository.findRelatedArticleByArticleId(article.getId());
                if (relatedArticles != null)
                    relatedArticleRepository.deleteAll(relatedArticles);

                logger.info("delete article sk refference with article id {}", article.getId());
                Iterable<ArticleSkReff> articleSkReffs = articleSkReffRepository.findByArticleId(article.getId());
                if (articleSkReffs != null)
                    articleSkReffRepository.deleteAll(articleSkReffs);

                logger.info("delete article image with article id {}", article.getId());
                Iterable<ArticleImage> articleImages = articleImageRepository.findArticleImagesByArticleId(article.getId());
                if (articleImages != null)
                    articleImageRepository.deleteAll(articleImages);

                logger.info("delete article state with article id {}", article.getId());
                ArticleState articleState = articleStateRepository.findByArticleId(article.getId());
                if (articleState != null)
                    articleStateRepository.delete(articleState);

                logger.info("delete article edit with article id {}", article.getId());
                List<ArticleEdit> articleEdits = articleEditRepository.findByArticleId(article.getId());
                if (articleEdits != null)
                    articleEditRepository.deleteAll(articleEdits);

                logger.debug("delete from tabel t_article_content_clone with article id {} and username {} ", article.getId(), username);
                Iterable<ArticleContentClone> contents = articleContentCloneRepository.findsByArticleId(article.getId(), username);
                articleContentCloneRepository.deleteAll(contents);

                logger.info("delete new article");
                articleRepository.delete(article);
                logger.info("deleted article {} success", article.getJudulArticle());
            } else {
                /*
                if article has been saved, and then user cancel article then delete clone of article contents related with username and article id
                 */
                logger.debug("delete from tabel t_article_content_clone with article id {} and username {} ", article.getId(), username);
                Iterable<ArticleContentClone> contents = articleContentCloneRepository.findsByArticleId(article.getId(), username);
                articleContentCloneRepository.deleteAll(contents);
            }
            return Boolean.TRUE;
        } catch (DataNotFoundException e) {
            logger.error("not found data article", e);
            throw new DataNotFoundException("", e);
        } catch (Exception e) {
            logger.error("fail to cancel article", e);
            throw new Exception(e);
        }
    }

    /**
     * @param requestDeleteDto
     * @param username
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = {Exception.class, DataNotFoundException.class, DeletePublishedArticleException.class})
    public Boolean deleteArticle(RequestDeleteDto requestDeleteDto, String username, String token) throws Exception {
        try {
            logger.info("cancel article with article id {}", requestDeleteDto.getId());
            Optional<Article> articleOpt = articleRepository.findById(requestDeleteDto.getId());
            if (articleOpt.isEmpty()) {
                logger.info("not article with id {}", requestDeleteDto.getId());
                throw new DataNotFoundException("not found article with id " + requestDeleteDto.getId());
            }

            Article article = articleOpt.get();
            if (requestDeleteDto.getIsHasSend() == Boolean.TRUE) {
                // Checking apakah article sudah dipublish? kalau iya, perlu persetujuan publisher to Approved
                ArticleState articleState = null;

                articleState = articleStateRepository.findByArticleId(article.getId());
                UserWrapperDto userWrapperDto = new UserWrapperDto();
                userWrapperDto.setUsername(requestDeleteDto.getSendTo().getUsername());
                logger.debug("get roles of task receiver {}", requestDeleteDto.getSendTo().getUsername());
                ResponseEntity<ApiResponseWrapper.RestResponse<List<String>>> roleSenderResponse = pakarOauthClient
                        .getRolesByUser(BEARER + token, username, userWrapperDto);
                List<String> rcvRoles = roleSenderResponse.getBody().getData();
                rcvRoles.forEach(e -> logger.debug("task receiver {} has role {}", userWrapperDto.getUsername(), e));

                Map<String, Object> wfRequestDelete = new HashMap<>();
                wfRequestDelete.put(PROCESS_KEY, ARTICLE_DELETE_WF);
                wfRequestDelete.put(ARTICLE_ID_PARAM, requestDeleteDto.getId());
                wfRequestDelete.put(SENDER_PARAM, username);
                Map<String, String> assignDto = new HashMap();
                assignDto.put(RECEIVER_PARAM, requestDeleteDto.getSendTo().getUsername());
                wfRequestDelete.put(SEND_TO_PARAM, assignDto);
                wfRequestDelete.put(SEND_NOTE_PARAM, requestDeleteDto.getSendNote());
                wfRequestDelete.put(GROUP_PARAM, rcvRoles.get(0));

                // Flow request delete
                ResponseEntity<ApiResponseWrapper.RestResponse<TaskDto>> restResponse = pakarWfClient
                        .requestDelete(BEARER + token, username, wfRequestDelete);
                logger.debug("response api request {}", restResponse);
                logger.debug("reponse status code {}", restResponse.getStatusCode());
                if (!restResponse.getBody().getApiStatus().getCode().equalsIgnoreCase(Constant.OK_ACK)) {
                    throw new WfApiClientException("fail call oauth endpoint requestDelete");
                }

                // save ke article state
                articleState.setCreatedBy(username);
                articleState.setCreatedDate(new Date());
                ResponseEntity<ApiResponseWrapper.RestResponse<List<UserProfileDto>>> restResponseUp = pakarOauthClient
                        .getListUserProfile(BEARER + token, username, Arrays.asList(new String[]{articleState.getSender()}));
                List<UserProfileDto> userProfileDtos = restResponseUp.getBody().getData();
                articleState.setFnSender(userProfileDtos != null ? userProfileDtos.get(0) != null ? userProfileDtos.get(0).getFullname() : "" : "");
                articleState.setReceiver(restResponse.getBody().getData().getAssigne());
                restResponseUp = pakarOauthClient
                        .getListUserProfile(BEARER + token, username, Arrays.asList(new String[]{articleState.getReceiver()}));
                userProfileDtos.clear();
                userProfileDtos = restResponseUp.getBody().getData();
                articleState.setFnReceiver(userProfileDtos != null ? userProfileDtos.get(0) != null ? userProfileDtos.get(0).getFullname() : "" : "");
                articleState.setReceiverState(restResponse.getBody().getData().getCurrentReceiverState());
                articleState.setSenderState(restResponse.getBody().getData().getCurrentSenderState());
                articleState.setArticle(article);
                articleState.setWfReqId(restResponse.getBody().getData().getRequestId());

                articleStateRepository.save(articleState);

                // send request delete notification to receiver
                ArticleNotification articleNotification = new ArticleNotification();
                articleNotification.setCreatedBy(username);
                articleNotification.setArticle(article);
                articleNotification.setNotifDate(new Date());

                String sendNote = messageSource.getMessage("article.notification.requestDelete"
                        , new Object[]{articleState.getFnSender(), Constant.Notification.HAPUS_STATUS, requestDeleteDto.getSendNote() != null ? requestDeleteDto.getSendNote() : ""}, null);
                articleNotification.setSendNote(sendNote);
                articleNotification.setSender(restResponse.getBody().getData().getSender());
                articleNotification.setReceiver(restResponse.getBody().getData().getAssigne());
                articleNotification.setStatus("Terima");
                articleNotification.setDocumentType("Artikel");

                articleNotificationRepository.save(articleNotification);

            }
            return Boolean.TRUE;
        } catch (DataNotFoundException e) {
            logger.error("not found data article", e);
            throw new DataNotFoundException("", e);
        } catch (WfApiClientException e) {
            logger.error("fail to call workflow request delete article", e);
            throw new Exception(e);
        } catch (Exception e) {
            logger.error("fail to delete article", e);
            throw new Exception(e);
        }
    }

    /**
     * Cancel sent article
     *
     * @param id
     * @param username
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = {Exception.class
            , DataNotFoundException.class
            , DeletePublishedArticleException.class
            , ArticleInEditingxception.class
            , WfApiClientException.class})
    public Boolean cancelSendArticle(Long id, String receiver, String username, String token) throws Exception {
        try {
            logger.info("cancel send article id {} to {}", receiver, id);
            Optional<Article> articleOpt = articleRepository.findById(id);
            if (articleOpt.isEmpty()) {
                logger.info("not article found with id {}", id);
                throw new DataNotFoundException("not found article with id " + id);
            }

            Article article = articleOpt.get();
            /*
            validate if receiver not already editing article was sent
             */
            ArticleState articleState = articleStateRepository.findByArticleId(article.getId());
            logger.info("receiver of article id {} and and wf request id {} ---> {} ", new Object[]{articleState.getArticle().getId(), articleState.getWfReqId(), articleState.getReceiver()});
            logger.info("find editing status of article id {} from receiver {}", id, receiver);
            ArticleEdit articleEdit = articleEditRepository.findActiveEditingStatusByUsername(id, receiver);
            if (articleEdit != null) {
                logger.info("article id {} in editing status", id);
                throw new ArticleInEditingxception("article id " + id + " in editing status ");
            }

            /*
            request cancel task to worflow engine
             */
            Map<String, Object> wfRequest = new HashMap<>();
            wfRequest.put(PROCESS_KEY, ARTICLE_REVIEW_WF);
            wfRequest.put(TITLE_PARAM, article.getJudulArticle());
            wfRequest.put(ARTICLE_ID_PARAM, article.getId());
            wfRequest.put(SENDER_PARAM, username);
            Map<String, String> assignDto = new HashMap();
            assignDto.put(USERNAME_PARAM, receiver);
            wfRequest.put(SEND_TO_PARAM, assignDto);
            wfRequest.put(WORKFLOW_REQ_ID_PARAM, articleState.getWfReqId());
            ResponseEntity<ApiResponseWrapper.RestResponse<TaskDto>> restResponse = pakarWfClient
                    .cancelTask(BEARER + token, username, wfRequest);
            if (!restResponse.getBody().getApiStatus().getCode().equalsIgnoreCase(Constant.OK_ACK)) {
                logger.info("fail call workflow endpoint cancelTask");
                throw new WfApiClientException("fail call workflow endpoint cancelTask");
            }
            logger.debug("response api request {}", restResponse);
//            currentState = restResponse.getBody().getData().getCurrentState();

            logger.info("get article state with article id {}", article.getId());
            articleState.setModifyBy(username);
            articleState.setModifyDate(new Date());
            articleState.setSenderState(null);
            articleState.setReceiverState(Constant.ArticleWfState.DRAFT);
            articleState.setReceiver(username);
            articleStateRepository.save(articleState);

            return Boolean.TRUE;
        } catch (DataNotFoundException e) {
            logger.error("fail to cancel article", e);
            throw new DataNotFoundException("", e);
        } catch (WfApiClientException e) {
            logger.error("cancel send article", e);
            throw new WfApiClientException("", e);
        } catch (ArticleInEditingxception e) {
            logger.error("cancel send article", e);
            throw new ArticleInEditingxception("", e);
        } catch (Exception e) {
            logger.error("fail to cancel article", e);
            throw new Exception(e);
        }
    }

    /**
     * get sequence number of content when fe will create new content
     *
     * @return
     * @throws Exception
     */
    @Override
    public Long getContentId(String username, String token) throws Exception {
        try {
            logger.info("get content id");
            ResponseEntity<ApiResponseWrapper.RestResponse<List<String>>> restResponse = pakarOauthClient.getRoles(BEARER + token, username);
            logger.debug("response api request {}", restResponse);
            List<String> roles = restResponse.getBody().getData();
            String role = roles.get(0);
            if (role.equals(ROLE_READER)) {
                logger.info("role {} has no authorize get content id", role);
                throw new AccesDeniedDeleteContentException("role " + role + " has no authorize get content id");
            }
            Long contentId = articleContentRepository.getContentId();
            return contentId;
        } catch (AccesDeniedDeleteContentException e) {
            logger.error("exception", e);
            throw new AccesDeniedDeleteContentException("exception", e);
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("exception", e);
        }
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ArticleContentDto getContentById(Long id) throws Exception {
        try {
            logger.info("get article content by id {}", id);
//            Optional<ArticleContent> articleContentOp = articleContentRepository.findById(id);
//            if (articleContentOp.isEmpty()) {
//                throw new DataNotFoundException("data not found");
//            }
//
//            ArticleContentClone articleContent = articleContentOp.get();
            ArticleContentClone articleContent = articleCloneService.findById(id, null);
            ArticleContentDto articleContentDto = mapToArticleContentDto(articleContent);
            return articleContentDto;
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            throw new DataNotFoundException("data not found");
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception(e);
        }
    }

    /**
     * @param id
     * @param username
     * @return
     * @throws Exception
     */
    @Override
    public ArticleContentDto getContentById(Long id, String username) throws Exception {
        try {
            logger.info("get article content by id {} and username {}", id, username);
            if (username == null)
                return getContentById(id);
            else {
                ArticleContentClone articleContent = articleCloneService.findById(id, username);
                ArticleContentDto articleContentDto = mapToArticleContentDto(articleContent);
                return articleContentDto;
            }
        } catch (Exception e) {
            logger.error("", e);
            throw new Exception(e);
        }
    }

    /**
     * @param articleContentDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = {Exception.class, DataNotFoundException.class, ParentContentNotFoundException.class, ArticleNotFoundException.class})
    public ArticleContentDto saveContent(ArticleContentDto articleContentDto) throws Exception {
        try {
            /*
            cek level first, if level == 1 then get content id, this content id will use to set content id
             */
            logger.debug("level action value of content is {}", articleContentDto.getLevel().longValue());
            logger.debug("parent value {}", articleContentDto.getParent());
            logger.info("process save and update content with id {}", articleContentDto.getId());
            articleContentDto.getBreadcumbArticleContentDtos().clear();

            logger.debug("save article content for user {}", articleContentDto.getUsername());
            ArticleContentClone articleContent = articleCloneService.findById(articleContentDto.getId(), articleContentDto.getUsername());
            if (articleContent == null) {
                logger.debug("not found article content with id {}, create new entity", articleContentDto.getId());
                articleContent = new ArticleContentClone();
                articleContent.setId(articleContentDto.getId());
                articleContent.setCreatedBy(articleContentDto.getUsername());
            } else {
                logger.debug("article content with id {} has found, update entity", articleContentDto.getId());
                articleContent.setModifyBy(articleContentDto.getUsername());
                articleContent.setModifyDate(new Date());
            }

            articleContent.setName(articleContentDto.getTitle());
            articleContent.setDescription(articleContentDto.getIntro());
            articleContent.setTopicCaption(articleContentDto.getTopicTitle());
            articleContent.setTopicContent(articleContentDto.getTopicContent());
            articleContent.setSort(articleContentDto.getSort());
            articleContent.setLevel(articleContentDto.getLevel());

            if (articleContentDto.getLevel().longValue() == 1) {
                logger.debug("set parent value for for level value {}", articleContentDto.getLevel().longValue());
                articleContent.setParent(articleContentDto.getParent());
            } else {
                logger.debug("level article content <> 1");
//                Optional<ArticleContent> parentOpt = articleContentRepository.findById(articleContentDto.getParent());
//                if (parentOpt.isEmpty()) {
//                    throw new ParentContentNotFoundException("parent article content not found");
//                }
//                articleContent.setParent(parentOpt.get().getId());
                Optional<ArticleContentClone> parentOpt = articleContentCloneRepository.findById(articleContentDto.getParent());
                if (parentOpt.isEmpty()) {
                    throw new ParentContentNotFoundException("parent article content not found");
                }
                articleContent.setParent(parentOpt.get().getId());
            }

            Optional<Article> articleOpt = articleRepository.findById(articleContentDto.getArticleId());
            if (articleOpt.isEmpty()) {
                throw new ArticleNotFoundException("data not found");
            }
            Article article = articleOpt.get();
            articleContent.setArticle(article);
            logger.info("save article content to db");
//            articleContent = articleContentRepository.save(articleContent);
            articleContent = articleCloneService.saveContent(articleContent);

            logger.debug("get article content parent for article content id {}", articleContent.getId());
            List<ArticleContentClone> breadcumbs = articleContentCloneRepository.findParentListById(articleContent.getId());
            breadcumbs.forEach(e -> {
                BreadcumbArticleContentDto bcDto = new BreadcumbArticleContentDto();
                bcDto.setId(e.getId());
                bcDto.setName(e.getName());
                bcDto.setLevel(e.getLevel());
                articleContentDto.getBreadcumbArticleContentDtos().add(bcDto);
            });

            logger.debug("sorted list parent content");
            // sorting bread crumb
            Collections.sort(articleContentDto.getBreadcumbArticleContentDtos(), new Comparator<BreadcumbArticleContentDto>() {
                @Override
                public int compare(BreadcumbArticleContentDto o1, BreadcumbArticleContentDto o2) {
                    logger.debug("level 1 {}", o1.getLevel());
                    logger.debug("level 2 {}", o2.getLevel());
                    return o1.getLevel().intValue() - o2.getLevel().intValue();
                }
            });
            return articleContentDto;
        } catch (ParentContentNotFoundException e) {
            logger.error("exception", e);
            throw new ParentContentNotFoundException("parent content not found");
        } catch (ArticleNotFoundException e) {
            logger.error("exception", e);
            throw new ArticleNotFoundException("article data not found");
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("exception", e);
        }
    }

    /**
     * @param articleContentDtos
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = {Exception.class, DataNotFoundException.class})
    public List<ArticleContentDto> saveBatchContents(List<ArticleContentDto> articleContentDtos, String username, String token) throws Exception {
        try {
            logger.info("save batch contents");
            for (ArticleContentDto articleContentDto : articleContentDtos) {
                logger.info("find content byd id");
                Optional<ArticleContent> contentOpt = articleContentRepository.findById(articleContentDto.getId());
                if (contentOpt.isEmpty()) {
                    logger.info("not found article content with id {}", articleContentDto.getId());
                    throw new DataNotFoundException("data not found");
                }

                ArticleContent articleContent = contentOpt.get();

                Long currentLevel = articleContent.getLevel();
                Long currentPid = articleContent.getParent();
                // validate parent id exist in database base on current pid if current pid != 0
                if (currentPid.intValue() > 0) {
                    boolean isExist = articleContentRepository.existsById(currentPid);
                    if (!isExist) {
                        logger.info("no article content found in database, process stopped");
                        throw new DataNotFoundException("article content found in database");
                    }
                }

                if (currentPid.intValue() == 0) {
                    if (currentLevel.intValue() != 1) {
                        logger.info("invalid level, cause article content has parent value 0 but level != 1, process stopped");
                        throw new InvalidLevelException("invalid level, cause article content has parent value 0 but level != 1");
                    }
                }

                /**
                 * if current parent id != 0 then
                 *   get structure with parent id = compared structure id
                 *   if child
                 */
                logger.info("validate article content structure");
                for (ArticleContentDto articleContentDto1 : articleContentDtos) {
                    logger.info("article content structure compared to {}", articleContentDto1.toString());
                    if (articleContentDto1.getId().intValue() == currentPid.intValue()) {
                        Long parentLevel = articleContentDto1.getLevel();
                        if (currentLevel.intValue() <= parentLevel.intValue()) {
                            logger.info("child level has value smaller than parent level, process stopped");
                            throw new InvalidLevelException("child level has value smaller than parent level");
                        }
                    }
                }

                logger.info("repopulate article content from dto");
                articleContent.setModifyBy(username);
                articleContent.setModifyDate(new Date());
                articleContent.setLevel(articleContentDto.getLevel());
                articleContent.setSort(articleContentDto.getSort());
                articleContent.setParent(articleContentDto.getParent());

                logger.info("save batch content");
                articleContentRepository.save(articleContent);
            }
            return articleContentDtos;
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            throw new DataNotFoundException("data not found exception", e);
        } catch (Exception e) {
            logger.error("exception", e);
            throw new SaveBatchException("failed save batch exception", e);
        }
    }

    /**
     * @param deleteContentDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = {Exception.class
            , DataNotFoundException.class
            , AccesDeniedDeleteContentException.class
            , ArticleContentNotFoundException.class})
    public Boolean deleteContent(DeleteContentDto deleteContentDto) throws Exception {
        try {
            logger.info("delete content with id {}", deleteContentDto.getContentId());
//            Optional<ArticleContent> articleContentOpt = articleContentRepository.findById(deleteContentDto.getContentId());
//            if (articleContentOpt.isEmpty()) {
//                logger.info("not found article content with id {}", deleteContentDto.getContentId());
//                throw new ArticleContentNotFoundException(String.format("article content with id %d not found", deleteContentDto.getContentId()));
//            }

            ArticleContentClone articleContent = articleCloneService.findById(deleteContentDto.getContentId(), deleteContentDto.getUsername());
            if (articleContent == null) {
                logger.info("not found article content with id {}", deleteContentDto.getContentId());
                throw new ArticleContentNotFoundException(String.format("article content with id %d not found", deleteContentDto.getContentId()));
            }
            logger.debug("call get roles api with token {}", deleteContentDto.getContentId());
            ResponseEntity<ApiResponseWrapper.RestResponse<List<String>>> restResponse = pakarOauthClient.getRoles(BEARER + deleteContentDto.getToken(), deleteContentDto.getUsername());
            logger.debug("response api request {}", restResponse);

//            ArticleContent articleContent = articleContentOpt.get();
            List<String> roles = restResponse.getBody().getData();
            String role = roles.get(0);
            Long level = articleContent.getLevel();
            if (level.longValue() == 1) {
                logger.info("validate role user with level content");
                if (!role.equals(ROLE_ADMIN)) {
                    logger.info("user with role {} has no authorize to delete content level 1", role);
                    throw new AccesDeniedDeleteContentException("role " + role + " has no authorize delete content");
                }
            }

            logger.debug("username {} ---> has roles {}", deleteContentDto.getUsername(), roles);

            // get article
            Optional<Article> articleOpt = articleRepository.findById(articleContent.getArticle().getId());
            if (articleOpt.isEmpty()) {
                logger.info("not found article from content with id {}", deleteContentDto.getContentId());
                throw new DataNotFoundException("data not found");
            }

//            Article _article = articleOpt.get();
//            if (_article.getArticleState().toLowerCase().equalsIgnoreCase(PUBLISHED)) {
//                List<ArticleContent> children = articleContentRepository.findContentChildrenAndOwnRowByParentId(articleContent.getId());
//                for (ArticleContent content : children) {
//                    logger.debug("content level {} and title {}", content.getLevel(), content.getName());
//                    content.setModifyBy(deleteContentDto.getUsername());
//                    content.setModifyDate(new Date());
//                    content.setDeleted(Boolean.TRUE);
//                    articleContentRepository.save(content);
//                }
//            } else {
//                // delete from row in db
//                logger.debug("delete from db current id and children");
//                List<ArticleContent> children = articleContentRepository.findContentChildrenAndOwnRowByParentId(articleContent.getId());
//                for (ArticleContent content : children) {
//                    logger.debug("delete content title ---> id {}", content.getName(), content.getId());
//                    articleContentRepository.delete(content);
//                }
//            }

            logger.debug("delete from tabel t_article_content_clone current id {} and children", articleContent.getId());
            List<ArticleContentClone> children = articleContentCloneRepository.findChildsIncludeParent(articleContent.getId(), deleteContentDto.getUsername());
            List<Long> ids = new ArrayList<>();
            children.forEach(e -> ids.add(e.getId()));
            articleContentCloneRepository.deleteByIds(deleteContentDto.getUsername(), ids);
            return Boolean.TRUE;
        } catch (AccesDeniedDeleteContentException e) {
            logger.error("exception", e);
            throw new AccesDeniedDeleteContentException("has no authorize delete content level 1");
        } catch (ArticleContentNotFoundException e) {
            logger.error("exception", e);
            throw new ArticleContentNotFoundException("has no authorize delete content level 1");
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            throw new DataNotFoundException("data not found for content id " + deleteContentDto.getContentId());
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("exception", e);
        }
    }

    /**
     * @param searchDto
     * @return
     * @throws Exception
     */
    @Override
    public Page<RelatedArticleDto> search(SearchDto searchDto) throws Exception {
        try {
            logger.info("search related article");
            if (searchDto.getPage() == null) {
                searchDto.setPage(0L);
            }
            Pageable pageable = PageRequest.of(searchDto.getPage().intValue() - 1, searchDto.getSize().intValue());
            Page<Article> searchResultPage = articleRepository.findRelatedArticles(searchDto.getExclude(), searchDto.getKeyword(), pageable);
            logger.debug("total items {}", searchResultPage.getTotalElements());
            logger.debug("total contents {}", searchResultPage.getContent().size());
            return new ToDoMapper().mapEntityPageIntoDTOPage(pageable, searchResultPage);
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("exception", e);
        }
    }

    /*
    HELPER METHOD
     */
    private List<ArticleContentDto> mapToListArticleContentDto(List<ArticleContent> iterable) {
        List<ArticleContentDto> listOfContents = new ArrayList<>();
        for (ArticleContent content : iterable) {
            ArticleContentDto contentDto = new ArticleContentDto();
            contentDto.setId(content.getId());
            contentDto.setLevel(content.getLevel());
            contentDto.setSort(content.getSort());
            contentDto.setTitle(content.getName());
            if (content.getLevel().intValue() == 1)
                contentDto.setIntro(content.getDescription());
            contentDto.setParent(content.getParent());
            contentDto.setArticleId(content.getArticle().getId());
            contentDto.setTopicTitle(content.getTopicCaption());
            contentDto.setTopicContent(content.getTopicContent());
            listOfContents.add(contentDto);
        }

        // sorting article content
        logger.debug("prepare article content list with total element {}", listOfContents.size());
        if (listOfContents.size() > 0) {
            Collections.sort(listOfContents, new Comparator<ArticleContentDto>() {
                @Override
                public int compare(ArticleContentDto o1, ArticleContentDto o2) {
                    logger.debug("sort 1 {}", o1.getSort());
                    logger.debug("sort 2 {}", o2.getSort());
                    return o1.getSort().intValue() - o2.getSort().intValue();
                }
            });
        }
        return listOfContents;
    }

    /*
    mapper article content clone to article content dto
     */
    private List<ArticleContentDto> mapToListArticleContentCloneDto(Iterable<ArticleContentClone> iterable) {
        List<ArticleContentDto> listOfContents = new ArrayList<>();
        iterable.forEach(e -> {
            ArticleContentDto contentDto = new ArticleContentDto();
            contentDto.setId(e.getId());
            contentDto.setLevel(e.getLevel());
            contentDto.setSort(e.getSort());
            contentDto.setTitle(e.getName());
            if (e.getLevel().intValue() == 1)
                contentDto.setIntro(e.getDescription());
            contentDto.setParent(e.getParent());
            contentDto.setArticleId(e.getArticle().getId());
            contentDto.setTopicTitle(e.getTopicCaption());
            contentDto.setTopicContent(e.getTopicContent());
            listOfContents.add(contentDto);
        });
        // sorting article content
        logger.debug("prepare article content list with total element {}", listOfContents.size());
        if (listOfContents.size() > 0) {
            Collections.sort(listOfContents, new Comparator<ArticleContentDto>() {
                @Override
                public int compare(ArticleContentDto o1, ArticleContentDto o2) {
                    logger.debug("sort 1 {}", o1.getSort());
                    logger.debug("sort 2 {}", o2.getSort());
                    return o1.getSort().intValue() - o2.getSort().intValue();
                }
            });
        }
        return listOfContents;
    }

    /**
     * @param content
     * @return
     */
    private ArticleContentDto mapToArticleContentDto(ArticleContent content) {
        ArticleContentDto contentDto = new ArticleContentDto();
        contentDto.setId(content.getId());
        contentDto.setLevel(content.getLevel());
        contentDto.setSort(content.getSort());
        contentDto.setTitle(content.getName());
        if (content.getLevel().intValue() == 1)
            contentDto.setIntro(content.getDescription());
        contentDto.setParent(content.getParent());
        contentDto.setArticleId(content.getArticle().getId());
        return contentDto;
    }

    private ArticleContentDto mapToArticleContentDto(ArticleContentClone content) {
        ArticleContentDto contentDto = new ArticleContentDto();
        contentDto.setId(content.getId());
        contentDto.setLevel(content.getLevel());
        contentDto.setSort(content.getSort());
        contentDto.setTitle(content.getName());
        if (content.getLevel().intValue() == 1)
            contentDto.setIntro(content.getDescription());
        contentDto.setParent(content.getParent());
        contentDto.setArticleId(content.getArticle().getId());
        return contentDto;
    }

    /**
     * @param iterable
     * @return
     */
    private List<SkReffDto> mapToSkReffDto(Iterable<SkRefference> iterable) {
        List<SkReffDto> listOfDtos = new ArrayList<>();
        for (SkRefference entity : iterable) {
            SkReffDto dto = new SkReffDto();
            dto.setId(entity.getId());
            dto.setTitle(entity.getTitle());
            dto.setSkNumber(entity.getSkNumber());
            listOfDtos.add(dto);
        }
        return listOfDtos;
    }

    /**
     * @param iterable
     * @return
     */
    private List<RelatedArticleDto> mapToRelatedArticleDto(Iterable<Article> iterable) {
        List<RelatedArticleDto> listOfDtos = new ArrayList<>();
        for (Article entity : iterable) {
            RelatedArticleDto dto = new RelatedArticleDto();
            dto.setId(entity.getId());
            dto.setTitle(entity.getJudulArticle());
            listOfDtos.add(dto);
        }
        return listOfDtos;
    }

    /**
     * @param iterable
     * @return
     */
    private List<BreadcumbArticleContentDto> mapToListParentArticleContentDto(Iterable<ArticleContent> iterable) {
        List<BreadcumbArticleContentDto> listOfContents = new ArrayList<>();
        for (ArticleContent content : iterable) {
            BreadcumbArticleContentDto contentDto = new BreadcumbArticleContentDto();
            contentDto.setId(content.getId());
            contentDto.setLevel(content.getLevel());
            contentDto.setName(content.getName());
            listOfContents.add(contentDto);
        }
        return listOfContents;
    }

    /**
     * get param from template
     *
     * @param text
     * @return
     */
    private List<String> getParams(String text) {
        logger.debug("split param tag {}", paramtTag);
        String[] tags = paramtTag.split("\\|");
        List<String> params = new ArrayList<>();
        for (int i = 0; i < tags.length; i++) {
            String tagEl = tags[i];
            Character openTag = tagEl.charAt(0);
            Character closeTag = tagEl.charAt(1);

            boolean startExtract = false;
            String param = "";
            for (int j = 0; j < text.length(); j++) {
                if (text.charAt(j) == closeTag) {
                    startExtract = false;
                    params.add(param.trim());
                }

                if (startExtract) {
                    param = param + text.charAt(j);
                }

                if (text.charAt(j) == openTag) {
                    startExtract = true;
                }
            }
        }
        return params;
    }

    /**
     * replace accordeon text with params
     *
     * @param replacedText
     * @param paramKey
     * @param paramValue
     * @return
     */
    private String replaceTextByParams(String replacedText, String paramKey, String paramValue) {
        logger.debug("split param tag {}", paramtTag);
        String[] tags = paramtTag.split("\\|");
        String key = paramKey;
        for (int i = 0; i < tags.length; i++) {
            String tagEl = tags[i];
            Character openTag = tagEl.charAt(0);
            Character closeTag = tagEl.charAt(1);
            paramKey = openTag + paramKey + closeTag;
            logger.debug("param key {} ---> param value {}", paramKey, paramValue);
            if (replacedText.contains(paramKey)) {
                replacedText = replacedText.replace(paramKey, paramValue);
                logger.debug("replaced text {}", replacedText);
                break;
            }
            paramKey = key;
        }
        logger.debug("replaced content title {}", replacedText);
        return replacedText;
    }

    /**
     *
     */
    private class ToDoMapper {
        public List<RelatedArticleDto> mapEntitiesIntoDTOs(Iterable<Article> entities) {
            List<RelatedArticleDto> dtos = new ArrayList<>();
            entities.forEach(e -> dtos.add(mapEntityIntoDTO(e)));
            return dtos;
        }

        /*

         */
        public RelatedArticleDto mapEntityIntoDTO(Article entity) {
            RelatedArticleDto dto = new RelatedArticleDto();

            dto.setCreatedBy(entity.getCreatedBy());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setId(entity.getId());
            dto.setTitle(entity.getJudulArticle());
            return dto;
        }

        /**
         * Transforms {@code Page<ENTITY>} objects into {@code Page<DTO>} objects.
         *
         * @param pageRequest The information of the requested page.
         * @param source      The {@code Page<ENTITY>} object.
         * @return The created {@code Page<DTO>} object.
         */
        public Page<RelatedArticleDto> mapEntityPageIntoDTOPage(Pageable pageRequest, Page<Article> source) {
            List<RelatedArticleDto> dtos = mapEntitiesIntoDTOs(source.getContent());
            return new PageImpl<>(dtos, pageRequest, source.getTotalElements());
        }

    }

    /**
     * @param requestFAQDto
     * @return
     * @throws Exception
     */
    @Override
    public List<FaqDto> findFaq(Long requestFAQDto) throws Exception {
        try {
            logger.info("search faq");
            List<FaqDto> listOfDtos = new ArrayList<>();
            List<FAQ> searchResult = articleFaqRepository.findFAQ(requestFAQDto);
            logger.info("search result = " + searchResult);
            for (FAQ entity : searchResult) {
                FaqDto dto = new FaqDto();
                dto.setId(entity.getId());
                dto.setAnswer(entity.getAnswer());
                dto.setQuestion(entity.getQuestion());
                listOfDtos.add(dto);
            }
            return listOfDtos;
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("exception", e);
        }
    }

    /**
     * @param username
     * @param token
     * @param articleId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserArticleEditingDto> findUserArticleEditings(String username, String token, Long articleId) throws Exception {
        try {
            logger.info("find users that editing article id {}", articleId);
            List<ArticleEdit> articleEdits = articleEditRepository.findArticleInEditingStatus(articleId);
            if (articleEdits == null) {
//                throw new NotFoundUserArticleEditingException("no user found that editing article id {}");
                return new ArrayList<UserArticleEditingDto>();
            }
            if (articleEdits.isEmpty()) {
//                throw new NotFoundUserArticleEditingException("no user found that editing article id {}");
                return new ArrayList<UserArticleEditingDto>();
            }

            List<String> userList = new ArrayList<>();
            articleEdits.forEach(e -> userList.add(e.getUsername()));
            logger.debug("list of user that editing article id {} ---> {}", articleId, userList);

            // get user profile from oauth server
            ResponseEntity<ApiResponseWrapper.RestResponse<List<UserProfileDto>>> restResponse = null;
            try {
                restResponse = pakarOauthClient.getListUserProfile(BEARER + token, username, userList);
                logger.debug("response data getListUserProfile {}", restResponse);
            } catch (Exception e) {
                logger.error("call remote list user profile failed", e);
                throw new OauthApiClientException("failed call oauth api client");
            }
            List<UserArticleEditingDto> userArticleEditingDtos = new ArrayList<>();
            List<UserProfileDto> userProfileDtos = restResponse.getBody().getData();
            userProfileDtos.forEach(e -> {
                UserArticleEditingDto dto_ = new UserArticleEditingDto();
                dto_.setFirstname(e.getFirstname());
                dto_.setFullname(e.getFullname());
                dto_.setLastname(e.getLastname());
                dto_.setUsername(e.getUsername());
                userArticleEditingDtos.add(dto_);
            });
            return userArticleEditingDtos;
        } catch (OauthApiClientException e) {
            logger.error("exception", e);
            throw new OauthApiClientException("exception", e);
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("exception", e);
        }
    }

    /**
     * private class to handle article content
     */
    private class ArticleContentHelper {
        public void verifyUpdateAndSaveContent(ArticleContentDto dto, String username) {
            dto.getBreadcumbArticleContentDtos().clear();
            Optional<ArticleContent> entity = articleContentRepository.findById(dto.getId());
            if (entity.isEmpty()) {
                logger.info("save new article content {}", dto.toString());
                ArticleContent _entity = new ArticleContent();
                _entity.setId(dto.getId());
                _entity.setCreatedBy(username);
                _entity.setParent(dto.getParent());
                Optional<Article> artOpt = articleRepository.findById(dto.getArticleId());
                _entity.setArticle(artOpt.get());
                _entity.setSort(dto.getSort());
                _entity.setLevel(dto.getLevel());
                _entity.setName(dto.getTitle());
                _entity.setTopicCaption(dto.getTopicTitle());
                _entity.setTopicContent(dto.getTopicContent());
                _entity.setDescription(dto.getIntro());

                _entity = articleContentRepository.save(_entity);

                // reset list parent
                logger.info("get list parent article content");
                // get list parent of articleContent
                logger.debug("get article content parent for article content id {}", _entity.getId());
                List<ArticleContent> breadcumbs = articleContentRepository.findParentListById(_entity.getId());
                breadcumbs.forEach(e -> {
                    BreadcumbArticleContentDto bcDto = new BreadcumbArticleContentDto();
                    bcDto.setId(e.getId());
                    bcDto.setName(e.getName());
                    bcDto.setLevel(e.getLevel());
                    dto.getBreadcumbArticleContentDtos().add(bcDto);
                });

                logger.debug("sorted list parent content");
                // sorting bread crumb
                Collections.sort(dto.getBreadcumbArticleContentDtos(), new Comparator<BreadcumbArticleContentDto>() {
                    @Override
                    public int compare(BreadcumbArticleContentDto o1, BreadcumbArticleContentDto o2) {
                        logger.debug("level 1 {}", o1.getLevel());
                        logger.debug("level 2 {}", o2.getLevel());
                        return o1.getLevel().intValue() - o2.getLevel().intValue();
                    }
                });
            } else {
                logger.debug("sort data {}", dto.getSort());
                ArticleContent _entity = entity.get();
                _entity.setModifyBy(username);
                _entity.setModifyDate(new Date());
                _entity.setParent(dto.getParent());
                _entity.setSort(dto.getSort());
                _entity.setLevel(dto.getLevel());
                _entity.setName(dto.getTitle());
                _entity.setTopicCaption(dto.getTopicTitle());
                _entity.setTopicContent(dto.getTopicContent());
                _entity.setDescription(dto.getIntro());

                articleContentRepository.save(_entity);
            }
        }
    }

    /**
     * @param searchDto
     * @return
     * @throws Exception
     */
    @Override
    public Page<SuggestionArticleDto> searchSuggestion(SearchSuggestionDto searchDto) throws Exception {
        try {
            Page<Article> searchResultPage = null;
            logger.info("search related article");
            if (searchDto.getPage() == null) {
                searchDto.setPage(0L);
            }

            if (searchDto.getPage() == 0) {
                searchDto.setPage(0L);
            }
            Pageable pageable = PageRequest.of(searchDto.getPage().intValue() - 1, searchDto.getSize().intValue());
            if (searchDto.getKeyword() == null || searchDto.getKeyword() == "") {
                searchResultPage = suggestionArticleRepository.findSuggestionArticleWithoutKey(searchDto.getExclude(), searchDto.getStructureId(), pageable);
            } else {
                searchResultPage = suggestionArticleRepository.findSuggestionArticles(searchDto.getExclude(), searchDto.getKeyword(), searchDto.getStructureId(), pageable);
            }
            logger.debug("total items {}", searchResultPage.getTotalElements());
            logger.debug("total contents {}", searchResultPage.getContent().size());
            return new ToDoMapperSuggestion().mapEntityPageIntoDTOPage(pageable, searchResultPage);

        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("exception", e);
        }
    }

    /**
     * @param reqCancelDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = {Exception.class
            , DataNotFoundException.class})
    public Boolean cancelEditArticle(String token, String username, RequestCancelEditDto reqCancelDto) throws Exception {
        try {
            logger.info("process Batal Ubah Article");
            ArticleVersion lastVersion = null;

            // get user profile from oauth server
            ResponseEntity<ApiResponseWrapper.RestResponse<List<UserProfileDto>>> restResponseUp = null;
            restResponseUp = pakarOauthClient.getListUserProfile(BEARER + token, username, Arrays.asList(new String[]{reqCancelDto.getUsername()}));
            if (!restResponseUp.getBody().getApiStatus().getCode().equalsIgnoreCase(Constant.OK_ACK)) {
                throw new OauthApiClientException("call oauth api client is failed");
            }
            lastVersion = articleVersionRepository.findLastTimeStampByFnModifier(reqCancelDto.getId(), restResponseUp.getBody().getData().get(0).getFullname());

//            lastVersion = articleVersionRepository.findLastTimeStampByUsername(reqCancelDto.getId(), reqCancelDto.getUsername());
            if (lastVersion == null) {
                throw new DataNotFoundException("Not found last version article");
            }
            logger.info("last version batal ubah " + lastVersion);
            articleVersionRepository.delete(lastVersion);

            // delete status editor
            ArticleEdit articleEdit = null;
            articleEdit = articleEditRepository.findByUsername(reqCancelDto.getId(), reqCancelDto.getUsername());
            logger.info("article edit on batal ubah " + articleEdit);
            articleEditRepository.delete(articleEdit);

            // delete content clone for user
            Iterable<ArticleContentClone> contentClones = articleContentCloneRepository.findsByArticleId(reqCancelDto.getId(), reqCancelDto.getUsername());
            logger.info("content clone on batal ubah " + contentClones);
            articleContentCloneRepository.deleteAll(contentClones);

            return Boolean.TRUE;
        } catch (OauthApiClientException e) {
            logger.error("fail to call Oauth ", e);
            throw new Exception("Data Not Found", e);
        } catch (DataNotFoundException e) {
            logger.error("Data Not Found", e);
            throw new Exception("Data Not Found", e);
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("exception", e);
        }
    }

    /**
     *
     */
    private class ToDoMapperSuggestion {
        public List<SuggestionArticleDto> mapEntitiesIntoDTOs(Iterable<Article> entities) {
            List<SuggestionArticleDto> dtos = new ArrayList<>();
            entities.forEach(e -> dtos.add(mapEntityIntoDTO(e)));
            return dtos;
        }

        /*

         */
        public SuggestionArticleDto mapEntityIntoDTO(Article entity) {
            SuggestionArticleDto dto = new SuggestionArticleDto();

//            dto.setCreatedBy(entity.getCreatedBy());
//            dto.setCreatedDate(entity.getCreatedDate());
            dto.setId(entity.getId());
            dto.setTitle(entity.getJudulArticle());
            return dto;
        }

        /**
         * Transforms {@code Page<ENTITY>} objects into {@code Page<DTO>} objects.
         *
         * @param pageRequest The information of the requested page.
         * @param source      The {@code Page<ENTITY>} object.
         * @return The created {@code Page<DTO>} object.
         */
        public Page<SuggestionArticleDto> mapEntityPageIntoDTOPage(Pageable pageRequest, Page<Article> source) {
            List<SuggestionArticleDto> dtos = mapEntitiesIntoDTOs(source.getContent());
            return new PageImpl<>(dtos, pageRequest, source.getTotalElements());
        }
    }
}