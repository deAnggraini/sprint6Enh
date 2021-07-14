package id.co.bca.pakar.be.doc.config;

import id.co.bca.pakar.be.doc.authenticate.CustomRemoteTokenServices;
import id.co.bca.pakar.be.doc.security.CustomAccessDeniedHandler;
import id.co.bca.pakar.be.doc.security.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Value("${spring.security.oauth2.clientId}")
	private String clientId;
	@Value("${spring.security.oauth2.clientSecret}")
	private String clientSecret;
	@Value("${spring.security.oauth2.resourceserver.token-info-uri}")
	private String checkTokenUri;

	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;

	private static final String[] AUTH_WHITELIST = {
			// -- swagger ui
			"/swagger-resources",
			"/swagger-resources/**",
			"/swagger-ui.html",
			"/api/v1/doc/theme"
			// other public endpoints of your API may be appended to this array
	};

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(AUTH_WHITELIST).permitAll()
				.antMatchers("/api/doc/**").authenticated();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenServices(tokenService());
		resources.authenticationEntryPoint(customAuthenticationEntryPoint);
		resources.accessDeniedHandler(customAccessDeniedHandler);
	}

	@Bean
	public ResourceServerTokenServices tokenService() {
		CustomRemoteTokenServices remoteTokenServices = new CustomRemoteTokenServices();
		remoteTokenServices.setCheckTokenEndpointUrl(checkTokenUri);
		remoteTokenServices.setClientId(clientId);
		remoteTokenServices.setClientSecret(clientSecret);
		return remoteTokenServices;
	}
}
