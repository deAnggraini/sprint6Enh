package id.co.bca.pakar.be.doc.api;

import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dto.*;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	@GetMapping("/api/v1/doc/theme")
	public ResponseEntity<RestResponse<ThemeDto>> themeLogin() {
		logger.info("theme process");
		try{
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			ThemeDto themeDto = themeService.getThemeList();

			logger.info("themeDto" +themeDto);

			return this.createResponse(themeDto, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);

		}catch (Exception e) {
			logger.error("exception", e);
			return this.createResponse(new ThemeDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], Constant.ApiResponseCode.GENERAL_ERROR.getAction()[1]);
		}

	}

	@GetMapping("/api/doc/theme")
	public ResponseEntity<RestResponse<ThemeDto>> theme(@RequestHeader(name="Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username) {
		logger.info("theme process");
		ThemeDto themeDto = new ThemeDto();
		try{
			logger.info("received token bearer --- " + authorization);
			String tokenValue = "";
			if (authorization != null && authorization.contains("Bearer")) {
				tokenValue = authorization.replace("Bearer", "").trim();

				logger.info("token value request header --- "+tokenValue);
				logger.info("username request header --- "+username);

				HttpHeaders headers = new HttpHeaders();
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

				themeDto = themeService.getThemeList();

				logger.info("themeDto" +themeDto.toString());
			}

			return this.createResponse(themeDto, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);

		}catch (Exception e) {
			logger.error("exception", e);
			return this.createResponse(new ThemeDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], Constant.ApiResponseCode.GENERAL_ERROR.getAction()[1]);
		}

	}

	/*@PostMapping("/api/doc/upload")
	public ResponseEntity<RestResponse<UploadFileDto>> uploadFile(
			@RequestParam("file") MultipartFile file, @RequestHeader(name="Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username, @RequestParam String imageType) {
		logger.info("uploadFile process");
		try {
			logger.info("received token bearer --- " + authorization);
			String tokenValue = "";
			if (authorization != null && authorization.contains("Bearer")) {
				tokenValue = authorization.replace("Bearer", "").trim();

				logger.info("token value request header --- " + tokenValue);
				logger.info("username request header --- " + username);
			}
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			logger.info("image type " + imageType);
			UploadFileDto uploadFileDto = uploadService.storeFile(file, imageType, username);
			return this.createResponse(uploadFileDto, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);

		}catch (Exception e){
			logger.error("exception", e);
			return this.createResponse(new UploadFileDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], Constant.ApiResponseCode.GENERAL_ERROR.getAction()[1]);
		}
	}*/

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
		logger.info("json input value "+jsonString);

		return createResponse(list, "00", "SUCCESS");
	}

	/**
	 * get article templates by structure id and usedBy
	 */
	@PostMapping(value = "/api/doc/templates", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RestResponse<List<ArticleTemplateDto>>> articleTemplates(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username, @RequestBody RequestTemplateDto requestTemplateDto) {
		try {
			logger.info("received token bearer --- " + authorization);
			String tokenValue = "";
			if (authorization != null && authorization.contains("Bearer")) {
				tokenValue = authorization.replace("Bearer", "").trim();

				logger.info("token value request header --- "+tokenValue);
				logger.info("username request header --- "+username);
			}
			logger.info("get article templates by structure id {}", requestTemplateDto.getStructureId());
			List<ArticleTemplateDto> templates = articleTemplateService.findTemplatesByStructureId(tokenValue, requestTemplateDto.getStructureId(), username);
			return createResponse(templates, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);
		} catch (Exception e) {
			logger.error("exception", e);
			return createResponse(new ArrayList<ArticleTemplateDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], Constant.ApiResponseCode.GENERAL_ERROR.getAction()[1]);
		}
	}

	/**
	 * check judul article tidak boleh sama
	 */
	@PostMapping(value = "/api/doc/checkUnique", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RestResponse<Boolean>> checkUnique(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username, @Valid @RequestBody ArticleTitleDto articleTitleDto) {
		try {
			logger.info("verify article title {} exist in database ", articleTitleDto.getJudulArticle());
			Boolean exist = articleService.existArticle(articleTitleDto.getJudulArticle());
			return exist.booleanValue() ? createResponse(exist, Constant.ApiResponseCode.ARTICLE_EXIST_IN_DATABASE.getAction()[0], Constant.ApiResponseCode.ARTICLE_EXIST_IN_DATABASE.getAction()[1]) :
					createResponse(exist, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);
		} catch (Exception e) {
			logger.error("",e);
			return createResponse(Boolean.FALSE, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], Constant.ApiResponseCode.GENERAL_ERROR.getAction()[1]);
		}
	}

	/**
	 *
	 * @param authorization
	 * @param username
	 * @param articleDto
	 * @return
	 */
	@PostMapping(value = "/api/doc/generateArticle", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RestResponse<Long>> generateArticle(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username, @RequestBody BaseArticleDto articleDto) {
		try {
			logger.info("generate article process");
			logger.info("received token bearer --- {}", authorization);
			String tokenValue = "";
			if (authorization != null && authorization.contains("Bearer")) {
				tokenValue = authorization.replace("Bearer", "").trim();

				logger.info("token value request header --- {}",tokenValue);
				logger.info("username request header --- {}",username);
			}
//			logger.info("get article templates by structure id {}", requestTemplateDto.getStructureId());
//			List<ArticleTemplateDto> templates = articleTemplateService.findTemplatesByStructureId(tokenValue, requestTemplateDto.getStructureId(), username);
			return createResponse(1L, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);
		} catch (Exception e) {
			logger.error("exception", e);
			return createResponse(0L, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], Constant.ApiResponseCode.GENERAL_ERROR.getAction()[1]);
		}
	}
}