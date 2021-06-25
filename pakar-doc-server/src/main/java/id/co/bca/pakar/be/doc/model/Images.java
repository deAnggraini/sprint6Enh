package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "r_images")
public class Images extends EntityBase {
	@Id
	@SequenceGenerator(name = "imageSeqGen", sequenceName = "imageSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "imageSeqGen")
	private Long id;
	@Column(name = "image_name")
	private String imageName;
	@Column(name = "image_description")
	private String imageDescription;
	@Column(name = "uri")
	private String uri;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getImageDescription() {
		return imageDescription;
	}
	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
}
