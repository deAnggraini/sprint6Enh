package id.co.bca.pakar.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import id.co.bca.pakar.util.DESedeEncryption;

@RestController
public class AuthenticationController {
	static String keyAlgorithm = "123456789013245678901234";

	@PostMapping(value = "/ad-gateways/verify1", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public String loginEaiSuccess(@RequestHeader Map<String, String> headers, @RequestBody EaiCredential credential) {
		headers.forEach((key, value) -> {
			System.out.println(String.format("Header '%s' = %s", key, value));
		});
		System.out.println("client request " + credential.toString());
		List<AuthenticationDto> authDtos = new ArrayList<AuthenticationDto>();
		authDtos.add(new AuthenticationDto("user", "password"));
		authDtos.add(new AuthenticationDto("test1", "password1"));
		authDtos.add(new AuthenticationDto("saifulhq", "12345"));
		authDtos.add(new AuthenticationDto("admin", "12345"));
		authDtos.add(new AuthenticationDto("super", "12345"));
		authDtos.add(new AuthenticationDto("editor", "12345"));
		authDtos.add(new AuthenticationDto("publisher", "12345"));
		authDtos.add(new AuthenticationDto("guest", "12345"));
		for (AuthenticationDto dto : authDtos) {
			System.out.println("client request " + dto.getUsername());
		}
		
		EaiLoginResponse response = new EaiLoginResponse();
		boolean loginStatus = false;
		for (AuthenticationDto dto : authDtos) {
			System.out.println("username dto "+dto.getUsername());
			if (dto.getUsername().equals(credential.getUserId())) {
				System.out.println("userid "+credential.getUserId());
				String password = "";
				try {
					password = new DESedeEncryption(keyAlgorithm).decryptFromHex(credential.getPassword());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("password "+password);
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
		System.out.println("return response " + ret);
		return ret;
	}
	
	public static void main(String[] args) {
//		String password = "";
//		try {
//			password = new DESedeEncryption("123456789013245678901234").decryptFromHex("7e4ec02231ed5cd4");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(password);
		List<AuthenticationDto> authDtos = new ArrayList();
		authDtos.add(new AuthenticationDto("user", "password"));
		authDtos.add(new AuthenticationDto("test1", "password1"));
		authDtos.add(new AuthenticationDto("saifulhq", "12345"));
		authDtos.add(new AuthenticationDto("admin", "12345"));
		authDtos.add(new AuthenticationDto("super", "12345"));
		authDtos.add(new AuthenticationDto("editor", "12345"));
		authDtos.add(new AuthenticationDto("publisher", "12345"));
		authDtos.add(new AuthenticationDto("guest", "12345"));
		authDtos.add(new AuthenticationDto("superadmin", "12345"));
		authDtos.add(new AuthenticationDto("test", "12345"));
		authDtos.add(new AuthenticationDto("reader", "12345"));

		EaiLoginResponse response = new EaiLoginResponse();
		boolean loginStatus = false;
		for (AuthenticationDto dto : authDtos) {
//			if (dto.getUsername().equals(credential.getUserId())) {
				String password = "";
				try {
					password = new DESedeEncryption("123456789013245678901234").decryptFromHex("7e4ec02231ed5cd4");
					System.out.println(password);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (dto.getPassword().equals(password)) {
					response.getErrorSchema().setErroCode("ESB-00-000");
					response.getErrorSchema().getErrorMessage().put("Indonesian", "Berhasil");
					response.getErrorSchema().getErrorMessage().put("English", "Success");
					response.getOutputSchema().setStatus("0");
					loginStatus = true;
					break;
				}
//			}
		}
	}
}
