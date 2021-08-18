/**
 * 
 */
package id.co.bca.pakar.be.oauth2.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.co.bca.pakar.be.oauth2.dao.UserRoleRepository;
import id.co.bca.pakar.be.oauth2.dto.ResponseUser;
import id.co.bca.pakar.be.oauth2.dto.SearchDto;
import id.co.bca.pakar.be.oauth2.dto.UserDto;
import id.co.bca.pakar.be.oauth2.model.User;
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
import org.springframework.transaction.annotation.Transactional;

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

	@Autowired
	private UserRoleRepository userRoleRepository;
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
	public List<ResponseUser> findUserNotReader(String username, SearchDto searchDto) throws Exception {
		try {
			List<UserProfile> uRoles = userProfileRepository.findUserNotReader(username, searchDto.getKeyword());
			List<ResponseUser> user = new ArrayList<ResponseUser>();
			for(UserProfile ur : uRoles) {
				ResponseUser userTemp = new ResponseUser();
				userTemp.setFullname(ur.getFullname());
				userTemp.setUsername(ur.getUser().getUsername());
				userTemp.setEmail(ur.getEmail());
				user.add(userTemp);
			}
			return user;
		} catch (Exception e) {
			logger.error("exception");
			throw new Exception("exception", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserDto> findUsersByRole(String username, String role, String keyword) throws Exception {
		try {
			List<UserProfile> up = userProfileRepository.findUsersByRole(username, role, keyword);
			return new MapperEntityIntoDto().mapEntitiesIntoDto(up);
		} catch (Exception e) {
			logger.error("exception", e);
			throw new Exception("exception", e);
		}
	}

	/**
	 *
	 * @param userList
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public List<UserDto> findUsersByListUser(List<String> userList) throws Exception {
		try {
			logger.info("load user profile from all user {}", userList);
			List<UserProfile> userProfiles = userProfileRepository.findUsersByListUser(userList);
			List<UserDto> userDtos = new ArrayList<UserDto>();
			for(UserProfile up : userProfiles) {
				UserDto userDto = new UserDto();
				userDto.setFullname(up.getFullname());
				userDto.setLastname(up.getLastname());
				userDto.setFirstname(up.getFirstname());
				userDto.setUsername(up.getUser().getUsername());
				logger.debug("add username {} to list", userDto.getUsername());
				userDtos.add(userDto);
			}
			userDtos.forEach(e-> logger.debug("user in edit article is {}", e.getUsername()));
//			logger.debug("all user profiles {}", userDtos);
			return userDtos;
		} catch (Exception e) {
			logger.error("exception");
			throw new Exception("exception", e);
		}
	}

	private class MapperEntityIntoDto {
		public List<UserDto> mapEntitiesIntoDto(List<UserProfile> entities) {
			List<UserDto> dtos = new ArrayList<>();
			entities.forEach(e -> dtos.add(mapEntityIntoDto(e)));
			return dtos;
		}

		public UserDto mapEntityIntoDto(UserProfile entity) {
			UserDto userDto = new UserDto();
			userDto.setUsername(entity.getUser().getUsername());
			userDto.setEmail(entity.getEmail());
			userDto.setFullname(entity.getFullname());
			return userDto;
		}
	}
}
