package id.co.bca.pakar.be.oauth2.api;

import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.bca.pakar.be.oauth2.dto.UserDto;

@RestController
public class UserController {
	@PostMapping(value = "/api/auth/user", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserDto> user() {
//		logger.info("received credential --------- " + dto.toString());
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		logger.info("authenticate process -----");
//		OAuthTokenDto oAuthToken = authenticationService.authenticate(dto);
		UserDto dto = new UserDto();
		return ResponseEntity.accepted().headers(headers).body(dto);
	}
}
