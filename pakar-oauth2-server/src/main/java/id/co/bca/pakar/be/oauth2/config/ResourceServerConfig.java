package id.co.bca.pakar.be.oauth2.config;

import id.co.bca.pakar.be.oauth2.authenticate.CustomAccessDeniedHandler;
import id.co.bca.pakar.be.oauth2.authenticate.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@SuppressWarnings("deprecation")
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/swagger-ui.html",
            "/api/auth/login",
            "/api/auth/logout",
            "/api/auth/refreshToken"
            // other public endpoints of your API may be appended to this array
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/api/auth/get**").authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        // format message
        resources.authenticationEntryPoint(customAuthenticationEntryPoint);
        resources.accessDeniedHandler(customAccessDeniedHandler);
    }
}
