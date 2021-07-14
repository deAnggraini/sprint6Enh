package id.co.bca.pakar.be.oauth2.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CredentialDto {
	@NotNull
	@NotEmpty(message = "username is required")
	@Pattern(regexp = "[A-Za-z0-9]+$")
	@JsonProperty("username")
	private String username;
	@NotNull
	@NotEmpty(message = "password is required")
	@Pattern(regexp = "[A-Za-z0-9]+$")
	@JsonProperty("password")
	private String password;
	@JsonProperty("remember")
	private Boolean rememberMe;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getRememberMe() {
		return rememberMe;
	}
	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
}
