package id.co.bca.pakar.be.oauth2.dto;

import java.util.HashSet;
import java.util.Set;

public class MenuDto {
	private Long id;
	private String menuCode;
	private String menuName;
	private String menuDescription;
	private String menuIcon;
	private String menuImage;
	private String uri;
	private Long level;
	private Long order;
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
	public String getMenuIcon() {
		return menuIcon;
	}
	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}
	public String getMenuImage() {
		return menuImage;
	}
	public void setMenuImage(String menuImage) {
		this.menuImage = menuImage;
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
	public Set<MenuDto> getMenuChilds() {
		return menuChilds;
	}
	public void setMenuChilds(Set<MenuDto> menuChilds) {
		this.menuChilds = menuChilds;
	}
}
