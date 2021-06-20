package id.co.bca.pakar.be.oauth2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class OAuth2ServerApplication {
	private static final Logger logger = LoggerFactory.getLogger(OAuth2ServerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(OAuth2ServerApplication.class, args);

//        ApplicationContext ctx = SpringApplication.run(OAuth2ServerApplication.class, args);
//
//        System.out.println("###### Inspect the beans provided by Spring Boot:");
//        logger.info("###### Inspect the beans provided by Spring Boot:");
//
//        String[] beanNames = ctx.getBeanDefinitionNames();
//        Arrays.sort(beanNames);
//        System.out.println("###### Number beans provided by Spring Boot: [{}]"+ beanNames.length);
//        logger.info("###### Number beans provided by Spring Boot: [{}]"+ beanNames.length);
	}

//	@RequestMapping("/validateUser")
//	public Principal user(Principal user) {
//		return user;
//	}

}
