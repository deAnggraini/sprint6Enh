package id.co.bca.pakar.be.oauth2.dto;

public class SearchUserDto  extends SearchDto {
	private String role;
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
