package id.co.bca.pakar.be.oauth2.api;

import id.co.bca.pakar.be.oauth2.common.Constant;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import id.co.bca.pakar.be.oauth2.dto.MenuDto;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;
import java.util.Arrays;
import id.co.bca.pakar.be.oauth2.service.MenuService;

@RestController
@CrossOrigin
public class MenuController extends BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MenuService menuService;

    @GetMapping(value = "/api/auth/menu", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<RestResponse<MenuDto>> menu(@RequestHeader (name="Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username) {
        try {
            String tokenValue = "";
            if (authorization != null && authorization.contains("Bearer")) {
                tokenValue = authorization.replace("Bearer", "").trim();
                logger.info("token value request header --- "+tokenValue);
                logger.info("username request header --- "+username);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            List<MenuDto> menu = menuService.getMenu(tokenValue, username);
            logger.info("menu controller " + menu.get(0));
//            RestResponse<MenuDto> tResponse = new RestResponse(menu, "00", "Menu Success Load");
//            return new ResponseEntity<RestResponse<MenuDto>>(tResponse, HttpStatus.OK);
            return this.createResponse(new MenuDto(), Constant.ApiResponseCode.MENU_PROFILE_SUCCESS.getAction()[0], Constant.ApiResponseCode.MENU_PROFILE_SUCCESS.getAction()[1]);

        } catch (Exception e) {
            logger.error("exception", e);
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            RestResponse<MenuDto> tResponse = new RestResponse(new MenuDto(), "01", "Menu Failed Load");
            return ResponseEntity.accepted().headers(headers).body(tResponse);
        }
    }

    @GetMapping("/api/auth/menus")
    public ResponseEntity<RestResponse<List<MenuDto>>> menus(@RequestHeader (name="Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username) {
        try {
            String tokenValue = "";
            if (authorization != null && authorization.contains("Bearer")) {
                tokenValue = authorization.replace("Bearer", "").trim();
                logger.info("token value request header --- "+tokenValue);
                logger.info("username request header --- "+username);
            }
            List<MenuDto> menu = menuService.getMenus(tokenValue, username);
            logger.info("menu controller {}", menu.get(0));
            return this.createResponse(menu, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);
        } catch (Exception e) {
            logger.error("exception", e);
            return this.createResponse(new ArrayList<MenuDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], Constant.ApiResponseCode.GENERAL_ERROR.getAction()[1]);
        }
    }
}
