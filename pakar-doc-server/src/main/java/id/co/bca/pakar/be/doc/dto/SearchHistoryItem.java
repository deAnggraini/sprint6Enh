package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchHistoryItem {
	@JsonProperty("id")
	private String id;
	@JsonProperty("title")
	private String title;
	
	
	public SearchHistoryItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SearchHistoryItem(String id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
