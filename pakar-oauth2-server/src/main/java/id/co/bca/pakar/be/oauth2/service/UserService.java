package id.co.bca.pakar.be.oauth2.service;

import id.co.bca.pakar.be.oauth2.dto.LoggedinDto;
import id.co.bca.pakar.be.oauth2.dto.SearchDto;

import java.util.List;

public interface UserService {
	LoggedinDto findUserByToken(String token);
	LoggedinDto findUserByToken(String token, String username);
	List<String> findRolesByUser(String username);
	List<String> findUserNotReader(SearchDto searchDto);
}
