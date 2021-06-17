package id.co.bca.pakar.be.oauth2.service.imp;

import java.util.Arrays;
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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import id.co.bca.pakar.be.oauth2.dao.RoleRepository;
import id.co.bca.pakar.be.oauth2.dao.UserProfileRepository;
import id.co.bca.pakar.be.oauth2.dto.CredentialDto;
import id.co.bca.pakar.be.oauth2.dto.LoggedinDto;
import id.co.bca.pakar.be.oauth2.dto.OAuthCredential;
import id.co.bca.pakar.be.oauth2.dto.OAuthTokenDto;
import id.co.bca.pakar.be.oauth2.model.UserProfile;
import id.co.bca.pakar.be.oauth2.model.UserRole;
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

		logger.info("received credential --- " + dto.toString());
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
			
//			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//			Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
//			
//			List<GrantedAuthority> listAuthorities = new ArrayList<GrantedAuthority>();
//			listAuthorities.addAll(authorities);
//			for(GrantedAuthority ga : listAuthorities) {
//				loggedinDto.getRoleDtos().add(ga.getAuthority());
//			}	
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
			}
		} catch (RestClientException e) {
			logger.error("login failed with exception", e);
			throw new Exception("failed login\n", e);
		}
		return loggedinDto;
	}

}
