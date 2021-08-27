package id.co.bca.pakar.be.oauth2.api;

import id.co.bca.pakar.be.oauth2.common.Constant;
import id.co.bca.pakar.be.oauth2.dto.MenuDto;
import id.co.bca.pakar.be.oauth2.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class MenuController extends BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MenuService menuService;

    @GetMapping("/api/auth/menu")
    public ResponseEntity<RestResponse<List<MenuDto>>> menus(@RequestHeader (name="Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username) {
        try {
            String tokenValue = "";
            if (authorization != null && authorization.contains("Bearer")) {
                tokenValue = authorization.replace("Bearer", "").trim();
                logger.info("token value request header --- "+tokenValue);
                logger.info("username request header --- "+username);
            }
            List<MenuDto> menu = menuService.getMenus(tokenValue, username);
            return this.createResponse(menu, Constant.ApiResponseCode.OK.getAction()[0],  messageSource.getMessage("success.response", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return this.createResponse(new ArrayList<MenuDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }
}
