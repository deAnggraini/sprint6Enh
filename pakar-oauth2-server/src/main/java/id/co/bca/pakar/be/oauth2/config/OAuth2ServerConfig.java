package id.co.bca.pakar.be.oauth2.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.AuthenticationEntryPoint;

import id.co.bca.pakar.be.oauth2.prop.SecurityProperties;
import id.co.bca.pakar.be.oauth2.token.CustomJdbcTokenStore;

@SuppressWarnings("deprecation")
@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(SecurityProperties.class)
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${spring.security.oauth2.clientId}")
	private String clientId;
	@Value("${spring.security.oauth2.clientSecret}")
	private String clientSecret;
	@Value("${spring.security.oauth2.authorize-grant-type:password,refresh_token}")
	private String[] authorizedGrantTypes;
	@Value("${spring.security.jwt.signingkey}")
	private String signingKey;
	@Value("${spring.security.oauth2.accessTokenValiditySeconds:43200}")
	private String accessTokenValiditySeconds;
	@Value("${spring.security.oauth2.refreshTokenValiditySeconds:2592000}")
	private String refreshTokenValiditySeconds;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;

	@Autowired
	private WebResponseExceptionTranslator customWebResponseExceptionTranslator;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		//		endpoints.authenticationManager(authenticationManager);
		// add jwt token as token generator
		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore)
		.accessTokenConverter(accessTokenConverter)
		.exceptionTranslator(customWebResponseExceptionTranslator);
	}

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()")
		.authenticationEntryPoint(authenticationEntryPoint());
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		logger.info("configure ClientDetailsServiceConfigurer");		
		clients.jdbc(dataSource)
			.passwordEncoder(passwordEncoder);
	}

	@Bean
	public TokenStore tokenStore() {
		//		return new JwtTokenStore(accessTokenConverter());
		logger.info("------ create bean TokenStore ------ ");
		/**
		 * set token to database
		 */
		if (tokenStore == null) {
			tokenStore = new CustomJdbcTokenStore(dataSource);
		}
		return tokenStore;
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		logger.info("------ create bean JwtAccessConverter ------ ");
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(signingKey);
		return converter;
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenService(final ClientDetailsService clientDetailsService) {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore);
		defaultTokenServices.setSupportRefreshToken(true);
		defaultTokenServices.setClientDetailsService(clientDetailsService);
		defaultTokenServices.setAuthenticationManager(authenticationManager);
		logger.info("converter ------ " + defaultTokenServices);

		return defaultTokenServices;
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new CustomAuthenticationEntryPoint();
	}
}
