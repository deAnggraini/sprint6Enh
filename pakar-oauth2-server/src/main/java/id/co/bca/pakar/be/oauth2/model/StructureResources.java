package id.co.bca.pakar.be.oauth2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//@Entity
//@Table(name = "r_structure_resources")
public class StructureResources {
	@Id
	@SequenceGenerator(name = "structureResourcesSeqGen", sequenceName = "structureResourcesSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "structureResourcesSeqGen")
	private Long id;
	@ManyToOne
	@JoinColumn(name = "structure_id")
	private Structure structure;
	@ManyToOne
	@JoinColumn(name = "resources_id")
	private Resources resources;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Structure getStructure() {
		return structure;
	}
	public void setStructure(Structure structure) {
		this.structure = structure;
	}
	public Resources getResources() {
		return resources;
	}
	public void setResources(Resources resources) {
		this.resources = resources;
	}
}
