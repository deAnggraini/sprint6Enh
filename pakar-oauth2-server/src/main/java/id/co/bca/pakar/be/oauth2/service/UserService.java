package id.co.bca.pakar.be.oauth2.service;

import id.co.bca.pakar.be.oauth2.dto.LoggedinDto;

public interface UserService {
	LoggedinDto findUserByToken(String token);
	LoggedinDto findUserByToken(String token, String username);
}
