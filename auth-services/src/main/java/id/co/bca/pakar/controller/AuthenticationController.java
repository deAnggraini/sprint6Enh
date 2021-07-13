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
	
//	public static void main(String[] args) {
////		String password = "";
////		try {
////			password = new DESedeEncryption("123456789013245678901234").decryptFromHex("7e4ec02231ed5cd4");
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		System.out.println(password);
//		List<AuthenticationDto> authDtos = new ArrayList();
//		authDtos.add(new AuthenticationDto("user", "password"));
//		authDtos.add(new AuthenticationDto("test1", "password1"));
//		authDtos.add(new AuthenticationDto("saifulhq", "12345"));
//		authDtos.add(new AuthenticationDto("admin", "12345"));
//		authDtos.add(new AuthenticationDto("super", "12345"));
//		authDtos.add(new AuthenticationDto("editor", "12345"));
//		authDtos.add(new AuthenticationDto("publisher", "12345"));
//		authDtos.add(new AuthenticationDto("guest", "12345"));
//		authDtos.add(new AuthenticationDto("superadmin", "12345"));
//		authDtos.add(new AuthenticationDto("test", "12345"));
//		authDtos.add(new AuthenticationDto("reader", "12345"));
//
//		EaiLoginResponse response = new EaiLoginResponse();
//		boolean loginStatus = false;
//		for (AuthenticationDto dto : authDtos) {
////			if (dto.getUsername().equals(credential.getUserId())) {
//				String password = "";
//				try {
//					password = (new TrippleDesEncryption("123456789013245678901234".getBytes())).decrypt("da82edc6dcc1af30");
//					System.out.println(password);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//				if (dto.getPassword().equals(password)) {
//					response.getErrorSchema().setErroCode("ESB-00-000");
//					response.getErrorSchema().getErrorMessage().put("Indonesian", "Berhasil");
//					response.getErrorSchema().getErrorMessage().put("English", "Success");
//					response.getOutputSchema().setStatus("0");
//					loginStatus = true;
//					break;
//				}
////			}
//		}
//	}
	
	public static void main(String[] args) throws Exception {
        String file = "src/test/resources/myUser.json";
        String json = readFileAsString(file);
        
        AuthenticationDto[] list = (AuthenticationDto[]) JSONMapperAdapter.jsonToListObject(json, AuthenticationDto[].class);
        for(AuthenticationDto dto : list) {
        	System.out.println(dto.toString());
        }
    }
}
