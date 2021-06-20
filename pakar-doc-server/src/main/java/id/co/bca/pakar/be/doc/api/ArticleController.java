package id.co.bca.pakar.be.doc.api;

import java.util.ArrayList;
import java.util.List;

import id.co.bca.pakar.be.doc.dto.ThemeDto;
import id.co.bca.pakar.be.doc.model.Theme;
import id.co.bca.pakar.be.doc.service.ThemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import id.co.bca.pakar.be.doc.dto.SearchHistoryDto;
import id.co.bca.pakar.be.doc.dto.SearchHistoryItem;
import id.co.bca.pakar.be.doc.util.JSONMapperAdapter;

@RestController
public class ArticleController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ThemeService themeService;

	@GetMapping("/api/v1/doc/theme")
	public ResponseEntity<RestResponse<ThemeDto>> theme(@RequestHeader("Authorization") String authToken) {
		logger.info("theme process");
		logger.info("authToken" +authToken);

		ThemeDto themeDto = themeService.getThemeList();

		logger.info("themeDto" +themeDto);

		return createResponse(themeDto, "OO", "SUCCESS");
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
	public ResponseEntity<RestResponse<List<SearchHistoryDto>>> search(@RequestBody String message) {
		logger.info("search article process");
		
//		String jsonString = " [\r\n"
//				+ "    {\r\n"
//				+ "        id: 1,\r\n"
//				+ "        parent: 'PAKAR',\r\n"
//				+ "        items: [\r\n"
//				+ "            { id: 1, title: 'Tahapan' },\r\n"
//				+ "            { id: 2, title: 'Tahapan Gold' },\r\n"
//				+ "            { id: 3, title: 'TAHAKA' },\r\n"
//				+ "            { id: 4, title: 'Xpresi' },\r\n"
//				+ "            { id: 5, title: 'Time Loan SME' },\r\n"
//				+ "            { id: 5, title: 'GIRO' }\r\n"
//				+ "        ]\r\n"
//				+ "    },\r\n"
//				+ "    {\r\n"
//				+ "        id: 2,\r\n"
//				+ "        parent: 'FAQ',\r\n"
//				+ "        items: [\r\n"
//				+ "            { id: 11, title: 'Bagaimana solusi ketika Teller melakukan input kode penalti pada nasabah' },\r\n"
//				+ "            { id: 12, title: 'Apakah bisa membuka Deposito dari Data RTGS Masuk?' }\r\n"
//				+ "        ]\r\n"
//				+ "    }\r\n"
//				+ "]";
		
		// next change to db
		List<SearchHistoryDto> list = new ArrayList<SearchHistoryDto>();
		SearchHistoryDto searcHistoryDto = new SearchHistoryDto();
		searcHistoryDto.setId("1");
		searcHistoryDto.setParent("PAKAR");
		searcHistoryDto.getItems().add(new SearchHistoryItem("1", "Tahapan"));
		searcHistoryDto.getItems().add(new SearchHistoryItem("2", "Tahapan Gold"));
		searcHistoryDto.getItems().add(new SearchHistoryItem("3", "TAHAKA"));
		searcHistoryDto.getItems().add(new SearchHistoryItem("5", "Xpresi"));
		searcHistoryDto.getItems().add(new SearchHistoryItem("5", "Time Loan SME"));
		searcHistoryDto.getItems().add(new SearchHistoryItem("6", "GIRO"));
		list.add(searcHistoryDto);
		searcHistoryDto = new SearchHistoryDto();
		searcHistoryDto.setId("2");
		searcHistoryDto.setParent("FAQ");
		searcHistoryDto.getItems().add(new SearchHistoryItem("1", "Bagaimana solusi ketika Teller melakukan input kode penalti pada nasabah"));
		searcHistoryDto.getItems().add(new SearchHistoryItem("2", "Apakah bisa membuka Deposito dari Data RTGS Masuk?"));
		list.add(searcHistoryDto);
		String jsonString = JSONMapperAdapter.objectToJson(list);
		logger.info("json input value "+jsonString);
		
		return createResponse(list, "00", "SUCCESS");
	}
}
