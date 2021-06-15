package id.co.bca.pakar.be.oauth2.authenticate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.exceptions.BadClientCredentialsException;
import org.springframework.stereotype.Component;

import id.co.bca.pakar.be.oauth2.dto.EaiLoginResponse;
import id.co.bca.pakar.be.oauth2.eai.RemoteEaiAuthentication;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	boolean shouldAuthenticateAgainstThirdPartySystem = true;

	@Autowired
	private RemoteEaiAuthentication remoteEaiAuthentication;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();

		EaiLoginResponse response = remoteEaiAuthentication.authenticate();

		logger.info("---- authentication ---- " + authentication);
		if (name != null && password != null) {
			if (response.getOutputSchema().getStatus().equals("0")) {
				logger.info("login success ");
				final List<GrantedAuthority> grantedAuths = new ArrayList<>();
				grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
//			UserDetails principal = new SecUserDto(name, password, grantedAuths);
				final Authentication auth = new UsernamePasswordAuthenticationToken(name, password,
						Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
				return auth;
			} else {
				logger.info("user / password salah ");
				return (Authentication) new BadClientCredentialsException();
			}			
		} else {
			logger.info("user / password salah ");
			return (Authentication) new BadClientCredentialsException();
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
