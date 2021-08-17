package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "r_structure")
public class Structure extends EntityBase{
	@Id
	@SequenceGenerator(name = "structureSeqGen", sequenceName = "structureSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "structureSeqGen")
	private Long id;
	@Version
	@Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
	private Long version;
	@Column(name = "name")
	private String structureName;
	@Column(name = "description")
	private String structureDescription;
	@Column(name = "sort")
	private Long sort;
	@Column(name = "level")
	private Long level;
	@Column(name = "parent")
	private Long parentStructure;
	@Column(name = "edit")
	private Boolean edit;
	@Column(name = "uri")
	private String uri;
	@Column(name = "location")
	private String location;
	@Column(name = "location_text")
	private String location_text;
	// flag to identify structur has article or no, for reader role structur appeared if structure has published structure
	@Column(name = "has_article")
	private Boolean isHasArticle = Boolean.FALSE;
	@Column(name = "breadcumb")
	private String breadCumb;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getStructureName() {
		return structureName;
	}

	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}

	public String getStructureDescription() {
		return structureDescription;
	}

	public void setStructureDescription(String structureDescription) {
		this.structureDescription = structureDescription;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public Long getParentStructure() {
		return parentStructure;
	}

	public void setParentStructure(Long parentStructure) {
		this.parentStructure = parentStructure;
	}

	public Boolean getEdit() {
		return edit;
	}

	public void setEdit(Boolean edit) {
		this.edit = edit;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation_text() {
		return location_text;
	}

	public void setLocation_text(String location_text) {
		this.location_text = location_text;
	}

	public Boolean getHasArticle() {
		return isHasArticle;
	}

	public void setHasArticle(Boolean hasArticle) {
		isHasArticle = hasArticle;
	}

	public String getBreadCumb() {
		return breadCumb;
	}

	public void setBreadCumb(String breadCumb) {
		this.breadCumb = breadCumb;
	}
}
