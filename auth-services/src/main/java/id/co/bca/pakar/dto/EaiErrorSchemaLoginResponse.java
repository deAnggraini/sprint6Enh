package id.co.bca.pakar.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EaiErrorSchemaLoginResponse {
	@JsonProperty("ErrorCode")
	private String erroCode;
	@JsonProperty("ErrorMessage")
	private Map<String, String> errorMessage = new HashMap<>();
	public String getErroCode() {
		return erroCode;
	}
	public void setErroCode(String erroCode) {
		this.erroCode = erroCode;
	}
	public Map<String, String> getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(Map<String, String> errorMessage) {
		this.errorMessage = errorMessage;
	}
}
