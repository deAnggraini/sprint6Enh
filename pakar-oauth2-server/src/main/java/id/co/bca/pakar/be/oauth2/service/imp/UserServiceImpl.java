/**
 * 
 */
package id.co.bca.pakar.be.oauth2.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.co.bca.pakar.be.oauth2.dto.SearchDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.stereotype.Service;

import id.co.bca.pakar.be.oauth2.dao.RoleRepository;
import id.co.bca.pakar.be.oauth2.dao.UserProfileRepository;
import id.co.bca.pakar.be.oauth2.dto.LoggedinDto;
import id.co.bca.pakar.be.oauth2.model.UserProfile;
import id.co.bca.pakar.be.oauth2.model.UserRole;
import id.co.bca.pakar.be.oauth2.service.UserService;
import id.co.bca.pakar.be.oauth2.token.CustomJdbcTokenStore;

/**
 * @author OGYA
 *
 */
@Service
public class UserServiceImpl implements UserService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
//	@Qualifier("tokenStore2")
	private CustomJdbcTokenStore tokenStore;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	/*
	 * get user profile by active token
	 */
	@Override
	public LoggedinDto findUserByToken(String token) {
		// TODO Auto-generated method stub
		try {
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
			logger.info("token value from db "+accessToken);
//			tokenStore.removeAccessToken(accessToken);
//
			OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
//			tokenStore.removeRefreshToken(refreshToken);
			logger.info("refresh_token value from db "+refreshToken);
			
			int expiresIn = accessToken.getExpiresIn();
			logger.info("expiresin from db "+expiresIn);
			
			Date expiresDate = accessToken.getExpiration();
			logger.info("expires date from db "+expiresDate);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * find token by token id and username
	 */
	@Override
	public LoggedinDto findUserByToken(String token, String username) {
		LoggedinDto loggedinDto = new LoggedinDto();
		try {
//			OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(token, username);			
			logger.info("access_token value from db "+accessToken);
			if(accessToken == null) {
				return loggedinDto;
			}
//			tokenStore.removeAccessToken(accessToken);
			loggedinDto.setAccess_token(token);
//
			OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
//			tokenStore.removeRefreshToken(refreshToken);
			logger.info("refresh_token value from db "+refreshToken);
			loggedinDto.setRefresh_token(refreshToken.getValue());
			
			int expiresIn = accessToken.getExpiresIn();
			logger.info("expiresin from db "+expiresIn);
			loggedinDto.setExpires_in(String.valueOf(expiresIn));
			
			Date expiresDate = accessToken.getExpiration();
			logger.info("expires date from db "+expiresDate);
			
			loggedinDto.setUsername(username);
			
			List<UserRole> uRoles = roleRepository.findUserRolesByUsername(username);
			for(UserRole ur : uRoles) {
				loggedinDto.getRoleDtos().add(ur.getRole().getId());
			}
			
			// get user profile
			UserProfile profile = userProfileRepository.findByUsername(username);
			if(profile != null) {
				loggedinDto.setFirstname(profile.getFirstname());
				loggedinDto.setLastname(profile.getLastname());
				loggedinDto.setFullname(profile.getFullname());
				loggedinDto.setEmail(profile.getEmail());
				loggedinDto.setPhone(profile.getPhone());
				loggedinDto.setCompanyName(profile.getCompanyName());
				loggedinDto.setOccupation(profile.getOccupation());
				loggedinDto.setPicture(profile.getPic());
			}
			return loggedinDto;
		} catch (Exception e) {
			logger.error("exception ", e);
		}
		return loggedinDto;
	}


	@Override
	public List<String> findRolesByUser(String username) {
		List<UserRole> uRoles = roleRepository.findUserRolesByUsername(username);
		List<String> roles = new ArrayList<>();
		for(UserRole ur : uRoles) {
			roles.add(ur.getRole().getId());
		}
		return roles;
	}

	@Override
	public List<String> findUserNotReader(SearchDto searchDto) {
		List<UserProfile> uRoles = userProfileRepository.findUserNotReader(searchDto.getKeyword());
		List<String> user = new ArrayList<>();
		for(UserProfile ur : uRoles) {
			user.add(ur.getFullname());
		}
		return user;
	}
}
