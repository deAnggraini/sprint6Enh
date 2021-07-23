package id.co.bca.pakar.be.doc.api;

import id.co.bca.pakar.be.doc.api.validator.MultiArticleContentValidator;
import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dto.*;
import id.co.bca.pakar.be.doc.exception.AccesDeniedDeleteContentException;
import id.co.bca.pakar.be.doc.exception.DataNotFoundException;
import id.co.bca.pakar.be.doc.exception.NotFoundArticleTemplateException;
import id.co.bca.pakar.be.doc.service.ArticleService;
import id.co.bca.pakar.be.doc.service.ArticleTemplateService;
import id.co.bca.pakar.be.doc.service.ThemeService;
import id.co.bca.pakar.be.doc.service.UploadService;
import id.co.bca.pakar.be.doc.util.JSONMapperAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
    private MultiArticleContentValidator multiArticleContentValidator;

    @GetMapping("/api/v1/doc/theme")
    public ResponseEntity<RestResponse<ThemeDto>> themeLogin() {
        logger.info("theme process");
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            ThemeDto themeDto = themeService.getThemeList();

            logger.info("themeDto" + themeDto);

            return this.createResponse(themeDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, new Locale("en", "US")));

        } catch (Exception e) {
            logger.error("exception", e);
            return this.createResponse(new ThemeDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, new Locale("en", "US")));
        }

    }

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

            return this.createResponse(themeDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, new Locale("en", "US")));

        } catch (Exception e) {
            logger.error("exception", e);
            return this.createResponse(new ThemeDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, new Locale("en", "US")));
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
     * get article templates by structure id and usedBy
     */
    @PostMapping(value = "/api/doc/templates", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<List<ArticleTemplateDto>>> articleTemplates(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody RequestTemplateDto requestTemplateDto) {
        try {
            logger.info("received token bearer --- " + authorization);
            logger.info("get article templates by structure id {}", requestTemplateDto.getStructureId());
            List<ArticleTemplateDto> templates = articleTemplateService.findTemplates(getTokenFromHeader(authorization), username);
            return createResponse(templates, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, new Locale("en", "US")));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<ArticleTemplateDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, new Locale("en", "US")));
        }
    }

    /**
     * check judul article tidak boleh sama
     */
    @PostMapping(value = "/api/doc/checkUnique", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<Boolean>> checkUnique(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @Valid @RequestBody ArticleTitleDto articleTitleDto) {
        try {
            logger.info("verify article title {} exist in database ", articleTitleDto.getJudulArticle());
            Boolean exist = articleService.existArticle(articleTitleDto.getJudulArticle());
            return exist.booleanValue() ? createResponse(exist, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("title.article.exist", null, new Locale("en", "US"))) :
                    createResponse(exist, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, new Locale("en", "US")));
        } catch (Exception e) {
            logger.error("", e);
            return createResponse(Boolean.FALSE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, new Locale("en", "US")));
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
            return createResponse(articleDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, new Locale("en", "US")));
        } catch (NotFoundArticleTemplateException e) {
            logger.error("exception", e);
            return createResponse(new ArticleDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("article.templete.not.found", null, new Locale("en", "US")));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArticleDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, new Locale("en", "US")));
        }
    }

    /**
     * @param authorization
     * @param username
     * @param id
     * @return
     */
    @GetMapping(value = "/api/doc/getArticle", produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<ArticleDto>> geArticle(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestParam(name = "id") Long id) {
        try {
            logger.info("generate article process");
            logger.info("received token bearer ---> {}", authorization);
            ArticleDto articleDto = articleService.getArticleById(id);
            return createResponse(articleDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, new Locale("en", "US")));
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new ArticleDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("data.not.found", null, new Locale("en", "US")));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArticleDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, new Locale("en", "US")));
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
    public ResponseEntity<RestResponse<RelatedArticleDto>> searchRelatedArticle(@RequestHeader("Authorization") String authorization,
                                                                                @RequestHeader(name = "X-USERNAME") String username,
                                                                                @RequestBody SearchDto searchDto) {
        try {
            logger.info("search related articles process");
            logger.info("received token bearer --- {}", authorization);
            searchDto.setUsername(username);
            searchDto.setToken(getTokenFromHeader(authorization));
            RelatedArticleDto articleDto = articleService.search(searchDto);
            return createResponse(articleDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, new Locale("en", "US")));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new RelatedArticleDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, new Locale("en", "US")));
        }
    }

