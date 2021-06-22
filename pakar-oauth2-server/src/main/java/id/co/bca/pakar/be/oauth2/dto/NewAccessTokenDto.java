package id.co.bca.pakar.be.oauth2.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewAccessTokenDto {
	@NotNull
	@NotEmpty(message = "refresh token required")
	@JsonProperty("refreshToken")
	private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }		
}
