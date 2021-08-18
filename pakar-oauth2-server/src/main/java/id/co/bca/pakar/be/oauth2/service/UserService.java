package id.co.bca.pakar.be.oauth2.service;

import id.co.bca.pakar.be.oauth2.dto.LoggedinDto;
import id.co.bca.pakar.be.oauth2.dto.ResponseUser;
import id.co.bca.pakar.be.oauth2.dto.SearchDto;
import id.co.bca.pakar.be.oauth2.dto.UserDto;

import java.util.List;

public interface UserService {
	LoggedinDto findUserByToken(String token);
	LoggedinDto findUserByToken(String token, String username);
	List<String> findRolesByUser(String username);
	List<ResponseUser> findUserNotReader(String username, SearchDto searchDto) throws Exception;
	List<UserDto> findUsersByRole(String username, String role, String keyword) throws Exception;
	List<UserDto> findUsersByListUser(List<String> userList) throws Exception;
}
