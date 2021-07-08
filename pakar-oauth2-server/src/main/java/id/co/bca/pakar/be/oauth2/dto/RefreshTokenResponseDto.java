package id.co.bca.pakar.be.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RefreshTokenResponseDto {	
	@JsonProperty("authToken")
	private String access_token;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("expiresIn")
	private String expires_in;
	@JsonProperty("refreshToken")
	private String refresh_token;
	
	public RefreshTokenResponseDto() {
		super();
	}
	
	public RefreshTokenResponseDto(String access_token, String expires_in, String refresh_token) {
		super();
		this.access_token = access_token;
		this.expires_in = expires_in;
		this.refresh_token = refresh_token;
	}


	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
}
