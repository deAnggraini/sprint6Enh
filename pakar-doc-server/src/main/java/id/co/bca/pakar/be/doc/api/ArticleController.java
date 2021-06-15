package id.co.bca.pakar.be.doc.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {
	@RequestMapping("/api/doc/test")
	public String user() {
		return "test resource server";
	}

//	@GetMapping("/")
//	public String index(@AuthenticationPrincipal Jwt jwt) {
//		return String.format("Hello, %s!", jwt.getSubject());
//	}

	@GetMapping("/message")
	public String message() {
		return "secret message";
	}

	@PostMapping("/message")
	public String createMessage(@RequestBody String message) {
		return String.format("Message was created. Content: %s", message);
	}
}
