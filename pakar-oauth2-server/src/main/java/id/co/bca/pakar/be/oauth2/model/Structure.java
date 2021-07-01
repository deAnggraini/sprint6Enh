package id.co.bca.pakar.be.oauth2.model;

import javax.persistence.*;

@Entity
@Table(name = "r_structure")
public class Structure extends EntityBase {
	@Id
	@SequenceGenerator(name = "structureSeqGen", sequenceName = "strcutureSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "structureSeqGen")
	private Long id;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}
