package id.co.bca.pakar.be.doc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import io.swagger.models.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
//
//	@Bean
//	public TokenStore tokenStore() {
//		return new JdbcTokenStore(jdbcTemplate.getDataSource());
//	}
	
	@Override  
	  protected void configure(HttpSecurity http) throws Exception {  
	    http  
	        .httpBasic().disable()  
	        .formLogin().disable()  
	        .csrf().disable()  
	        .authorizeRequests(authorize -> authorize  
//	            .mvcMatchers(HttpMethod.GET, "/messages/**").hasAuthority("SCOPE_read")  
//	            .mvcMatchers(HttpMethod.POST, "/messages/**").hasAuthority("SCOPE_write")  
	            .anyRequest().authenticated()  
	        )  
	        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)  
	        .sessionManagement(sessionManagement ->  
	            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  
	    ;  
	  }  
}
