package id.co.bca.pakar.be.oauth2.api;

import id.co.bca.pakar.be.oauth2.common.Constant;
import id.co.bca.pakar.be.oauth2.dto.CredentialDto;
import id.co.bca.pakar.be.oauth2.dto.LoggedinDto;
import id.co.bca.pakar.be.oauth2.dto.NewAccessTokenDto;
import id.co.bca.pakar.be.oauth2.dto.RefreshTokenResponseDto;
import id.co.bca.pakar.be.oauth2.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class AuthenticationController extends BaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping(value = Constant.ApiEndpoint.LOGIN_URL, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RestResponse<LoggedinDto>> login(@Valid @RequestBody CredentialDto dto) {
		try {
			logger.info("----- authenticate process -----");
			LoggedinDto oAuthToken = authenticationService.authenticate(dto);
			oAuthToken.setRememberMe(dto.getRememberMe());
			
			return this.createResponse(oAuthToken, Constant.ApiResponseCode.LOGIN_SUCCEED.getAction()[0], Constant.ApiResponseCode.LOGIN_SUCCEED.getAction()[1]);
		} catch (Exception e) {
			logger.error("exception", e);
			return this.createResponse(new LoggedinDto(), Constant.ApiResponseCode.INCORRECT_USERNAME_PASSWORD.getAction()[0], Constant.ApiResponseCode.INCORRECT_USERNAME_PASSWORD.getAction()[1]);
		}
	}
	
	@PostMapping(value = Constant.ApiEndpoint.LOGOUT_URL, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RestResponse<String>> logout(@RequestHeader("Authorization") String authorization) {
//		String authHeader = request.getHeader("Authorization");
		try {
			logger.info("logout proces for token value " + authorization);
			if (authorization != null) {
			    String tokenValue = authorization.replace("Bearer", "").trim();
			    Boolean logoutStatus = authenticationService.logout(tokenValue);
			    if(logoutStatus) {
			    	return this.createResponse("0", Constant.ApiResponseCode.LOGOUT_SUCCEED.getAction()[0], Constant.ApiResponseCode.LOGOUT_SUCCEED.getAction()[1]);
			    } else
			    	return this.createResponse("-1", Constant.ApiResponseCode.LOGOUT_FAILED.getAction()[0], Constant.ApiResponseCode.LOGOUT_FAILED.getAction()[1]);			    
			}
			return this.createResponse("-1", Constant.ApiResponseCode.LOGOUT_FAILED.getAction()[0], Constant.ApiResponseCode.LOGOUT_FAILED.getAction()[1]);
		} catch (Exception e) {
			logger.error("exception",e);
			return this.createResponse("-1", Constant.ApiResponseCode.LOGOUT_FAILED.getAction()[0], Constant.ApiResponseCode.LOGOUT_FAILED.getAction()[1]);
		}
	}
	
	@PostMapping(value = Constant.ApiEndpoint.REFRESH_TOKEN_URL, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RestResponse<RefreshTokenResponseDto>> refreshToken(@Valid @RequestBody NewAccessTokenDto dto) {
		try {
			logger.info("generate new access token with refresh token --- " + dto.getRefreshToken());
			RefreshTokenResponseDto oAuthToken = authenticationService.generateNewAccessToken(dto.getRefreshToken());
			return this.createResponse(oAuthToken, Constant.ApiResponseCode.REFRESH_TOKEN_SUCCEED.getAction()[0], Constant.ApiResponseCode.REFRESH_TOKEN_SUCCEED.getAction()[1]);
		} catch (Exception e) {
			logger.error("exception", e);
			return this.createResponse(new RefreshTokenResponseDto(), Constant.ApiResponseCode.REFRESH_TOKEN_FAILED.getAction()[0], Constant.ApiResponseCode.REFRESH_TOKEN_FAILED.getAction()[1]);
		}
	}
}
