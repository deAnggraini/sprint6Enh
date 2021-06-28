package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

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
