package id.co.bca.pakar.be.oauth2.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import id.co.bca.pakar.be.oauth2.dto.CredentialDto;
import id.co.bca.pakar.be.oauth2.dto.LoggedinDto;
import id.co.bca.pakar.be.oauth2.service.AuthenticationService;

@RestController
@CrossOrigin
public class AuthenticationController extends BaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping(value = "/api/auth/login", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RestResponse<LoggedinDto>> login(@RequestBody CredentialDto dto) {
		try {
			logger.info("received credential data --------- " + dto.toString());
//			HttpHeaders headers = new HttpHeaders();
//			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			logger.info("authenticate process -----");
			LoggedinDto oAuthToken = authenticationService.authenticate(dto);
			
//			RestResponse<LoggedinDto> tResponse = new RestResponse(oAuthToken);
//			return ResponseEntity.accepted().headers(headers).body(tResponse);
			return this.createResponse(oAuthToken, "00", "SUCCESS");
		} catch (Exception e) {
			logger.error("exception", e);
//			HttpHeaders headers = new HttpHeaders();
//			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//			RestResponse<LoggedinDto> tResponse = new RestResponse(new LoggedinDto(), "01", "FAILED LOGIN");
//			return ResponseEntity.accepted().headers(headers).body(tResponse);
			return this.createResponse(new LoggedinDto(), "01", "FAILED LOGIN");
		}
	}
	
	@PostMapping(value = "/api/auth/logout", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
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
}
