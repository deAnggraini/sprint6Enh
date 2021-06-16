package id.co.bca.pakar.be.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import id.co.bca.pakar.be.oauth2.authenticate.CustomAuthenticationProvider;
import id.co.bca.pakar.be.oauth2.eai.RemoteEaiAuthentication;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomAuthenticationProvider authenticationProvider;
	
//	@Override
//	protected void configure(HttpSecurity httpSecurity) {
//		try {
//			httpSecurity
//			  .antMatcher("/**")
//			  .authorizeRequests()
//			  .and()
//			  .authorizeRequests().anyRequest().authenticated();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.anonymous().disable().csrf().disable().cors();
	}

	@Bean
	public AuthenticationProvider getAuthenticationProvider() throws Exception {
		return new CustomAuthenticationProvider();
	}
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public RemoteEaiAuthentication getRemoteEaiAuthenticcation() {
		return new RemoteEaiAuthentication();
	}
}
