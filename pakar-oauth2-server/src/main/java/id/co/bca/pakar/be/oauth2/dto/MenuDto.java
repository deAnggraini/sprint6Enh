package id.co.bca.pakar.be.oauth2.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuDto {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("menu_code")
	private String menuCode;
	@JsonProperty("menu_name")
	private String menuName;
	@JsonProperty("menu_description")
	private String menuDescription;
	@JsonProperty("icon")
	private IconDto iconDto;
	@JsonProperty("image")
	private ImageDto imageDto;
	@JsonProperty("uri")
	private String uri;
	@JsonProperty("level")
	private Long level;
	@JsonProperty("order")
	private Long order;
	@JsonProperty("edit")
	private Boolean editStatus = Boolean.FALSE;
	@JsonProperty("childs")
	private Set<MenuDto> menuChilds = new HashSet<MenuDto>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
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
	
	public IconDto getIconDto() {
		return iconDto;
	}
	public void setIconDto(IconDto iconDto) {
		this.iconDto = iconDto;
	}
	public ImageDto getImageDto() {
		return imageDto;
	}
	public void setImageDto(ImageDto imageDto) {
		this.imageDto = imageDto;
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
	public Set<MenuDto> getMenuChilds() {
		return menuChilds;
	}
	public void setMenuChilds(Set<MenuDto> menuChilds) {
		this.menuChilds = menuChilds;
	}
}
