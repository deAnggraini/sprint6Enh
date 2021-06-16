package id.co.bca.pakar.be.oauth2.service.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import id.co.bca.pakar.be.oauth2.dto.CredentialDto;
import id.co.bca.pakar.be.oauth2.dto.LoggedinDto;
import id.co.bca.pakar.be.oauth2.dto.OAuthCredential;
import id.co.bca.pakar.be.oauth2.dto.OAuthTokenDto;
import id.co.bca.pakar.be.oauth2.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestTemplate restTemplate;

	@Value("${spring.security.oauth2.clientId}")
	private String clientId;
	@Value("${spring.security.oauth2.clientSecret}")
	private String clientSecret;
	@Value("${spring.security.oauth2.server.url}")
	private String uri;
	
	@Override
	public LoggedinDto authenticate(CredentialDto dto) {
		OAuthCredential cred = new OAuthCredential();
		cred.setUsername(dto.getUsername());
		cred.setPassword(dto.getPassword());
		cred.setGrant_type("password");

		logger.info("received credential --------- " + cred.toString());
		String credentials = clientId + ":" + clientSecret;
		String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Basic " + encodedCredentials);

		HttpEntity<String> request = new HttpEntity<String>(headers);

		String access_token_url = uri;
		access_token_url += "?username=" + cred.getUsername();
		access_token_url += "&password=" + cred.getPassword();
		access_token_url += "&grant_type=" + cred.getGrant_type();

		logger.info("authenticate oauth to ------ "+uri);
		ResponseEntity<OAuthTokenDto> response = null;
		LoggedinDto loggedinDto = new LoggedinDto();
		try {
			response = restTemplate.exchange(access_token_url, HttpMethod.POST, request, OAuthTokenDto.class);
			
			logger.info("http status response  --------- " + ((ResponseEntity<OAuthTokenDto>) response).getStatusCode());
			logger.info("generated oauth2 token --------- " + response.getBody());

//			OAuthTokenDto oAuthToken = (OAuthTokenDto) JSONMapperAdapter.jsonToObject(response.getBody(),  OAuthTokenDto.class);

			loggedinDto.setUsername(cred.getUsername());
			loggedinDto.setAccess_token(response.getBody().getAccess_token());
			loggedinDto.setRefresh_token(response.getBody().getRefresh_token());
			loggedinDto.setExpires_in(response.getBody().getExpires_in());		
			loggedinDto.setScope(response.getBody().getScope());
			loggedinDto.setToken_type(response.getBody().getToken_type());
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
			
			List<GrantedAuthority> listAuthorities = new ArrayList<GrantedAuthority>();
			listAuthorities.addAll(authorities);
			for(GrantedAuthority ga : listAuthorities) {
				loggedinDto.getRoleDtos().add(ga.getAuthority());
			}			
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("failed login");
		}
		return loggedinDto;
	}

}
