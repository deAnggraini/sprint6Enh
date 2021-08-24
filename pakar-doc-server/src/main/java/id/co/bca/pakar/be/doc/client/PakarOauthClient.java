package id.co.bca.pakar.be.doc.client;

import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dto.auth.UserProfileDto;
import id.co.bca.pakar.be.doc.dto.auth.UserWrapperDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "pakar-auth-server", url = "${spring.security.oauth2.resourceserver.auth-server}")
//Enabling feign
//@FeignClient(name="pakar-oauth-service")
//enabling ribbon
//@RibbonClient(name="pakar-oauth-service")
public interface PakarOauthClient {
    @PostMapping(value="/api/auth/getRoles", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ApiResponseWrapper.RestResponse<List<String>>> getRoles(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestHeader(Constant.Headers.X_USERNAME) String username);
    @PostMapping(value="/api/auth/getRolesByUser", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ApiResponseWrapper.RestResponse<List<String>>> getRolesByUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestHeader(Constant.Headers.X_USERNAME) String username, @RequestBody UserWrapperDto userWrapperDto);
    @PostMapping(value="/api/auth/getUser", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ApiResponseWrapper.RestResponse<UserProfileDto>> getUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestHeader(Constant.Headers.X_USERNAME) String username);
    @PostMapping(value = "/api/auth/getListUserProfile", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<ApiResponseWrapper.RestResponse<List<UserProfileDto>>> getListUserProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestHeader(Constant.Headers.X_USERNAME) String username, @RequestBody List<String> userIdDto);
}
