package id.co.bca.pakar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EaiOutputSchemaLoginResponse {
	// success 0, salah user/password = -1
	@JsonProperty("Status")
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
