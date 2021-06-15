package id.co.bca.pakar.be.oauth2.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import id.co.bca.pakar.be.oauth2.prop.SecurityProperties;

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
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;

//	@Autowired
//	private JdbcTemplate jdbcTemplate;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		endpoints.authenticationManager(authenticationManager);
		// add jwt token as token generator
		endpoints
			.authenticationManager(authenticationManager)
			.tokenStore(tokenStore)
			.accessTokenConverter(accessTokenConverter);
	}

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(clientId).secret(passwordEncoder.encode(clientSecret))
				.authorizedGrantTypes(authorizedGrantTypes)
				.accessTokenValiditySeconds(Integer.parseInt(accessTokenValiditySeconds))
				.refreshTokenValiditySeconds(Integer.parseInt(refreshTokenValiditySeconds)).scopes("read", "write");
	}
	
//	@Override
//    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.jdbc(dataSource);
//    }

//	@Bean
//	@Primary
//	// Making this primary to avoid any accidental duplication with another token
//	// service instance of the same name
//	public DefaultTokenServices tokenServices() {
//		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//		defaultTokenServices.setTokenStore(tokenStore);
//		defaultTokenServices.setSupportRefreshToken(true);
//		defaultTokenServices.setClientDetailsService(clientDetails());
//		return defaultTokenServices;
//	}	

//	@Bean
//	public ClientDetailsService clientDetails() {
//		JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(jdbcTemplate.getDataSource());
//		jdbcClientDetailsService.setPasswordEncoder(passwordEncoder);
//		return jdbcClientDetailsService;
//	}
//
	@Bean
	public TokenStore tokenStore() {
//		return new JwtTokenStore(accessTokenConverter());
		/**
		 * set token to database
		 */
		if (tokenStore == null) {
            tokenStore = new JdbcTokenStore(dataSource);
        }
        return tokenStore;
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(signingKey);
		return converter;
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenService(final ClientDetailsService clientDetailsService) {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore);
		defaultTokenServices.setTokenEnhancer(accessTokenConverter);
		defaultTokenServices.setSupportRefreshToken(true);
		defaultTokenServices.setClientDetailsService(clientDetailsService);
		defaultTokenServices.setAuthenticationManager(authenticationManager);
		logger.info("##### converter : " + defaultTokenServices);

		return defaultTokenServices;
	}
}
