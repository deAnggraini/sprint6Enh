package id.co.bca.pakar.be.oauth2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
	private Images images;
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

	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}
}
