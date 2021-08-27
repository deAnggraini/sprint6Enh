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
import java.util.Locale;

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

			return this.createResponse(oAuthToken, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
		} catch (Exception e) {
			logger.error("exception", e);
			return this.createResponse(new LoggedinDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("userid.password.incorrect.update", null, getLocale()));
		}
	}

	
	@PostMapping(value = Constant.ApiEndpoint.LOGOUT_URL, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RestResponse<String>> logout(@RequestHeader("Authorization") String authorization) {
		try {
			logger.info("logout proces for token value " + authorization);
			if (authorization != null) {
			    String tokenValue = authorization.replace("Bearer", "").trim();
			    Boolean logoutStatus = authenticationService.logout(tokenValue);
			    if(logoutStatus) {
			    	return this.createResponse("0", Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("logout.success", null, getLocale()));
			    } else
			    	return this.createResponse("-1", Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("logout.failed", null, getLocale()));
			}
			return this.createResponse("-1", Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("logout.failed", null, getLocale()));
		} catch (Exception e) {
			logger.error("exception",e);
			return this.createResponse("-1", Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("logout.failed", null, getLocale()));
		}
	}
	
	@PostMapping(value = Constant.ApiEndpoint.REFRESH_TOKEN_URL, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RestResponse<RefreshTokenResponseDto>> refreshToken(@Valid @RequestBody NewAccessTokenDto dto) {
		try {
			logger.info("generate new access token with refresh token --- " + dto.getRefreshToken());
			RefreshTokenResponseDto oAuthToken = authenticationService.generateNewAccessToken(dto.getRefreshToken());
			return this.createResponse(oAuthToken, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("refreshtoken.success", null, getLocale()));
		} catch (Exception e) {
			logger.error("exception", e);
			return this.createResponse(new RefreshTokenResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("refreshtoken.failed", null, getLocale()));
		}
	}
}
