package id.co.bca.pakar.be.oauth2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//@Entity
//@Table(name = "r_resources")
public class Resources extends EntityBase {
	@Id
	@SequenceGenerator(name = "resourcesSeqGen", sequenceName = "resourcesSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "resourcesSeqGen")
	private Long id;
	@Column(name = "uri")
	private String uri;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
}
