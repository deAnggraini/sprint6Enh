package id.co.bca.pakar.be.wf.client;

import id.co.bca.pakar.be.wf.common.Constant;
import id.co.bca.pakar.be.wf.dto.auth.UserProfileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "pakar-auth-server", url = "${spring.security.oauth2.resourceserver.auth-server}")
public interface PakarOauthClient {
    @PostMapping(value="/api/auth/getRoles", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ApiResponseWrapper.RestResponse<List<String>>> getRoles(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestHeader(Constant.Headers.X_USERNAME) String username);
    @PostMapping(value="/api/auth/getUser", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ApiResponseWrapper.RestResponse<UserProfileDto>> getUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestHeader(Constant.Headers.X_USERNAME) String username);
    @PostMapping(value = "/api/auth/getListUserProfile", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<ApiResponseWrapper.RestResponse<List<UserProfileDto>>> getListUserProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestHeader(Constant.Headers.X_USERNAME) String username, @RequestBody List<String> userIdDto);
}
