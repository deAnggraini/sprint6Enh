package id.co.bca.pakar.be.doc.api;

import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dto.*;
import id.co.bca.pakar.be.doc.exception.DataNotFoundException;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@CrossOrigin
@RestController
public class SkRefferenceController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ThemeService themeService;

	@Autowired
	private UploadService uploadService;

	@Autowired
	private ArticleTemplateService articleTemplateService;

	@Autowired
	private ArticleService articleService;

	@PostMapping("/api/doc/skRefference")
	public ResponseEntity<RestResponse<List<SkReffDto>>> getSkRefferences(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username) {
		logger.info("sk refference process");
		try{
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			ThemeDto themeDto = themeService.getThemeList();

			logger.info("themeDto" +themeDto);

			return this.createResponse(new ArrayList<SkReffDto>(), Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, new Locale("en", "US")));

		}catch (Exception e) {
			logger.error("exception", e);
			return createResponse(new ArrayList<SkReffDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, new Locale("en", "US")));
		}
	}
}