package id.co.bca.pakar.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import id.co.bca.pakar.dto.AuthenticationDto;
import id.co.bca.pakar.dto.EaiCredential;
import id.co.bca.pakar.dto.EaiLoginResponse;
import id.co.bca.pakar.util.JSONMapperAdapter;
import id.co.bca.pakar.util.TrippleDesEncryption;

@RestController
public class AuthenticationController {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	static String keyAlgorithm = "123456789013245678901234";
	
	@Value("${spring.json.file.user}")
	private String userPath;

	@PostMapping(value = "/ad-gateways/verify1", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public String loginEaiSuccess(@RequestHeader Map<String, String> headers, @RequestBody EaiCredential credential) throws Exception {
		headers.forEach((key, value) -> {
			logger.info(String.format("Header '%s' = %s", key, value));
		});
		logger.info("client request " + credential.toString());

		String json = readFileAsString(userPath);
        AuthenticationDto[] authDtos = (AuthenticationDto[]) JSONMapperAdapter.jsonToListObject(json, AuthenticationDto[].class);	
		EaiLoginResponse response = new EaiLoginResponse();
		boolean loginStatus = false;
		for (AuthenticationDto dto : authDtos) {
			logger.info("username dto "+dto.getUsername());
			if (dto.getUsername().equals(credential.getUserId())) {
				logger.info("userid "+credential.getUserId());
				String password = "";
				try {
					password = new TrippleDesEncryption(keyAlgorithm.getBytes()).decrypt(credential.getPassword());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				logger.info("password "+password);
				if (dto.getPassword().equals(password)) {
					response.getErrorSchema().setErroCode("ESB-00-000");
					response.getErrorSchema().getErrorMessage().put("Indonesian", "Berhasil");
					response.getErrorSchema().getErrorMessage().put("English", "Success");
					response.getOutputSchema().setStatus("0");
					loginStatus = true;
					break;
				}
			}
		}

		if (!loginStatus) {
			response.getErrorSchema().setErroCode("ESB-18-286");
			response.getErrorSchema().getErrorMessage().put("Indonesian", "UserID atau Password salah");
			response.getErrorSchema().getErrorMessage().put("English", "Wrong UserID or Password");
			response.getOutputSchema().setStatus("-1");
		}
		ObjectMapper mapper = new ObjectMapper();
		String ret = "";
		try {
			ret = mapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("return response " + ret);
		return ret;
	}
	
	public static String readFileAsString(String file)throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

	public static void main(String[] args) throws Exception {
        String file = "src/test/resources/myUser.json";
        String json = readFileAsString(file);
        
        AuthenticationDto[] list = (AuthenticationDto[]) JSONMapperAdapter.jsonToListObject(json, AuthenticationDto[].class);
        for(AuthenticationDto dto : list) {
        	System.out.println(dto.toString());
        }
    }
}
