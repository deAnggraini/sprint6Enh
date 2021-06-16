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

	@GetMapping("/api/doc/theme")
	public String theme() {
		return "secret message";
	}

	@GetMapping("/api/doc/categori-article")
	public String categoriArticle(@RequestBody String message) {
		return String.format("Message was created. Content: %s", message);
	}
	
	@PostMapping("/api/doc/recomendation")
	public String recomendation(@RequestBody String message) {
		return String.format("Message was created. Content: %s", message);
	}
	
	@PostMapping("/api/doc/news")
	public String news(@RequestBody String message) {
		return String.format("Message was created. Content: %s", message);
	}
	
	@PostMapping("/api/doc/popular")
	public String popular(@RequestBody String message) {
		return String.format("Message was created. Content: %s", message);
	}
	
	@PostMapping("/api/doc/search")
	public String search(@RequestBody String message) {
		return String.format("Message was created. Content: %s", message);
	}
}
