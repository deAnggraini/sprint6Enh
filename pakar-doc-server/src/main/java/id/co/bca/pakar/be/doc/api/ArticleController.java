package id.co.bca.pakar.be.doc.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.bca.pakar.be.doc.dto.SearchHistoryDto;
import id.co.bca.pakar.be.doc.util.JSONMapperAdapter;

@RestController
public class ArticleController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/api/doc/test")
	public String user() {
		return "test resource server";
	}

	@GetMapping("/api/doc/theme")
	public String theme() {
		return "secret message";
	}

	// pindah ke service menu
//	@GetMapping("/api/doc/categori-article")
//	public String categoriArticle(@RequestBody String message) {
//		return String.format("Message was created. Content: %s", message);
//	}
	
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
	public ResponseEntity<RestResponse<SearchHistoryDto>> search(@RequestBody String message) {
		logger.info("search article process");
		
		String jsonString = " [\r\n"
				+ "    {\r\n"
				+ "        id: 1,\r\n"
				+ "        parent: 'PAKAR',\r\n"
				+ "        items: [\r\n"
				+ "            { id: 1, title: 'Tahapan' },\r\n"
				+ "            { id: 2, title: 'Tahapan Gold' },\r\n"
				+ "            { id: 3, title: 'TAHAKA' },\r\n"
				+ "            { id: 4, title: 'Xpresi' },\r\n"
				+ "            { id: 5, title: 'Time Loan SME' },\r\n"
				+ "            { id: 5, title: 'GIRO' },\r\n"
				+ "        ]\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        id: 2,\r\n"
				+ "        parent: 'FAQ',\r\n"
				+ "        items: [\r\n"
				+ "            { id: 11, title: 'Bagaimana solusi ketika Teller melakukan input kode penalti pada nasabah' },\r\n"
				+ "            { id: 12, title: 'Apakah bisa membuka Deposito dari Data RTGS Masuk?' },\r\n"
				+ "        ]\r\n"
				+ "    },\r\n"
				+ "]";
		
		SearchHistoryDto searchDto = (SearchHistoryDto) JSONMapperAdapter.jsonToObject(jsonString, SearchHistoryDto.class);
		return createResponse(searchDto, "OO", "SUCCESS");
	}
}
