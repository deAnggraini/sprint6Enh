package id.co.bca.pakar.be.oauth2.authenticate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import id.co.bca.pakar.be.oauth2.dao.RoleRepository;
import id.co.bca.pakar.be.oauth2.dto.EaiLoginResponse;
import id.co.bca.pakar.be.oauth2.eai.RemoteEaiAuthentication;
import id.co.bca.pakar.be.oauth2.model.UserRole;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//	boolean shouldAuthenticateAgainstThirdPartySystem = true;

	@Autowired
	private RemoteEaiAuthentication remoteEaiAuthentication;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private MessageSource messageSource;

	@SuppressWarnings({ "deprecation", "deprecation" })
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			String name = authentication.getName();
			String password = authentication.getCredentials().toString();
			logger.info("authenticate username ---" + name);
			if (name != null && password != null) {
				logger.info("authenticate user to remote EAI system");
				EaiLoginResponse response = remoteEaiAuthentication.authenticate(name, password);

				if (response.getOutputSchema().getStatus().equals("0")) {
					logger.info("user {} has been verified at EAI system", name);
					List<GrantedAuthority> grantedAuths = new ArrayList<>();
					List<UserRole> uRoles = roleRepository.findUserRolesByUsername(name);
					for(UserRole ur : uRoles) {
						grantedAuths.add(new SimpleGrantedAuthority(ur.getRole().getId()));
					}
					
					final Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
					return auth;
				} else {
					logger.info("user / password salah ");
					throw new BadCredentialsException(messageSource.getMessage("userid.password.incorrect.update", null, new Locale("en", "US")));
				}
			} else {
				logger.info("user / password salah ");
				throw new BadCredentialsException("Wrong userid or password");
			}
		} catch (Exception e) {
			logger.error("user / password salah with exception", e);
			throw new BadCredentialsException("Wrong userid or password");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
