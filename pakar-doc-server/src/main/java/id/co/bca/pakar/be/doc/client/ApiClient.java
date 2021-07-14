package id.co.bca.pakar.be.doc.client;

import id.co.bca.pakar.be.doc.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class ApiClient {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.security.oauth2.server.url}")
    private String oauthUri;

    /**
     *
     * @param username
     * @param tokenValue
     * @return
     */
    public String getRoles(String username, String tokenValue) {
        String role = "anonymous";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add(Constant.Headers.AUTHORIZATION, Constant.Headers.BEARER + tokenValue);
            headers.add(Constant.Headers.X_USERNAME, username);
            HttpEntity<String> request = new HttpEntity<String>(headers);
            String url_api = oauthUri + "/api/auth/getRoles";
            logger.debug("get roles from api {}", url_api);
            Map map = restTemplate.exchange(url_api, HttpMethod.POST, request, Map.class).getBody();
            logger.debug("response body {}", map);
            if (map.containsKey("status")) {
                Map apiStatus = (Map) map.get("status");
                String code = (String) apiStatus.get("code");
                if (!code.equals("00")) {
                    throw new RestClientException("");
                }
                logger.debug("get role from response data {}", map.get("data"));
                List jsonRoles = (List) map.get("data");
                logger.debug("get role from response data {}", jsonRoles);
                role = (String) jsonRoles.get(0);
            }
        } catch (RestClientException e) {
            logger.error("exception", e);
        }
        return role;
    }
}
