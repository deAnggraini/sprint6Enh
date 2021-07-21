package id.co.bca.pakar.be.oauth2.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EaiErrorSchemaLoginResponse {
	@JsonProperty("ErrorCode")
	private String erroCode;
	@JsonProperty("ErrorMessage")
	private Map<String, String> errorMessage = new HashMap<>();
	@JsonProperty("AlertMessage")
	private String alertMessage ;
	@JsonProperty("FailCount")
	private Integer failCount;

	public String getAlertMessage() {
		return alertMessage;
	}
	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}
	public Integer getFailCount() {
		return failCount;
	}
	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

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
