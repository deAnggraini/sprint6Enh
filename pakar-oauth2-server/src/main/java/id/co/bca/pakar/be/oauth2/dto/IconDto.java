package id.co.bca.pakar.be.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IconDto {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("name")
	private String iconName;
	@JsonProperty("uri")
	private String uri;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIconName() {
		return iconName;
	}
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	
}
