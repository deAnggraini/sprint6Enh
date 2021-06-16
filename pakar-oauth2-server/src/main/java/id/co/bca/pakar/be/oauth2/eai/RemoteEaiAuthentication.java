package id.co.bca.pakar.be.oauth2.eai;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import id.co.bca.pakar.be.oauth2.dto.EaiCredential;
import id.co.bca.pakar.be.oauth2.dto.EaiLoginResponse;
import id.co.bca.pakar.be.oauth2.util.JSONMapperAdapter;

@Service
public class RemoteEaiAuthentication {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${spring.eai.ad.loginUri}")
	private String loginUri;
	@Value("${spring.eai.ad.clientId}")
	private String clientId;
	@Value("${spring.eai.ad.applicationID}")
	private String applicationId;
	@Value("${spring.eai.ad.key}")
	private String encryptionKey;

	@Autowired
	private RestTemplate restTemplate;

	public EaiLoginResponse authenticate(String username, String password) {
		logger.info("authenticate to EAI system");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("ClientID", clientId);

		String access_token_url = loginUri;

		EaiCredential eaiCred = new EaiCredential();
		eaiCred.setUserId(username);
		String encryptedPassword = "";
		try {
			logger.info("encrypt password using 3DES ECB Mode");
			encryptedPassword = new DESedeEncryption(encryptionKey).encryptToHex(password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		eaiCred.setPassword(encryptedPassword);
		eaiCred.setApplicationId(applicationId);
		HttpEntity<EaiCredential> entity = new HttpEntity<EaiCredential>(eaiCred, headers);

		logger.info("call eai system ------ " + access_token_url);
		String response = restTemplate.exchange(access_token_url, HttpMethod.POST, entity, String.class).getBody();
		logger.info("response from eai login ------ " + response);

		EaiLoginResponse eaiLoginResponse = (EaiLoginResponse) JSONMapperAdapter.jsonToObject(response,
				EaiLoginResponse.class);
		logger.info("error status response ------ " + eaiLoginResponse.getOutputSchema().getStatus());
		logger.info("error code response ------ " + eaiLoginResponse.getErrorSchema().getErroCode());
		return eaiLoginResponse;
	}
}
