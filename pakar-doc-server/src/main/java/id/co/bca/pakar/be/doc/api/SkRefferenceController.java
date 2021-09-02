package id.co.bca.pakar.be.doc.api;

import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dto.RequestSkReffDto;
import id.co.bca.pakar.be.doc.dto.SkReffDto;
import id.co.bca.pakar.be.doc.service.SkRefferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
public class SkRefferenceController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SkRefferenceService skRefferenceService;

    private Locale DEFAULT_LOCALE = null;

    private Locale locale = DEFAULT_LOCALE;

    public Locale getLocale() {
        return locale;
    }

    @PostMapping(value = "/api/doc/searchSkRefference", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<List<SkReffDto>>> getSkRefferences(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody RequestSkReffDto requestSkReffDto) {
        logger.info("search sk refference process");
        try {
            List<SkReffDto> skReffDtos = skRefferenceService.findSkReffs(requestSkReffDto.getKeyword());
            return this.createResponse(skReffDtos, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<SkReffDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }
}