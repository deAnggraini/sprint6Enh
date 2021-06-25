package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "r_structure_image")
public class StructureImages extends EntityBase {
	@Id
	@SequenceGenerator(name = "structureImageSeqGen", sequenceName = "structureImageSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "structureImageSeqGen")
	private Long id;
	@ManyToOne
	@JoinColumn(name = "structure_id")
	private Structure structure;
	@ManyToOne
	@JoinColumn(name = "image_id")
	private Images imagesicons;
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
	public Images getImagesicons() {
		return imagesicons;
	}
	public void setImagesicons(Images imagesicons) {
		this.imagesicons = imagesicons;
	}
}
