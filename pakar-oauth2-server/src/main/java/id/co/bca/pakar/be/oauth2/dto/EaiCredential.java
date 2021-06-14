package id.co.bca.pakar.be.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EaiCredential {
	@JsonProperty("UserID")
	private String userId;
	@JsonProperty("Password")
	private String password;
	@JsonProperty("ApplicationID")
	private String applicationId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	@Override
	public String toString() {
		return "EaiCredential [userId=" + userId + ", password=" + password + ", applicationId=" + applicationId + "]";
	}
}
