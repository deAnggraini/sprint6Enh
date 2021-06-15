package id.co.bca.pakar.be.doc.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

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
	private DataSource dataSource;

	private static final String[] AUTH_WHITELIST = {
			// -- swagger ui
			"/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/swagger-ui.html", "/webjars/**", "/api/doc/test",
//            "/login/**",
			"/api/v1//getActiveRole"
			// other public endpoints of your API may be appended to this array
	};

	@Override
	public void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
//				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//				.antMatchers("/api/doc/**").authenticated();
		
		http.authorizeRequests()
        	.antMatchers("/api/doc/**")
        	.authenticated();
	}

//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        // format message
//        resources.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
//        resources.accessDeniedHandler(new CustomAccessDeniedHandler());
//    }
//	@Override
//	public void configure(ResourceServerSecurityConfigurer resources) {
//		resources.tokenStore(tokenStore());
//	}
//
//	@Bean
//	public TokenStore tokenStore() {
//		return new JdbcTokenStore(dataSource);
//	}
	
	@Bean()
	@Primary
	protected RemoteTokenServices remoteToken(){
		RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
		remoteTokenServices.setCheckTokenEndpointUrl(checkTokenUri);
		remoteTokenServices.setClientId(clientId);
		remoteTokenServices.setClientSecret(clientSecret);
		return remoteTokenServices;
	}

//	    @Bean()
//	protected RemoteTokenServices secondTokenServices(){
//		RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
//		remoteTokenServices.setCheckTokenEndpointUrl("http://localhost:8080/oauth-server/oauth/check_token");
//		remoteTokenServices.setClientId("second");
//		remoteTokenServices.setClientSecret("secret");
//		return remoteTokenServices;
//	}}
}