//	@PostMapping(value = "/api/doc/saveArticle", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
//			MediaType.APPLICATION_JSON_VALUE })
//	public ResponseEntity<RestResponse<ArticleDto>> saveArticle(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username, @ModelAttribute MultipartArticleDto articleDto, BindingResult bindingResult) {
//		try {
//			logger.info("save article process");
//			logger.info("received token bearer --- {}", authorization);
//			articleDto.setUsername(username);
//			articleDto.setToken(getTokenFromHeader(authorization));
//			ArticleDto _articleDto = articleService.saveArticle(articleDto);
//			return createResponse(_articleDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, new Locale("en", "US")));
//		} catch (Exception e) {
//			logger.error("exception", e);
//			return createResponse(new ArticleDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, new Locale("en", "US")));
//		}
//	}

    @GetMapping(value = "/api/doc/getContentId", produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<Long>> getContentId(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody BaseDto baseDto) {
        try {
            logger.info("get content id");
            logger.info("received token bearer --- {}", authorization);
            baseDto.setUsername(username);
            baseDto.setToken(getTokenFromHeader(authorization));
            Long contentId = articleService.getContentId(baseDto);
            return createResponse(contentId, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, new Locale("en", "US")));
        } catch (AccesDeniedDeleteContentException e) {
            logger.error("exception", e);
            return createResponse(0L, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("access.denied.delete.content", new Object[]{username}, Locale.ENGLISH));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(0L, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, new Locale("en", "US")));
        }
    }

    @PostMapping(value = "/api/doc/saveContent", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<ArticleContentDto>> saveArticleContent(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @Valid @RequestBody ArticleContentDto articleContentDto) {
        try {
            logger.info("save article content");
            logger.info("received token bearer --- {}", authorization);
            articleContentDto.setUsername(username);
            articleContentDto.setToken(getTokenFromHeader(authorization));
            articleContentDto = articleService.saveContent(articleContentDto);
            return createResponse(articleContentDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, new Locale("en", "US")));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArticleContentDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, new Locale("en", "US")));
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
            // TODO implement saveBatchContents
            logger.info("save batch article content");
            logger.info("received token bearer --- {}", authorization);
            multiArticleContentValidator.validate(articleContentDtos, bindingResult);
            if (bindingResult.hasErrors()) {
                logger.info("binding result " + bindingResult.getAllErrors());
                String errorMessage = "";
                for (Object object : bindingResult.getAllErrors()) {
                    if (object instanceof FieldError) {
                        FieldError fieldError = (FieldError) object;
                        errorMessage = messageSource.getMessage(fieldError.getCode(), null, new Locale("en", "US"));
                    }
                }
                return createResponse(new ArrayList<ArticleContentDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], errorMessage);
            }
//            articleContentDto.setUsername(username);
//            articleContentDto.setToken(getTokenFromHeader(authorization));
            articleService.saveBatchContents(articleContentDtos);
            return createResponse(new ArrayList<ArticleContentDto>(), Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, new Locale("en", "US")));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<ArticleContentDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, new Locale("en", "US")));
        }
    }

    @PostMapping(value = "/api/doc/deleteContent", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<Boolean>> deleteArticleContent(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @Valid @RequestBody DeleteContentDto deleteContentDto) {
        try {
            logger.info("save article content");
            logger.info("received token bearer --- {}", authorization);
            deleteContentDto.setUsername(username);
            deleteContentDto.setToken(getTokenFromHeader(authorization));
            Boolean status = articleService.deleteContent(deleteContentDto);
            return createResponse(status, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, Locale.ENGLISH));
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(Boolean.FALSE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("data.not.found", null, Locale.ENGLISH));
        } catch (AccesDeniedDeleteContentException e) {
            logger.error("exception", e);
            return createResponse(Boolean.FALSE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("access.denied.delete.content", new Object[]{username}, Locale.ENGLISH));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(Boolean.FALSE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, Locale.ENGLISH));
        }
    }
}