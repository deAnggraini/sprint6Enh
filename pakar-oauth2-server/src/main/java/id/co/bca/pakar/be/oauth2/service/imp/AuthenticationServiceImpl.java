package id.co.bca.pakar.be.oauth2.service.imp;

import id.co.bca.pakar.be.oauth2.common.Constant;
import id.co.bca.pakar.be.oauth2.dao.RoleRepository;
import id.co.bca.pakar.be.oauth2.dao.UserProfileRepository;
import id.co.bca.pakar.be.oauth2.dto.*;
import id.co.bca.pakar.be.oauth2.model.AuditLogin;
import id.co.bca.pakar.be.oauth2.model.UserProfile;
import id.co.bca.pakar.be.oauth2.model.UserRole;
import id.co.bca.pakar.be.oauth2.service.AuthenticationService;
import id.co.bca.pakar.be.oauth2.token.CustomJdbcTokenStore;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CustomJdbcTokenStore tokenStore;

	@Value("${spring.security.oauth2.clientId}")
	private String clientId;
	@Value("${spring.security.oauth2.clientSecret}")
	private String clientSecret;
	@Value("${spring.security.oauth2.authorize-grant-type}")
	private String[] grantTypes;
	@Value("${spring.security.oauth2.server.url}")
	private String uri;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public LoggedinDto authenticate(CredentialDto dto) throws Exception {
		OAuthCredential cred = new OAuthCredential();
		cred.setUsername(dto.getUsername());
		cred.setPassword(dto.getPassword());
		cred.setGrant_type("password");

		String credentials = clientId + ":" + clientSecret;
		String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Basic " + encodedCredentials);

		HttpEntity<String> request = new HttpEntity<String>(headers);

		String access_token_url = uri+"/oauth/token";
		access_token_url += "?username=" + cred.getUsername();
		access_token_url += "&password=" + cred.getPassword();
		access_token_url += "&grant_type=" + cred.getGrant_type();

		logger.debug("authenticate user to --- "+access_token_url);
		ResponseEntity<OAuthTokenDto> response = null;
		LoggedinDto loggedinDto = new LoggedinDto();
		try {
			response = restTemplate.exchange(access_token_url, HttpMethod.POST, request, OAuthTokenDto.class);
			
			logger.info("http status response  --- " + ((ResponseEntity<OAuthTokenDto>) response).getStatusCode());
//			logger.info("generated token oauth2 ---" + response.getBody());

			loggedinDto.setUsername(cred.getUsername());
			loggedinDto.setAccess_token(response.getBody().getAccess_token());
			loggedinDto.setRefresh_token(response.getBody().getRefresh_token());
			loggedinDto.setExpires_in(response.getBody().getExpires_in());		
			loggedinDto.setScope(response.getBody().getScope());
			loggedinDto.setToken_type(response.getBody().getToken_type());
			
			// get user role
			List<UserRole> uRoles = roleRepository.findUserRolesByUsername(cred.getUsername());
			for(UserRole ur : uRoles) {
				loggedinDto.getRoleDtos().add(ur.getRole().getId());
			}
			
			// get user profile
			UserProfile profile = userProfileRepository.findByUsername(cred.getUsername());
			if(profile != null) {
				loggedinDto.setFirstname(profile.getFirstname());
				loggedinDto.setLastname(profile.getLastname());
				loggedinDto.setFullname(profile.getFullname());
				loggedinDto.setEmail(profile.getEmail());
				loggedinDto.setPhone(profile.getPhone());
				loggedinDto.setCompanyName(profile.getCompanyName());
				loggedinDto.setOccupation(profile.getOccupation());
				loggedinDto.setPicture(profile.getPic());
			}
		} catch (RestClientException e) {
			logger.error("login failed with exception", e);
			throw new Exception("failed login\n", e);
		}
		return loggedinDto;
	}

	/**
	 * logout oauth2 pakar
	 */
	@Override
	public Boolean logout(String tokenValue) throws Exception {
		logger.info("--- processing logout ---");
		try {
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);			
			logger.info("access_token value from db "+accessToken);
			if(accessToken == null) {
				return Boolean.FALSE;
			}
			
			logger.info("remove access token "+accessToken);
			tokenStore.removeAccessToken(accessToken);			
			
			OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
			logger.info("remove refresh token "+refreshToken);
			tokenStore.removeRefreshToken(refreshToken);

			return Boolean.TRUE;
		} catch (Exception e) {
			logger.error("exception", e);
			return Boolean.FALSE;
		}
	}

	/**
	 * generate new access token
	 */
	@Override
	public RefreshTokenResponseDto generateNewAccessToken(String refreshToken) throws Exception {
		logger.info("--- processing generate new token ---");
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			String credentials = clientId + ":" + clientSecret;
			String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
			headers.add("Authorization", "Basic " + encodedCredentials);

			HttpEntity<String> request = new HttpEntity<String>(headers);

			String access_token_url = uri+"/oauth/token";
			access_token_url += "?grant_type=" + "refresh_token";
			access_token_url += "&refresh_token=" + refreshToken;
			ResponseEntity<OAuthTokenDto> response = null;
			logger.info("refresh token to url "+access_token_url);
			response = restTemplate.exchange(access_token_url, HttpMethod.POST, request, OAuthTokenDto.class);
			logger.info("http status response  --- " + ((ResponseEntity<OAuthTokenDto>) response).getStatusCode());
//			logger.info("generated new response oauth2 token ---" + response.getBody());
			if(((ResponseEntity<OAuthTokenDto>) response).getStatusCode().equals(HttpStatus.OK)) {
				RefreshTokenResponseDto dto = new RefreshTokenResponseDto();
				dto.setAccess_token(response.getBody().getAccess_token());
				dto.setRefresh_token(response.getBody().getRefresh_token());
//				dto.setRefresh_token(refreshToken);
				dto.setExpires_in(response.getBody().getExpires_in());		
				return dto;
			} else {
				logger.info("fail refresh token cause http status response from /oauth/token url <> 200 ");
				return new RefreshTokenResponseDto();
			}
		} catch (HttpClientErrorException e) {
			throw new HttpClientErrorException(HttpStatus.OK, "generate new token fail");
		} catch (Exception e) {
			logger.error("exception when refresh token", e);
			throw new Exception("generate new token fail");
		}
	}

}
