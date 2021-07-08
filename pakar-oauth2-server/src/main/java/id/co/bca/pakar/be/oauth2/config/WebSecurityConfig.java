package id.co.bca.pakar.be.oauth2.config;

import id.co.bca.pakar.be.oauth2.authenticate.CustomAuthenticationProvider;
import id.co.bca.pakar.be.oauth2.service.imp.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

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
        http.anonymous().disable().csrf().disable().cors().disable();
    }

    @Bean
    public AuthenticationProvider getAuthenticationProvider() throws Exception {
        return new CustomAuthenticationProvider();
    }

    @Bean("userPasswordEncoder")
    public BCryptPasswordEncoder userPasswordEncoder() {
        //        return new BCryptPasswordEncoder(4);
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(userPasswordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//	@Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "X-USERNAME", "refreshToken"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
