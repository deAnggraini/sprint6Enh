package id.co.bca.pakar.be.doc.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
	@Value("${spring.datasource.url}")
    private String url;
    
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    
    @Value("${spring.datasource.username}")
    private String username;
    
    @Value("${spring.datasource.password}")
    private String password;
    
    @Value("${spring.rest.timeout.read}")
	private String readTimeOut;

	@Value("${spring.rest.timeout.connection}")
	private String connectionTimeout;
    
//    @Bean
//    public DataSource dataSource() {
//        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        
//        dataSource.setDriverClassName(driverClassName);
//        dataSource.setUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        
//        return dataSource;
//    }
    
//    @Bean
//    public TokenStore tokenStore() {
//        return new JdbcTokenStore(dataSource());
//    }
    
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		// Do any additional configuration here
		Duration readTimeOutInMiliSecond = Duration.ofMillis(Integer.valueOf(readTimeOut));
		Duration writeTimeOutInMiliSecond = Duration.ofMillis(Integer.valueOf(connectionTimeout));
		RestTemplate restTemplate = builder.setConnectTimeout(writeTimeOutInMiliSecond).setReadTimeout(readTimeOutInMiliSecond).build();

		return restTemplate;
	}
}
