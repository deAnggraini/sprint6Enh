package id.co.bca.pakar.be.doc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

//@EnableResourceServer
@SpringBootApplication
public class DocumentServerApplication {
	private static final Logger logger = LoggerFactory.getLogger(DocumentServerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DocumentServerApplication.class, args);
	}

//	@RequestMapping("/validateUser")
//	public Principal user(Principal user) {
//		return user;
//	}

}
