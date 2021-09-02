package id.co.bca.pakar.be.doc.api;

import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dto.ArticleNotificationDto;
import id.co.bca.pakar.be.doc.dto.SearchNotificationDto;
import id.co.bca.pakar.be.doc.dto.RequestUpdateNotificationDto;
import id.co.bca.pakar.be.doc.service.ArticleNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 */
@CrossOrigin
@RestController
public class ArticleNotificationController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ArticleNotificationService articleNotificationService;

    private Locale DEFAULT_LOCALE = null;

    private Locale locale = DEFAULT_LOCALE;

    public Locale getLocale() {
        return locale;
    }

    /**
     *
     * @param authorization
     * @param username
     * @param searchDto
     * @return
     */
    @PostMapping(value = "/api/doc/getNotification", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<RestResponse<Map<String, Object>>> getNotification(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username,
                                                                             @RequestBody SearchNotificationDto searchDto ) {
        try {
            logger.info("get notification for receiver {}",username);
            Page<ArticleNotificationDto> dtoPage = articleNotificationService.searchNotification(username, getTokenFromHeader(authorization), searchDto);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("list", dtoPage.getContent());
            maps.put("totalElements", dtoPage.getTotalElements());
            maps.put("totalPages", dtoPage.getTotalPages());
            maps.put("currentPage", searchDto.getPage());
            long totalRead = articleNotificationService.totalReadUnread(username, true);
            long totalUnRead = articleNotificationService.totalReadUnread(username, false);
            maps.put("total_read", totalRead);
            maps.put("total_unread", totalUnRead);
            return createResponse(maps, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new HashMap<>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     *
     * @param authorization
     * @param username
     * @param reqDto
     */
    @PostMapping(value = "/api/doc/updateStatusNotification", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<RestResponse<Long>> updateNotification(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username, @RequestBody RequestUpdateNotificationDto reqDto) {
        try {
            logger.info("update notification");
            long ret = articleNotificationService.updateReadNotification(username, reqDto);
            if(ret != -1 )
                return createResponse(ret, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
            else
                return createResponse(-1L, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(-1L, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }
}