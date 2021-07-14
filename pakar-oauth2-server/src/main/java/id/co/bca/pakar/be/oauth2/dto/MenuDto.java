package id.co.bca.pakar.be.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MenuDto {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("title")
	private String menuName;
	@JsonProperty("desc")
	private String menuDescription;
	@JsonProperty("icon")
	private String iconUri;
    @JsonProperty("image")
    private String imageUri;
	@JsonProperty("uri")
	private String uri;
	@JsonProperty("level")
	private Long level=1L;
	@JsonProperty("sort")
	private Long order = 1L;
	@JsonProperty("edit")
	private Boolean editStatus = Boolean.TRUE;
	@JsonProperty("menus")
	private List<MenuDto> menuChilds = new ArrayList<MenuDto>();
	@JsonProperty("parent")
	private Long parent = 0L;
	@JsonProperty("hasArticle")
	private Boolean hasArticle = Boolean.FALSE;

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

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
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


	public List<MenuDto> getMenuChilds() {
		return menuChilds;
	}

	public void setMenuChilds(List<MenuDto> menuChilds) {
		this.menuChilds = menuChilds;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public Boolean getHasArticle() {
		return hasArticle;
	}

	public void setHasArticle(Boolean hasArticle) {
		this.hasArticle = hasArticle;
	}

	@Override
	public String toString() {
		return "MenuDto{" +
				"id=" + id +
				", menuName='" + menuName + '\'' +
				", menuDescription='" + menuDescription + '\'' +
				", iconUri='" + iconUri + '\'' +
				", imageUri='" + imageUri + '\'' +
				", uri='" + uri + '\'' +
				", level=" + level +
				", order=" + order +
				", editStatus=" + editStatus +
				", menuChilds=" + menuChilds +
				", parent=" + parent +
				", hasArticle=" + hasArticle +
				'}';
	}
}
