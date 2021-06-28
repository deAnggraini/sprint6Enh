package id.co.bca.pakar.be.oauth2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "r_structure")
public class Structure extends EntityBase{
	@Id
	@SequenceGenerator(name = "structureSeqGen", sequenceName = "strcutureSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "structureSeqGen")
	private Long id;
	@Column(name = "struct_name")
	private String structureName;
	@Column(name = "struct_description")
	private String structureDescription;
	@Column(name = "order")
	private Long order;
	@Column(name = "level")
	private Long level;
	@ManyToOne
	@JoinColumn(name = "parent_structure")
	private Menu parentStructure;

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

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public Menu getParentStructure() {
		return parentStructure;
	}

	public void setParentStructure(Menu parentStructure) {
		this.parentStructure = parentStructure;
	}
}
