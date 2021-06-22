package id.co.bca.pakar.be.oauth2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "r_icons")
public class Icons extends EntityBase {
	@Id
	@SequenceGenerator(name = "iconSeqGen", sequenceName = "iconSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "iconSeqGen")
	private Long id;
	@Column(name = "icon_name")
	private String iconName;
	@Column(name = "icon_description")
	private String iconDescription;
	@Column(name = "uri")
	private String uri;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIconName() {
		return iconName;
	}
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
	public String getIconDescription() {
		return iconDescription;
	}
	public void setIconDescription(String iconDescription) {
		this.iconDescription = iconDescription;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
}
