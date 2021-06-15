package id.co.bca.pakar.be.oauth2.api;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import id.co.bca.pakar.be.oauth2.dto.CredentialDto;
import id.co.bca.pakar.be.oauth2.dto.OAuthTokenDto;
import id.co.bca.pakar.be.oauth2.service.AuthenticationService;

@RestController
public class AuthenticationController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping(value = "/api/auth/login", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<OAuthTokenDto> login(@RequestBody CredentialDto dto) {
		logger.info("received credential --------- " + dto.toString());
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		logger.info("authenticate process -----");
		OAuthTokenDto oAuthToken = authenticationService.authenticate(dto);
		return ResponseEntity.accepted().headers(headers).body(oAuthToken);
	}
}
