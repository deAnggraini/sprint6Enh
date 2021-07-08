package id.co.bca.pakar.be.doc.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    
    @Value("${spring.rest.timeout.read}")
	private String readTimeOut;

	@Value("${spring.rest.timeout.connection}")
	private String connectionTimeout;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		// Do any additional configuration here
		Duration readTimeOutInMiliSecond = Duration.ofMillis(Integer.valueOf(readTimeOut));
		Duration writeTimeOutInMiliSecond = Duration.ofMillis(Integer.valueOf(connectionTimeout));
		RestTemplate restTemplate = builder.setConnectTimeout(writeTimeOutInMiliSecond).setReadTimeout(readTimeOutInMiliSecond).build();

		return restTemplate;
	}
}
