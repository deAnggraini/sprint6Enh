package id.co.bca.pakar.be.doc.api;

import id.co.bca.pakar.be.doc.api.validator.MultiArticleContentValidator;
import id.co.bca.pakar.be.doc.api.validator.MultipartArticleValidator;
import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dto.*;
import id.co.bca.pakar.be.doc.exception.*;
import id.co.bca.pakar.be.doc.service.*;
import id.co.bca.pakar.be.doc.util.JSONMapperAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 *
 */
@CrossOrigin
@RestController
public class ArticleController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ThemeService themeService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private ArticleTemplateService articleTemplateService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleContentService articleContentService;

    @Autowired
    private MultiArticleContentValidator multiArticleContentValidator;

    @Autowired
    private MultipartArticleValidator multipartArticleValidator;

    @Autowired
    private MyPagesService myPagesService;

    @Autowired
    private ArticleVersionService articleVersionService;

    private Locale DEFAULT_LOCALE = null;

    private Locale locale = DEFAULT_LOCALE;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     *
     * @return
     */
    @GetMapping("/api/v1/doc/theme")
    public ResponseEntity<RestResponse<ThemeDto>> themeLogin() {
        logger.info("theme process");
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            ThemeDto themeDto = themeService.getThemeList();

            logger.info("themeDto" + themeDto);

            return this.createResponse(themeDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));

        } catch (Exception e) {
            logger.error("exception", e);
            return this.createResponse(new ThemeDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }

    }

    /**
     *
     * @param authorization
     * @param username
     * @return
     */
    @GetMapping("/api/doc/theme")
    public ResponseEntity<RestResponse<ThemeDto>> theme(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username) {
        logger.info("theme process");
        ThemeDto themeDto = new ThemeDto();
        try {
            logger.info("received token bearer --- " + authorization);
            String tokenValue = "";
            if (authorization != null && authorization.contains("Bearer")) {
                tokenValue = authorization.replace("Bearer", "").trim();

                logger.info("token value request header --- " + tokenValue);
                logger.info("username request header --- " + username);

                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

                themeDto = themeService.getThemeList();

                logger.info("themeDto" + themeDto.toString());
            }

            return this.createResponse(themeDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));

        } catch (Exception e) {
            logger.error("exception", e);
            return this.createResponse(new ThemeDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }

    }

    @PostMapping("/api/doc/recomendation")
    public String recomendation(@RequestBody String message) {
        return String.format("Message was created. Content: %s", message);
    }

    @PostMapping("/api/doc/news")
    public String news(@RequestBody String message) {
        return String.format("Message was created. Content: %s", message);
    }

    @PostMapping("/api/doc/popular")
    public String popular(@RequestBody String message) {
        return String.format("Message was created. Content: %s", message);
    }

    @PostMapping("/api/doc/search")
    public ResponseEntity<RestResponse<List<SearchHistoryDto>>> search(@RequestBody String message) {
        logger.info("search article process");

//		String jsonString = " [\r\n"
//				+ "    {\r\n"
//				+ "        id: 1,\r\n"
//				+ "        parent: 'PAKAR',\r\n"
//				+ "        items: [\r\n"
//				+ "            { id: 1, title: 'Tahapan' },\r\n"
//				+ "            { id: 2, title: 'Tahapan Gold' },\r\n"
//				+ "            { id: 3, title: 'TAHAKA' },\r\n"
//				+ "            { id: 4, title: 'Xpresi' },\r\n"
//				+ "            { id: 5, title: 'Time Loan SME' },\r\n"
//				+ "            { id: 5, title: 'GIRO' }\r\n"
//				+ "        ]\r\n"
//				+ "    },\r\n"
//				+ "    {\r\n"
//				+ "        id: 2,\r\n"
//				+ "        parent: 'FAQ',\r\n"
//				+ "        items: [\r\n"
//				+ "            { id: 11, title: 'Bagaimana solusi ketika Teller melakukan input kode penalti pada nasabah' },\r\n"
//				+ "            { id: 12, title: 'Apakah bisa membuka Deposito dari Data RTGS Masuk?' }\r\n"
//				+ "        ]\r\n"
//				+ "    }\r\n"
//				+ "]";

        // next change to db
        List<SearchHistoryDto> list = new ArrayList<SearchHistoryDto>();
        SearchHistoryDto searcHistoryDto = new SearchHistoryDto();
        searcHistoryDto.setId("1");
        searcHistoryDto.setParent("PAKAR");
        searcHistoryDto.getItems().add(new SearchHistoryItem("1", "Tahapan"));
        searcHistoryDto.getItems().add(new SearchHistoryItem("2", "Tahapan Gold"));
        searcHistoryDto.getItems().add(new SearchHistoryItem("3", "TAHAKA"));
        searcHistoryDto.getItems().add(new SearchHistoryItem("5", "Xpresi"));
        searcHistoryDto.getItems().add(new SearchHistoryItem("5", "Time Loan SME"));
        searcHistoryDto.getItems().add(new SearchHistoryItem("6", "GIRO"));
        list.add(searcHistoryDto);
        searcHistoryDto = new SearchHistoryDto();
        searcHistoryDto.setId("2");
        searcHistoryDto.setParent("FAQ");
        searcHistoryDto.getItems().add(new SearchHistoryItem("1", "Bagaimana solusi ketika Teller melakukan input kode penalti pada nasabah"));
        searcHistoryDto.getItems().add(new SearchHistoryItem("2", "Apakah bisa membuka Deposito dari Data RTGS Masuk?"));
        list.add(searcHistoryDto);
        String jsonString = JSONMapperAdapter.objectToJson(list);
        logger.info("json input value " + jsonString);

        return createResponse(list, "00", "SUCCESS");
    }

    /**
     *
     * @param authorization
     * @param username
     * @param searchDto
     * @return
     */
    @PostMapping(value = "/api/doc/searchArticle", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<Map<String, Object>>> searchArticle(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody SearchPublishedArticleDto searchDto) {
        try {
            logger.info("search article");
            Page<ArticleDto> pageArticleDto = articleVersionService.searchPublishedArticle(searchDto);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("list", pageArticleDto.getContent());
            maps.put("totalElements", pageArticleDto.getTotalElements());
            maps.put("totalPages", pageArticleDto.getTotalPages());
            maps.put("currentPage", searchDto.getPage());
            return createResponse(maps, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        }  catch (MinValuePageNumberException e) {
            logger.error("exception", e);
            return createResponse(new HashMap<>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("paging.minimum.invalid", null, getLocale()));
        }  catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new HashMap<>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    @PostMapping(value = "/api/doc/revertArticleVersion", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<Long>> revertArticleVersion(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username) {
        return null;
    }

    /**
     * get article templates by structure id and usedBy
     */
    @PostMapping(value = "/api/doc/templates", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<List<ArticleTemplateDto>>> articleTemplates(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody RequestTemplateDto requestTemplateDto) {
        try {
            logger.info("received token bearer --- " + authorization);
            logger.info("get article templates by structure id {}", requestTemplateDto.getStructureId());
            List<ArticleTemplateDto> templates = articleTemplateService.findTemplates(getTokenFromHeader(authorization), username);
            return createResponse(templates, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<ArticleTemplateDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     * check judul article tidak boleh sama
     */
    @PostMapping(value = "/api/doc/checkUnique", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<Boolean>> checkUnique(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @Valid @RequestBody ArticleTitleDto articleTitleDto) {
        try {
            logger.info("verify article title {} exist in database ", articleTitleDto.getTitle());
            Boolean exist = articleService.existArticle(articleTitleDto.getTitle(), articleTitleDto.getExlude());
            return exist.booleanValue() ? createResponse(exist, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("title.article.exist", null, getLocale())) :
                    createResponse(exist, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (Exception e) {
            logger.error("", e);
            return createResponse(Boolean.FALSE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     * @param authorization
     * @param username
     * @param generateArticleDto
     * @return
     */
    @PostMapping(value = "/api/doc/generateArticle", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<ArticleDto>> generateArticle(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @Valid @RequestBody GenerateArticleDto generateArticleDto) {
        try {
            logger.info("generate article process");
            logger.info("received token bearer --- {}", authorization);
            generateArticleDto.setUsername(username);
            generateArticleDto.setToken(getTokenFromHeader(authorization));
            ArticleDto articleDto = articleService.generateArticle(generateArticleDto);
            return createResponse(articleDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (DuplicateTitleException e) {
            logger.error("exception", e);
            return createResponse(new ArticleDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("article.title.duplicate", null, getLocale()));
        } catch (NotFoundArticleTemplateException e) {
            logger.error("exception", e);
            return createResponse(new ArticleDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("article.templete.not.found", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArticleDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     *
     * @param authorization
     * @param username
     * @param requestDto
     * @return
     */
    @PostMapping(value = "/api/doc/getArticle", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<ArticleDto>> geArticle(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody GetArticleDto requestDto) {
        try {
            logger.info("get article process");
            logger.info("received token bearer ---> {}", authorization);
            ArticleDto articleDto = articleService.getArticleById(requestDto.getId(), requestDto.getIsEdit(), username, getTokenFromHeader(authorization));
            return createResponse(articleDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (ArticleNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new ArticleDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("article.not.found", null, getLocale()));
        } catch (StructureNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new ArticleDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("structure.not.found", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArticleDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     * search related article
     *
     * @param authorization
     * @param username
     * @return
     */
    @PostMapping(value = "/api/doc/searchRelatedArticle", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<Map<String, Object>>> searchRelatedArticle(@RequestHeader("Authorization") String authorization,
                                                                                  @RequestHeader(name = "X-USERNAME") String username,
                                                                                  @RequestBody SearchDto searchDto) {
        try {
            logger.info("search related articles process");
            logger.info("received token bearer --- {}", authorization);
            searchDto.setUsername(username);
            searchDto.setToken(getTokenFromHeader(authorization));
            Page<RelatedArticleDto> pageArticleDto = articleService.search(searchDto);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("list", pageArticleDto.getContent());
            maps.put("totalElements", pageArticleDto.getTotalElements());
            maps.put("totalPages", pageArticleDto.getTotalPages());
            maps.put("currentPage", searchDto.getPage());
            return createResponse(maps, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new HashMap<>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("data.not.found", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new HashMap<>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     *
     * @param authorization
     * @param username
     * @param articleDto
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/api/doc/saveArticle", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<RestResponse<ArticleResponseDto>> saveArticle(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username, @ModelAttribute MultipartArticleDto articleDto, BindingResult bindingResult) {
        try {
            logger.info("save article process");
            logger.info("received token bearer --- {}", authorization);
            multipartArticleValidator.validate(articleDto, bindingResult);
            if (bindingResult.hasErrors()) {
                logger.info("binding result " + bindingResult.getAllErrors());
                String errorMessage = "";
                for (Object object : bindingResult.getAllErrors()) {
                    if(object instanceof FieldError) {
                        FieldError fieldError = (FieldError) object;
                        errorMessage = messageSource.getMessage(fieldError.getCode(),null, getLocale());
                    }
                }
                return createResponse(new ArticleResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], errorMessage);
            }
            articleDto.setUsername(username);
            articleDto.setToken(getTokenFromHeader(authorization));
            ArticleResponseDto _articleDto = articleService.saveArticle(articleDto);
            return createResponse(_articleDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new ArticleResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("article.not.found", null, getLocale()));
        } catch (DuplicateTitleException e) {
            logger.error("exception", e);
            return createResponse(new ArticleResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("title.article.exist", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArticleResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     *
     * @param authorization
     * @param username
     * @param cancelDto
     * @return
     */
    @PostMapping(value = "/api/doc/cancelArticle", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<RestResponse<Boolean>> cancelArticle(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username, @RequestBody CancelDto cancelDto) {
        try {
            logger.info("cancel article process");
            logger.info("received token bearer --- {}", authorization);
            Boolean status = articleService.cancelArticle(cancelDto.getId(), username, getTokenFromHeader(authorization));
            return createResponse(status, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (DeletePublishedArticleException e) {
            logger.error("exception", e);
            return createResponse(Boolean.TRUE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("published.article.could.not.deleted", null, getLocale()));
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(Boolean.TRUE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("data.not.found", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(Boolean.TRUE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     *
     * @param authorization
     * @param username
     * @param cancelDto
     * @return
     */
    @PostMapping(value = "/api/doc/cancelSendArticle", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<RestResponse<Boolean>> cancelSendArticle(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username, @RequestBody CancelSendArticleDto cancelDto) {
        try {
            logger.info("cancel send article process");
            logger.info("received token bearer --- {}", authorization);
            Boolean status = articleService.cancelSendArticle(cancelDto.getId(), cancelDto.getReceiver(), username, getTokenFromHeader(authorization));
            return createResponse(status, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (ArticleInEditingxception e) {
            logger.error("exception", e);
            return createResponse(Boolean.TRUE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("could.not.cancel.send.article", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(Boolean.TRUE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     * get sequence number of id content
     *
     * @param authorization
     * @param username
     * @return
     */
    @GetMapping(value = "/api/doc/getContentId", produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<Long>> getContentId(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username) {
        try {
            logger.info("get content id");
            logger.info("received token bearer --- {}", authorization);
            Long contentId = articleService.getContentId(username, getTokenFromHeader(authorization));
            return createResponse(contentId, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (AccesDeniedDeleteContentException e) {
            logger.error("exception", e);
            return createResponse(0L, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("access.denied.delete.content", new Object[]{username}, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(0L, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     * get article content by content id
     *
     * @param authorization
     * @param username
     * @param baseDto
     * @return
     */
    @PostMapping(value = "/api/doc/getContentById", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<ArticleContentDto>> getContentById(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody BaseArticleDto baseDto) {
        try {
            logger.info("get content by id");
            logger.info("received token bearer --- {}", authorization);
            baseDto.setUsername(username);
            baseDto.setToken(getTokenFromHeader(authorization));
            ArticleContentDto articleContentDto = articleService.getContentById(baseDto.getId(), baseDto.getUsername());
            return createResponse(articleContentDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (AccesDeniedDeleteContentException e) {
            logger.error("exception", e);
            return createResponse(new ArticleContentDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("access.denied.delete.content", new Object[]{username}, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArticleContentDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     * save content article/accordeon
     * @param authorization
     * @param username
     * @param articleContentDto
     * @return
     */
    @PostMapping(value = "/api/doc/saveContent", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<ArticleContentDto>> saveArticleContent(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @Valid @RequestBody ArticleContentDto articleContentDto) {
        try {
            logger.info("save article content");
            logger.info("received token bearer --- {}", authorization);
            articleContentDto.setUsername(username);
            articleContentDto.setToken(getTokenFromHeader(authorization));
            articleContentDto = articleService.saveContent(articleContentDto);
            return createResponse(articleContentDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (ParentContentNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new ArticleContentDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("content.parent.not.found", null, getLocale()));
        } catch (ArticleNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new ArticleContentDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("article.not.found", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArticleContentDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     * save all accordeon with all chidren
     *
     * @param authorization
     * @param username
     * @param articleContentDtos
     * @return
     */
    @PostMapping(value = "/api/doc/saveBatchContent", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<List<ArticleContentDto>>> saveBatchArticleContent(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody List<ArticleContentDto> articleContentDtos, BindingResult bindingResult) {
        try {
            logger.info("save batch article content");
            logger.info("received token bearer --- {}", authorization);
            multiArticleContentValidator.validate(articleContentDtos, bindingResult);
            if (bindingResult.hasErrors()) {
                logger.info("binding result " + bindingResult.getAllErrors());
                String errorMessage = "";
                for (Object object : bindingResult.getAllErrors()) {
                    if (object instanceof FieldError) {
                        FieldError fieldError = (FieldError) object;
                        errorMessage = messageSource.getMessage(fieldError.getCode(), null, getLocale());
                    }
                }
                return createResponse(new ArrayList<ArticleContentDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], errorMessage);
            }
            articleService.saveBatchContents(articleContentDtos, username, getTokenFromHeader(authorization));
            return createResponse(articleContentDtos, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<ArticleContentDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("data.not.found", null, getLocale()));
        } catch (InvalidLevelException e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<ArticleContentDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("level.article.content.invalid", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<ArticleContentDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     *
     * @param authorization
     * @param username
     * @param deleteContentDto
     * @return
     */
    @PostMapping(value = "/api/doc/deleteContent", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<Boolean>> deleteArticleContent(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @Valid @RequestBody DeleteContentDto deleteContentDto) {
        try {
            logger.info("save article content");
            logger.info("received token bearer --- {}", authorization);
            deleteContentDto.setUsername(username);
            deleteContentDto.setToken(getTokenFromHeader(authorization));
            Boolean status = articleService.deleteContent(deleteContentDto);
            return createResponse(status, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(Boolean.FALSE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("data.not.found", null, getLocale()));
        } catch (ArticleContentNotFoundException e) {
            logger.error("exception", e);
            return createResponse(Boolean.FALSE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("article.content.not.found", new Object[] {deleteContentDto.getContentId()}, getLocale()));
        } catch (AccesDeniedDeleteContentException e) {
            logger.error("exception", e);
            return createResponse(Boolean.FALSE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("access.denied.delete.content", new Object[]{username}, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(Boolean.FALSE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     * cancel article content make article back to previous content that was saved
     * @param authorization
     * @param username
     * @param deleteContentDto
     * @return
     */
    @PostMapping(value = "/api/doc/cancelContent", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<Boolean>> cancelArticleContent(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @Valid @RequestBody DeleteContentDto deleteContentDto) {
        // TODO implement cancel article
        try {
            logger.info("save article content");
            logger.info("received token bearer --- {}", authorization);
            deleteContentDto.setUsername(username);
            deleteContentDto.setToken(getTokenFromHeader(authorization));
            Boolean status = articleService.deleteContent(deleteContentDto);
            return createResponse(status, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(Boolean.FALSE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("data.not.found", null, getLocale()));
        } catch (AccesDeniedDeleteContentException e) {
            logger.error("exception", e);
            return createResponse(Boolean.FALSE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("access.denied.delete.content", new Object[]{username}, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(Boolean.FALSE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     * find FAQ for preview article
     * @param requestFAQDto
     * @return
     */
    @PostMapping(value = "/api/doc/findFAQ", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<List<FaqDto>>> findFAQ(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @Valid @RequestBody RequestFAQDto requestFAQDto) {

        try {
            logger.info("find FAQ");
            List<FaqDto> listFaq = articleService.findFaq(requestFAQDto.getId());
            return createResponse(listFaq, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<FaqDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("data.not.found", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<FaqDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("data.not.found", null, getLocale()));
        }
    }

    /**
     * find Suggestion Article for preview article
     * @param searchDto
     * @return
     */
    @PostMapping(value = "/api/doc/suggestionArticle", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<Map<String, Object>>> findSuggestionArticle(@RequestHeader("Authorization") String authorization,
                                                                                   @RequestHeader(name = "X-USERNAME") String username,
                                                                                   @Valid @RequestBody SearchSuggestionDto searchDto) {
        try {
            logger.info("search suggestion articles process");
            logger.info("received token bearer --- {}", authorization);
            searchDto.setUsername(username);
            searchDto.setToken(getTokenFromHeader(authorization));
            Page<SuggestionArticleDto> pageArticleDto = articleService.searchSuggestion(searchDto);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("list", pageArticleDto.getContent());
            maps.put("totalElements", pageArticleDto.getTotalElements());
            maps.put("totalPages", pageArticleDto.getTotalPages());
            maps.put("currentPage", searchDto.getPage());
            return createResponse(maps, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new HashMap<>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("data.not.found", null, getLocale()));
        } catch(Exception e) {
            logger.error("exception", e);
            return createResponse(new HashMap<>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     *
     * @param authorization
     * @param username
     * @param searchDto
     * @return
     */
    @PostMapping(value = "/api/doc/searchMyPages", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<Map<String, Object>>> searchMyPages(@RequestHeader("Authorization") String authorization,
                                                                                   @RequestHeader(name = "X-USERNAME") String username,
                                                                                    @RequestBody SearchMyPageDto searchDto
                                                                                   ) {
        try {
            logger.info("search My Pages process");
            logger.info("received token bearer --- {}", authorization);
            searchDto.setUsername(username);
            searchDto.setToken(getTokenFromHeader(authorization));
            Page<MyPageDto> pageMyPageDto = myPagesService.searchMyPages(searchDto);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("list", pageMyPageDto.getContent());
            maps.put("totalElements", pageMyPageDto.getTotalElements());
            maps.put("totalPages", pageMyPageDto.getTotalPages());
            maps.put("currentPage", searchDto.getPage());
            return createResponse(maps, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (MinValuePageNumberException e) {
            logger.error("exception", e);
            return createResponse(new HashMap<>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("paging.minimum.invalid", null, getLocale()));
        } catch (OauthApiClientException e) {
            logger.error("exception", e);
            return createResponse(new HashMap<>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("data.not.found", null, getLocale()));
        } catch (WfApiClientException e) {
            logger.error("exception", e);
            return createResponse(new HashMap<>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("fail.request.workflow", null, getLocale()));
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new HashMap<>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("data.not.found", null, getLocale()));
        } catch(Exception e) {
            logger.error("exception", e);
            return createResponse(new HashMap<>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     *
     * @param authorization
     * @param username
     * @return
     */
    @PostMapping(value = "/api/doc/searchContent", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<Map<String, Object>>> searchContent(@RequestHeader("Authorization") String authorization,
                                                                           @RequestHeader(name = "X-USERNAME") String username,
                                                                           @RequestBody SearchMyPageDto searchDto
    ) {
        try {
            logger.info("search Contents process");
            logger.info("received token bearer --- {}", authorization);
            searchDto.setUsername(username);
            searchDto.setToken(getTokenFromHeader(authorization));
            Page<MyPageDto> pageMyPageDto = articleContentService.searchContent(searchDto);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("list", pageMyPageDto.getContent());
            maps.put("totalElements", pageMyPageDto.getTotalElements());
            maps.put("totalPages", pageMyPageDto.getTotalPages());
            maps.put("currentPage", searchDto.getPage());
            return createResponse(maps, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (MinValuePageNumberException e) {
            logger.error("exception", e);
            return createResponse(new HashMap<>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("paging.minimum.invalid", null, getLocale()));
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new HashMap<>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("data.not.found", null, getLocale()));
        } catch (AccesDeniedViewContentsException e) {
            logger.error("exception", e);
            return createResponse(new HashMap<>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("contents.article.not.for.reader", null, getLocale()));
        } catch(Exception e) {
            logger.error("exception", e);
            return createResponse(new HashMap<>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     * get user still editing for some article
     * @param authorization
     * @param username
     * @return
     */
    @PostMapping(value = "/api/doc/getArticleEditing", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<List<UserArticleEditingDto>>> getArticleEditing(@RequestHeader("Authorization") String authorization,
                                                               @RequestHeader(name = "X-USERNAME") String username,
                                                               @RequestBody GetArticleDto dto) {
        try {
            logger.info("search Contents process");
            logger.info("received token bearer --- {}", authorization);
            List<UserArticleEditingDto> articleEditingDtos = articleService.findUserArticleEditings(username, getTokenFromHeader(authorization), dto.getId());
            return createResponse(articleEditingDtos, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch(NotFoundUserArticleEditingException e) {
            logger.info("theres is no user edit article id {}", dto.getId());
            return createResponse(new ArrayList<UserArticleEditingDto>() , Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("none.user.edit.article", new Object[]{dto.getId()}, getLocale()));
        } catch(Exception e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<UserArticleEditingDto>() , Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     * get article version history
     * @param authorization
     * @param username
     * @return
     */
    @PostMapping(value = "/api/doc/getVersionHistoryArticle", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<Map>> getArticleEditing(@RequestHeader("Authorization") String authorization,
                                                               @RequestHeader(name = "X-USERNAME") String username) {
        try {
            logger.info("search Contents process");
            logger.info("received token bearer --- {}", authorization);
//            searchDto.setUsername(username);
//            searchDto.setToken(getTokenFromHeader(authorization));
//            Page<MyPageDto> pageMyPageDto = articleContentService.searchContent(searchDto);
//            Map<String, Object> maps = new HashMap<String, Object>();
//            maps.put("list", pageMyPageDto.getContent());
//            maps.put("totalElements", pageMyPageDto.getTotalElements());
//            maps.put("totalPages", pageMyPageDto.getTotalPages());
//            maps.put("currentPage", searchDto.getPage());
            return createResponse(new HashMap(), Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, null));
        } catch(Exception e) {
            logger.error("exception", e);
            return createResponse(new HashMap<>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, null));
        }
    }


    /**
     *
     * @param authorization
     * @param username
     * @param requestDeleteDto
     * @return
     */
    @PostMapping(value = "/api/doc/deleteArticle", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<RestResponse<Boolean>> deleteArticle(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username, @RequestBody RequestDeleteDto requestDeleteDto) {
        try {
            logger.info("delete article process ");
            logger.info("received token bearer --- {}", authorization);
            Boolean status = articleService.deleteArticle(requestDeleteDto, username, getTokenFromHeader(authorization));
            return createResponse(status, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(Boolean.FALSE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("data.not.found", null, getLocale()));
        } catch (WfApiClientException e) {
            logger.error("exception", e);
            return createResponse(Boolean.FALSE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("fail.request.workflow", null, getLocale()));
        }catch (Exception e) {
            logger.error("exception", e);
            return createResponse(Boolean.FALSE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     *
     * @param authorization
     * @param username
     * @param reqCancelEdit
     * @return
     */
    @PostMapping(value = "/api/doc/cancelEditArticle", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<RestResponse<Boolean>> cancelEditArticle(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username, @RequestBody RequestCancelEditDto reqCancelEdit) {
        try {
            logger.info("cancel Edit Article Process ");
            logger.info("received token bearer --- {}", authorization);
            Boolean status = articleService.cancelEditArticle(authorization, username, reqCancelEdit);
            return createResponse(status, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (OauthApiClientException e) {
            logger.error("exception", e);
            return createResponse(Boolean.FALSE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("fail.request.oauth", null, getLocale()));
        }catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(Boolean.FALSE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("data.not.found", null, getLocale()));
        }catch (Exception e) {
            logger.error("exception", e);
            return createResponse(Boolean.FALSE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }
}