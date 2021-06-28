/**
 * 
 */
package id.co.bca.pakar.be.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author OGYA
 *
 */
public class ImageDto {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("name")
	private String imageName;
	@JsonProperty("uri")
	private String uri;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
