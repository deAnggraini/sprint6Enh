package id.co.bca.pakar.be.doc.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchHistoryDto {
	@JsonProperty("id")
	private String id;
	@JsonProperty("parent")
	private String parent;
	@JsonProperty("items")
	private List<SearchHistoryItem> items = new ArrayList<SearchHistoryItem>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public List<SearchHistoryItem> getItems() {
		return items;
	}
	public void setItems(List<SearchHistoryItem> items) {
		this.items = items;
	}
}
