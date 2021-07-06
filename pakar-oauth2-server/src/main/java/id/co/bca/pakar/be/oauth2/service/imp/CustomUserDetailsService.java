package id.co.bca.pakar.be.oauth2.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import id.co.bca.pakar.be.oauth2.dao.RoleRepository;
import id.co.bca.pakar.be.oauth2.dao.UserRepository;
import id.co.bca.pakar.be.oauth2.dto.SecUserDto;
import id.co.bca.pakar.be.oauth2.model.User;
import id.co.bca.pakar.be.oauth2.model.UserRole;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			logger.info("load user by user name " + username);
			User user = userRepository.findUserByUsername(username);
			List<GrantedAuthority> roles = new ArrayList<>();
			logger.info("load roles by user name " + username);
			List<UserRole> uRoles = roleRepository.findUserRolesByUsername(username);
			for (UserRole ur : uRoles) {
				roles.add(new SimpleGrantedAuthority(ur.getRole().getId()));
			}
			logger.info("populate username {} with roles {}", username, roles);
			SecUserDto dto = new SecUserDto(user.getUsername(), user.getPassword(), user.getEnabled().booleanValue(),
					true, true, true, roles);
			return dto;
		} catch (Exception e) {
			logger.error("exception", e);
			throw new UsernameNotFoundException("not found username");
		}
	}

}
