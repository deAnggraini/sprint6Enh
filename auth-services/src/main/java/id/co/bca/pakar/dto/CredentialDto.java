package id.co.bca.pakar.dto;

/**
 * 
 * @author OGYA
 * class for credential login from client
 */
public class CredentialDto {
	private String username;
	private String password;
	
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
		return "CredentialDto [username=" + username + ", password=" + password + "]";
	}
	
	
}
