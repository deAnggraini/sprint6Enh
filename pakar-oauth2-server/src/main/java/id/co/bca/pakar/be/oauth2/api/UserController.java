package id.co.bca.pakar.be.oauth2.api;

import id.co.bca.pakar.be.oauth2.common.Constant;
import id.co.bca.pakar.be.oauth2.dto.*;
import id.co.bca.pakar.be.oauth2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class UserController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    /**
     * decode token object to authorization object
     *
     * @param principal
     * @return
     */
    @GetMapping("/api/auth/me")
    public ResponseEntity<RestResponse<Principal>> get(final Principal principal) {
        if (principal != null)
            return createResponse(principal, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);
        else
            return createResponse(principal, Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], Constant.ApiResponseCode.GENERAL_ERROR.getAction()[1]);
    }

    /**
     * @param authorization
     * @param username
     * @return
     */
    @PostMapping(value = "/api/auth/getUser", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<LoggedinDto>> getUser(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username) {
        try {
            logger.info("received token bearer --- " + authorization);
            String tokenValue = "";
            if (authorization != null && authorization.contains("Bearer")) {
                tokenValue = authorization.replace("Bearer", "").trim();

                logger.info("token value request header --- " + tokenValue);
                logger.info("username request header --- " + username);
            }
//            HttpHeaders headers = new HttpHeaders();
//            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            logger.info("get user by token ");
            LoggedinDto loggedInDto = userService.findUserByToken(tokenValue, username);
//            return createResponse(loggedInDto, Constant.ApiResponseCode.EXIST_USER_PROFILE.getAction()[0], Constant.ApiResponseCode.EXIST_USER_PROFILE.getAction()[1]);
			return this.createResponse(loggedInDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
		} catch (Exception e) {
            logger.error("exception", e);
//            HttpHeaders headers = new HttpHeaders();
//            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//            RestResponse<LoggedinDto> tResponse = new RestResponse(new LoggedinDto(), Constant.ApiResponseCode.USER_PROFILE_NOT_FOUND.getAction()[0], Constant.ApiResponseCode.USER_PROFILE_NOT_FOUND.getAction()[1]);
//            return new ResponseEntity<RestResponse<LoggedinDto>>(tResponse, HttpStatus.OK);
			return this.createResponse(new LoggedinDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
		}
    }

    @PostMapping(value = "/api/auth/getRoles", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<List<String>>> getRoles(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username) {
        try {
            logger.info("get role by username {}", username);
            List<String> roles = userService.findRolesByUser(username);
            return this.createResponse(roles, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return this.createResponse(new ArrayList<String>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    @PostMapping(value = "/api/auth/getRolesByUser", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<List<String>>> getRolesByUser(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody UserWrapper userWrapper) {
        try {
            logger.info("get role by username {}", userWrapper.getUsername());
            List<String> roles = userService.findRolesByUser(userWrapper.getUsername());
            return this.createResponse(roles, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return this.createResponse(new ArrayList<String>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    @PostMapping(value = "/api/auth/searchUserNotReader", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<List<ResponseUser>>> searchUserNotReader(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username,
                                                                                @RequestBody SearchDto searchDto) {
        try {
            logger.info("load username {}", searchDto);
            List<ResponseUser> user = userService.findUserNotReader(username, searchDto);
            return this.createResponse(user, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return this.createResponse(new ArrayList<ResponseUser>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     * @param authorization
     * @param username
     * @param searchDto
     * @return
     */
    @PostMapping(value = "/api/auth/getUsersByRole", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<List<UserDto>>> getUsersByRole(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username,
                                                                      @RequestBody SearchUserDto searchDto) {
        try {
            logger.info("load all user by role {}", searchDto);
            List<UserDto> dtos = userService.findUsersByRole(username, searchDto.getRole(), searchDto.getKeyword());
            return createResponse(dtos, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);
        } catch (Exception e) {
            logger.error("exception", e);
            return this.createResponse(new ArrayList<UserDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     * @param authorization
     * @param username
     * @param listUserDto
     * @return
     */
    @PostMapping(value = "/api/auth/getListUserProfile", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<List<UserDto>>> getListUserProfile(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username,
                                                                          @RequestBody List<String> listUserDto) {
        try {
            logger.info("load all user by collection users {}", listUserDto);
            List<UserDto> dtos = userService.findUsersByListUser(listUserDto);
            return createResponse(dtos, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<UserDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }
}
