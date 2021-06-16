package id.co.bca.pakar.dto;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationResponseDto {
	private UserDto userDto = new UserDto();
	private TokenDto tokenDto = new TokenDto();
	private List<String> roleDtos = new ArrayList<String>();
	
	public UserDto getUserDto() {
		return userDto;
	}
	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}
	public TokenDto getTokenDto() {
		return tokenDto;
	}
	public void setTokenDto(TokenDto tokenDto) {
		this.tokenDto = tokenDto;
	}
	public List<String> getRoleDtos() {
		return roleDtos;
	}
	public void setRoleDtos(List<String> roleDtos) {
		this.roleDtos = roleDtos;
	}
}
