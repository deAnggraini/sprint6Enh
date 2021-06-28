package id.co.bca.pakar.be.oauth2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "r_structure_icon")
public class StructureIcons extends EntityBase {
	@Id
	@SequenceGenerator(name = "structureIconSeqGen", sequenceName = "structureIconSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "structureIconSeqGen")
	private Long id;
	@ManyToOne
	@JoinColumn(name = "structure_id")
	private Structure structure;
	@ManyToOne
	@JoinColumn(name = "icon_id")
	private Icons icons;
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
	public Icons getIcons() {
		return icons;
	}
	public void setIcons(Icons icons) {
		this.icons = icons;
	}
}
