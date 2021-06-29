package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class MenuDto {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("title")
	private String menuName;
	@JsonProperty("desc")
	private String menuDescription;
	@JsonProperty("icon")
	private String iconUri;
	@JsonProperty("uri")
	private String uri;
	@JsonProperty("level")
	private Long level=1L;
	@JsonProperty("sort")
	private Long order = 1L;
	@JsonProperty("edit")
	private Boolean editStatus = Boolean.TRUE;
//	@JsonProperty("menus")
//	private Set<MenuDto> menuChilds = new HashSet<MenuDto>();
	private Long parent = 0L;
	private String code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuDescription() {
		return menuDescription;
	}

	public void setMenuDescription(String menuDescription) {
		this.menuDescription = menuDescription;
	}

	public String getIconUri() {
		return iconUri;
	}

	public void setIconUri(String iconUri) {
		this.iconUri = iconUri;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public Boolean getEditStatus() {
		return editStatus;
	}

	public void setEditStatus(Boolean editStatus) {
		this.editStatus = editStatus;
	}
	
	

//	public Set<MenuDto> getMenuChilds() {
//		return menuChilds;
//	}
//
//	public void setMenuChilds(Set<MenuDto> menuChilds) {
//		this.menuChilds = menuChilds;
//	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "MenuDto{" +
				"id=" + id +
				", menuName='" + menuName + '\'' +
				", menuDescription='" + menuDescription + '\'' +
				", iconUri='" + iconUri + '\'' +
				", uri='" + uri + '\'' +
				", level=" + level +
				", order=" + order +
				", editStatus=" + editStatus +
				'}';
	}
}
