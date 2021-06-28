package id.co.bca.pakar.dto;

public class AuthenticationDto {
	private String username;
	private String password;
	
	
	public AuthenticationDto(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public AuthenticationDto() {
		// TODO Auto-generated constructor stub
	}
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
	@Override
	public String toString() {
		return "AuthenticationDto [username=" + username + ", password=" + password + "]";
	}
	
}
