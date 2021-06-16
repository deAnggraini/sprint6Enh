package id.co.bca.pakar.be.oauth2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "r_role")
public class Role extends EntityBase {
	@Id
	@Column(name = "role_id", nullable = false, unique = true)
	private String id;	
	@Column(name = "description", nullable = true)
	private String description;
	
	public Role() {

	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
