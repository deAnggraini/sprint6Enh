package id.co.bca.pakar.be.oauth2.api;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import id.co.bca.pakar.be.oauth2.dto.CredentialDto;
import id.co.bca.pakar.be.oauth2.dto.LoggedinDto;
import id.co.bca.pakar.be.oauth2.dto.OAuthTokenDto;
import id.co.bca.pakar.be.oauth2.service.AuthenticationService;

@RestController
public class AuthenticationController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AuthenticationService authenticationService;
	
//	@Autowired
//	private TokenStore tokenStore;

	@PostMapping(value = "/api/auth/login", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<LoggedinDto> login(@RequestBody CredentialDto dto) {
		try {
			logger.info("received credential data --------- " + dto.toString());
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			logger.info("authenticate process -----");
			LoggedinDto oAuthToken = authenticationService.authenticate(dto);
			return ResponseEntity.accepted().headers(headers).body(oAuthToken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("exception", e);
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			return ResponseEntity.accepted().headers(headers).body(new LoggedinDto());
		}
	}
	
//	@PostMapping(value = "/api/auth/logout", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
//			MediaType.APPLICATION_JSON_VALUE })
//	public String logout(HttpServletRequest request, HttpServletResponse response) {		
//		String authHeader = request.getHeader("Authorization");
//		logger.info("logout proces for token value " + authHeader);
//        if (authHeader != null) {
//            String tokenValue = authHeader.replace("Bearer", "").trim();
//            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
//            if(accessToken != null) {
//            	tokenStore.removeAccessToken(accessToken);
//            	return "0";
//            } else
//            	return "-1";
//        }
//        return "-1";
//	}
}
