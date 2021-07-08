package id.co.bca.pakar.be.oauth2.config;

import id.co.bca.pakar.be.oauth2.authenticate.CustomAuthenticationEntryPoint;
import id.co.bca.pakar.be.oauth2.authenticate.CustomAuthenticationProvider;
import id.co.bca.pakar.be.oauth2.authenticate.CustomExceptionTranslator;
import id.co.bca.pakar.be.oauth2.service.imp.CustomTokenServices;
import id.co.bca.pakar.be.oauth2.service.imp.CustomUserDetailsService;
import id.co.bca.pakar.be.oauth2.token.CustomJdbcTokenStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

@SuppressWarnings("deprecation")
@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${spring.security.jwt.signingkey}")
	private String signingKey;

	@Autowired
	private DataSource dataSource;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomExceptionTranslator customExceptionTranslator;

	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private CustomAuthenticationProvider authenticationProvider;

	@Bean("clientPasswordEncoder")
	PasswordEncoder clientPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		logger.info("configure AuthorizationServerEndpointsConfigurer");
		// add jwt token as token generator
		endpoints.authenticationManager(new ProviderManager(authenticationProvider));
		endpoints.tokenStore(tokenStore());
		endpoints.accessTokenConverter(accessTokenConverter());
		endpoints.userDetailsService(userDetailsService);
		endpoints.exceptionTranslator(customExceptionTranslator);
		endpoints.tokenServices(customTokenServices());
	}

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer cfg) throws Exception {
		logger.info("configure AuthorizationServerSecurityConfigurer");
		cfg.allowFormAuthenticationForClients();
		// Enable /oauth/token_key URL used by resource server to validate JWT tokens
		cfg.tokenKeyAccess("permitAll");
		// Enable /oauth/check_token URL
		cfg.checkTokenAccess("permitAll");
		cfg.passwordEncoder(clientPasswordEncoder());
		cfg.authenticationEntryPoint(customAuthenticationEntryPoint);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		logger.info("configure ClientDetailsServiceConfigurer");
		clients.jdbc(dataSource);
	}

	@Bean
	public TokenStore tokenStore() {
        logger.info("create bean TokenStore");
//        return new JwtTokenStore(accessTokenConverter());

		/**
		 * set token to database
		 */
//		if (tokenStore == null) {
//			tokenStore = new CustomJdbcTokenStore(dataSource);
//		}
		return new CustomJdbcTokenStore(dataSource);
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		logger.info("create bean JwtAccessConverter");
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(signingKey);
		return converter;
	}

	@Bean
	public  CustomTokenServices customTokenServices() {
		CustomTokenServices customTokenServices = new CustomTokenServices();
		customTokenServices.setSupportRefreshToken(true);
		customTokenServices.setReuseRefreshToken(false);
		customTokenServices.setTokenStore(tokenStore());
		customTokenServices.setTokenEnhancer(accessTokenConverter());
		customTokenServices.setClientDetailsService(clientDetailsService());
		return customTokenServices;
	}

	@Bean(name="myClientDetailsService")
	@Primary
	public JdbcClientDetailsService clientDetailsService() {
		return new JdbcClientDetailsService(dataSource);
	}
}
