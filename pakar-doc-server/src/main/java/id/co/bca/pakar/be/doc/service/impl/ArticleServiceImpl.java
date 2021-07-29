package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.client.ApiResponseWrapper;
import id.co.bca.pakar.be.doc.client.PakarOauthClient;
import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dao.*;
import id.co.bca.pakar.be.doc.dto.*;
import id.co.bca.pakar.be.doc.exception.*;
import id.co.bca.pakar.be.doc.model.*;
import id.co.bca.pakar.be.doc.service.ArticleService;
import id.co.bca.pakar.be.doc.util.FileUploadUtil;
import id.co.bca.pakar.be.doc.util.TreeArticleContents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static id.co.bca.pakar.be.doc.common.Constant.ArticleWfState.PRE_DRAFT;
import static id.co.bca.pakar.be.doc.common.Constant.ArticleWfState.PUBLISHED;
import static id.co.bca.pakar.be.doc.common.Constant.Headers.BEARER;
import static id.co.bca.pakar.be.doc.common.Constant.Roles.ROLE_ADMIN;
import static id.co.bca.pakar.be.doc.common.Constant.Roles.ROLE_READER;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.article.param-tag:[]}")
    private String paramtTag;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleTemplateRepository articleTemplateRepository;

    @Autowired
    private ArticleTemplateStructureRepository articleTemplateStructureRepository;

    @Autowired
    private ArticleTemplateContentRepository articleTemplateContentRepository;

    @Autowired
    private ArticleContentRepository articleContentRepository;

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
    private ArticleHistoryRepository articleHistoryRepository;

    @Autowired
    private PakarOauthClient pakarOauthClient;

    @Value("${upload.path.category}")
    private String pathCategory;
    @Value("${upload.path.base}")
    private String basePath;

    @Override
    @Transactional
    public Boolean existArticle(String title) {
        try {
            logger.info("verify existence of article title ---> {}", title);
            Boolean exist = articleRepository.existByArticleTitle(title);
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
    @Transactional(rollbackOn = {Exception.class, NotFoundArticleTemplateException.class})
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

            logger.info("populate article");
            Article article = new Article();
            article.setCreatedBy(generateArticleDto.getUsername());
            article.setJudulArticle(generateArticleDto.getJudulArticle());
            article.setArticleTemplate(template.getId());
            article.setArticleUsedBy(generateArticleDto.getUsedBy());
            Structure structure = structureRepository.findStructure(generateArticleDto.getStructureId());
            article.setStructure(structure);
            article.setArticleState(PRE_DRAFT);
            if (template.getTemplateName().trim().toLowerCase().equalsIgnoreCase("empty".toLowerCase())) {
                article.setUseEmptyTemplate(Boolean.TRUE);
            }

            logger.info("populate article contents");
            Iterable<ArticleTemplateContent> templateContents = articleTemplateContentRepository.findByTemplateId(template.getId());
            for (ArticleTemplateContent articleTemplateContent : templateContents) {
                ArticleContent articleContent = new ArticleContent();
                Long seqContentId = articleContentRepository.getContentId();
                logger.info("get sequence content id {}",  seqContentId);
                articleContent.setId(seqContentId);
                articleContent.setCreatedBy(generateArticleDto.getUsername());
                logger.debug("articleTemplateContent.getName() value {}", articleTemplateContent.getName());
                logger.debug("param key {}", generateArticleDto.getParamKey());
                logger.debug("param value {}", generateArticleDto.getParamValue());
                articleContent.setName(replaceTextByParams(articleTemplateContent.getName(), generateArticleDto.getParamKey(), generateArticleDto.getParamValue()));
                articleContent.setLevel(articleTemplateContent.getLevel());
                articleContent.setSort(articleTemplateContent.getSort());
                articleContent.setTopicCaption(articleTemplateContent.getTopicCaption());
                articleContent.setTopicContent(articleTemplateContent.getTopicContent());
                article.getArticleContents().add(articleContent);
                articleContent.setArticle(article);
            }

            logger.info("save article");
            article = articleRepository.save(article);
            logger.info("generate article success");

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

            articleDto = getArticleById(article.getId());
            return articleDto;
        } catch (NotFoundArticleTemplateException e) {
            logger.error("", e);
            throw new Exception("not found article template");
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
    @Override
    @Transactional
    public ArticleDto getArticleById(Long id) throws Exception {
        try {
            Optional<Article> articleOpt = articleRepository.findById(id);

            if (articleOpt.isEmpty()) {
                throw new DataNotFoundException("not found article with id --> " + id);
            }

            Article article = articleOpt.get();
            ArticleDto articleDto = new ArticleDto();
            articleDto.setCreatedBy(article.getCreatedBy());
            articleDto.setCreatedDate(article.getCreatedDate());
            articleDto.setId(article.getId());
            articleDto.setJudulArticle(article.getJudulArticle());
            articleDto.setShortDescription(article.getShortDescription());
            List<ArticleContentDto> articleContentDtos = new TreeArticleContents().menuTree(mapToListArticleContentDto(article.getArticleContents()));
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

            // get current structure
            Optional<Structure> currStructOpt = structureRepository.findById(article.getStructure().getId());
            if(currStructOpt.isEmpty()) {
                throw new DataNotFoundException("not found article with id --> " + article.getStructure().getId());
            }
            Structure currStruct = currStructOpt.get();
            BreadcumbStructureDto bcDto = new BreadcumbStructureDto();
            bcDto.setId(currStruct.getId());
            bcDto.setName(currStruct.getStructureName());
            bcDto.setLevel(currStruct.getLevel());
            articleDto.getStructureParentList().add(bcDto);

            // get list parent of new structure
            Long parentId = article.getStructure().getParentStructure();
            boolean parentStatus = Boolean.TRUE;
            do {
                Optional<Structure> parentStructure = structureRepository.findById(parentId);
                if (!parentStructure.isEmpty()) {
                    Structure _parent = parentStructure.get();
                    parentId = _parent.getParentStructure();
                    bcDto = new BreadcumbStructureDto();
                    bcDto.setId(_parent.getId());
                    bcDto.setName(_parent.getStructureName());
                    bcDto.setLevel(_parent.getLevel());
                    articleDto.getStructureParentList().add(bcDto);
                    if (parentId == null)
                        parentStatus = Boolean.FALSE;
                    else if (parentId.longValue() == 0)
                        parentStatus = Boolean.FALSE;
                } else {
                    parentStatus = Boolean.FALSE;
                }
            } while (parentStatus);

            // sorting bread crumb
            Collections.sort(articleDto.getStructureParentList(), new Comparator<BreadcumbStructureDto>() {
                @Override
                public int compare(BreadcumbStructureDto o1, BreadcumbStructureDto o2) {
                    return o1.getLevel().intValue() - o2.getLevel().intValue();
                }
            });
            return articleDto;
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
    @Transactional(rollbackOn = {Exception.class})
    public ArticleResponseDto saveArticle(MultipartArticleDto articleDto) throws Exception {
        ArticleResponseDto articleResponseDto = new ArticleResponseDto();
        try {
            logger.info("save article process");
            Optional<Article> articleOpt = articleRepository.findById(articleDto.getId());
            if (articleOpt.isEmpty()) {
                logger.info("not found article data with id {}", articleDto.getId());
                throw new DataNotFoundException("data not found");
            }

            Article article = articleOpt.get();
            article.setModifyBy(articleDto.getUsername());
            article.setModifyDate(new Date());
            article.setArticleState(Constant.ArticleWfState.DRAFT);
            article.setVideoLink(articleDto.getVideoLink());

            article = articleRepository.save(article);

            for (SkReffDto skReffDto : articleDto.getSkReff()) {
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

            articleResponseDto.setId(article.getId());
            articleResponseDto.setVideoLink(article.getVideoLink());
            for (BaseArticleDto dto : articleDto.getRelated()) {
                articleResponseDto.getRelated().add(dto);
            }

            for (ArticleContentDto dto : articleDto.getContents()) {
                articleResponseDto.getContents().add(dto);
            }

            for (SkReffDto dto : articleDto.getSkReff()) {
                articleResponseDto.getSkReff().add(dto);
            }

            articleResponseDto.setJudulArticle(article.getJudulArticle());
            return articleResponseDto;
        } catch (Exception e) {
            logger.error("", e);
            throw new Exception("exception", e);
        }
    }

    /**
     * get content id
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
            Optional<ArticleContent> articleContentOp = articleContentRepository.findById(id);
            if (articleContentOp.isEmpty()) {
                throw new DataNotFoundException("data not found");
            }

            ArticleContent articleContent = articleContentOp.get();
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
     * @param articleContentDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackOn = {Exception.class})
    public ArticleContentDto saveContent(ArticleContentDto articleContentDto) throws Exception {
        try {
            logger.info("process save and update content with id {}", articleContentDto.getId());
            articleContentDto.getBreadcumbArticleContentDtos().clear();
            Optional<ArticleContent> articleContentOpt = articleContentRepository.findById(articleContentDto.getId());
            ArticleContent articleContent = null;
            if(articleContentOpt.isEmpty()) {
                logger.debug("not found article content with id {}, create new entity", articleContentDto.getId());
                articleContent = new ArticleContent();
                articleContent.setId(articleContentDto.getId());
                articleContent.setCreatedBy(articleContentDto.getUsername());
            } else {
                logger.debug("article content with id {} has found, update entity", articleContentDto.getId());
                articleContent = articleContentOpt.get();
                articleContent.setModifyBy(articleContentDto.getUsername());
                articleContent.setModifyDate(new Date());
            }

            articleContent.setName(articleContentDto.getTitle());
            articleContent.setDescription(articleContentDto.getIntroduction());
            articleContent.setTopicCaption(articleContentDto.getTopicTitle());
            articleContent.setTopicContent(articleContentDto.getTopicContent());
            articleContent.setSort(articleContentDto.getOrder());
            articleContent.setLevel(articleContentDto.getLevel());
            Optional<ArticleContent> parentOpt = articleContentRepository.findById(articleContentDto.getParent());
            if (parentOpt.isEmpty()) {
                throw new DataNotFoundException("data not found");
            }

            articleContent.setParent(parentOpt.get().getId());
            Optional<Article> articleOpt = articleRepository.findById(articleContentDto.getArticleId());
            if (articleOpt.isEmpty()) {
                throw new DataNotFoundException("data not found");
            }
            Article article = articleOpt.get();
            articleContent.setArticle(article);
            logger.info("save article content to db");
            articleContent = articleContentRepository.save(articleContent);

            if(!article.getArticleState().equalsIgnoreCase(PRE_DRAFT)) {
                // save to history
                new ArticleHistoryHelper().populateArticleHistory();
            }

            // reset list parent
            logger.info("get list parent article content");
            // get list parent of articleContent
            Long parentId = articleContent.getParent();
            boolean parentStatus = Boolean.TRUE;
            do {
                logger.debug("find article content with parent id {}", parentId);
                Optional<ArticleContent> parentContent = articleContentRepository.findById(parentId);
                if (!parentContent.isEmpty()) {
                    ArticleContent _parent = parentContent.get();
                    parentId = _parent.getParent();
                    BreadcumbArticleContentDto bcDto = new BreadcumbArticleContentDto();
                    bcDto.setId(_parent.getId());
                    bcDto.setName(_parent.getName());
                    bcDto.setLevel(_parent.getLevel());
                    articleContentDto.getBreadcumbArticleContentDtos().add(bcDto);
                    logger.debug("copy list parent object id:name ---> {}:{} has level value {}", new Object[] {bcDto.getId()
                            ,bcDto.getName(),
                            bcDto.getLevel()});
                    if (parentId == null)
                        parentStatus = Boolean.FALSE;
                    else if (parentId.longValue() == 0)
                        parentStatus = Boolean.FALSE;
                } else {
                    logger.debug("article content with parent id {} not found, break loop", parentId);
                    parentStatus = Boolean.FALSE;
                }
            } while (parentStatus);

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
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            throw new DataNotFoundException("data not found");
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
    @Transactional(rollbackOn = {Exception.class, DataNotFoundException.class})
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
                articleContent.setSort(articleContentDto.getOrder());
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
    @Transactional(rollbackOn = {Exception.class, DataNotFoundException.class, AccesDeniedDeleteContentException.class})
    public Boolean deleteContent(DeleteContentDto deleteContentDto) throws Exception {
        try {
            logger.info("delete content with id {}", deleteContentDto.getContentId());
            Optional<ArticleContent> articleContentOpt = articleContentRepository.findById(deleteContentDto.getContentId());
            if (articleContentOpt.isEmpty()) {
                logger.info("not found article content with id {}", deleteContentDto.getContentId());
                throw new DataNotFoundException("data not found");
            }
            logger.debug("call get roles api with token {}", deleteContentDto.getContentId());
            ResponseEntity<ApiResponseWrapper.RestResponse<List<String>>> restResponse = pakarOauthClient.getRoles(BEARER + deleteContentDto.getToken(), deleteContentDto.getUsername());
            logger.debug("response api request {}", restResponse);

            ArticleContent articleContent = articleContentOpt.get();
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
            if(articleOpt.isEmpty()) {
                logger.info("not found article from content with id {}", deleteContentDto.getContentId());
                throw new DataNotFoundException("data not found");
            }

            Article _article = articleOpt.get();
            if(_article.getArticleState().toLowerCase().equalsIgnoreCase(PUBLISHED)) {
                List<ArticleContent> children = articleContentRepository.findContentChildrenAndOwnRowByParentId(articleContent.getId());
                for (ArticleContent content : children) {
                    logger.debug("content level {} and title {}", content.getLevel(), content.getName());
                    content.setModifyBy(deleteContentDto.getUsername());
                    content.setModifyDate(new Date());
                    content.setDeleted(Boolean.TRUE);
                    articleContentRepository.save(content);
                }
                // save to history
                logger.info("save to history");
                ArticleHistory articleHistory = new ArticleHistory();
                articleHistory.setCreatedBy(deleteContentDto.getUsername());
                articleHistoryRepository.save(articleHistory);
            } else {
                // delete from row in db
                logger.debug("delete from db current id and children");
                List<ArticleContent> children = articleContentRepository.findContentChildrenAndOwnRowByParentId(articleContent.getId());
                for (ArticleContent content : children) {
                    logger.debug("delete content title ---> id {}", content.getName(), content.getId());
                    articleContentRepository.delete(content);
                }
            }
            return Boolean.TRUE;
        } catch (AccesDeniedDeleteContentException e) {
            logger.error("exception", e);
            throw new AccesDeniedDeleteContentException("has no authorize delete content level 1");
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            throw new DataNotFoundException("data not found for content id "+deleteContentDto.getContentId());
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
            if(searchDto.getPage() == null) {
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
    private List<ArticleContentDto> mapToListArticleContentDto(Iterable<ArticleContent> iterable) {
        List<ArticleContentDto> listOfContents = new ArrayList<>();
        for (ArticleContent content : iterable) {
            ArticleContentDto contentDto = new ArticleContentDto();
            contentDto.setId(content.getId());
            contentDto.setLevel(content.getLevel());
            contentDto.setOrder(content.getSort());
            contentDto.setTitle(content.getName());
            if (content.getLevel().intValue() == 1)
                contentDto.setIntroduction(content.getDescription());
            contentDto.setParent(content.getParent());
            contentDto.setArticleId(content.getArticle().getId());
            contentDto.setTopicTitle(content.getTopicCaption());
            contentDto.setTopicContent(content.getTopicContent());
            listOfContents.add(contentDto);
        }
        // sorting article content
        Collections.sort(listOfContents, new Comparator<ArticleContentDto>() {
            @Override
            public int compare(ArticleContentDto o1, ArticleContentDto o2) {
                return o1.getOrder().intValue() - o2.getOrder().intValue();
            }
        });
        return listOfContents;
    }

    /**
     *
     * @param content
     * @return
     */
    private ArticleContentDto mapToArticleContentDto(ArticleContent content) {
        ArticleContentDto contentDto = new ArticleContentDto();
        contentDto.setId(content.getId());
        contentDto.setLevel(content.getLevel());
        contentDto.setOrder(content.getSort());
        contentDto.setTitle(content.getName());
        if (content.getLevel().intValue() == 1)
            contentDto.setIntroduction(content.getDescription());
        contentDto.setParent(content.getParent());
        contentDto.setArticleId(content.getArticle().getId());
        return contentDto;
    }

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
            List<FaqDto> searchResult = articleRepository.findFAQ(requestFAQDto);
            return searchResult;
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("exception", e);
        }
    }

    private class ArticleHistoryHelper {
        public ArticleHistory populateArticleHistory() {
            return new ArticleHistory();
        }

        public ArticleContentHistory populateArticleContentHistory() {
            return new ArticleContentHistory();
        }
    }
}
