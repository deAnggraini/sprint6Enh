package id.co.bca.pakar.be.oauth2.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import id.co.bca.pakar.be.oauth2.common.Constant;
import id.co.bca.pakar.be.oauth2.dto.CredentialDto;
import id.co.bca.pakar.be.oauth2.dto.LoggedinDto;
import id.co.bca.pakar.be.oauth2.dto.NewAccessTokenDto;
import id.co.bca.pakar.be.oauth2.dto.RefreshTokenResponseDto;
import id.co.bca.pakar.be.oauth2.service.AuthenticationService;

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
			
			return this.createResponse(oAuthToken, "00", "SUCCESS");
		} catch (Exception e) {
			logger.error("exception", e);
			return this.createResponse(new LoggedinDto(), "01", "FAILED LOGIN");
		}
	}
	
	@PostMapping(value = Constant.ApiEndpoint.LOGOUT_URL, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RestResponse<String>> logout(HttpServletRequest request, HttpServletResponse response) {		
		String authHeader = request.getHeader("Authorization");
		try {
			logger.info("logout proces for token value " + authHeader);
			if (authHeader != null) {
			    String tokenValue = authHeader.replace("Bearer", "").trim();
			    Boolean logoutStatus = authenticationService.logout(tokenValue);
			    if(logoutStatus) {
			    	return this.createResponse("0", "00", "SUCCESS");
			    } else
			    	return this.createResponse("-1", "01", "LOGOUT FAILED");			    
			}
			return this.createResponse("-1", "01", "LOGOUT FAILED");
		} catch (Exception e) {
			logger.error("exception",e);
			return this.createResponse("-1", "01", "LOGOUT FAILED");
		}
	}
	
	@PostMapping(value = Constant.ApiEndpoint.REFRESH_TOKEN_URL, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RestResponse<RefreshTokenResponseDto>> refreshToken(@Valid @RequestBody NewAccessTokenDto dto) {
		try {
			logger.info("generate new access token with refresh token --- " + dto.getRefreshToken());
			RefreshTokenResponseDto oAuthToken = authenticationService.generateNewAccessToken(dto.getRefreshToken());
			return this.createResponse(oAuthToken, "00", "SUCCESS");
		} catch (Exception e) {
			logger.error("exception", e);
			return this.createResponse(new RefreshTokenResponseDto(), "01", "FAILED GENERATE NEW ACCESS TOKEN");
		}
	}
}
