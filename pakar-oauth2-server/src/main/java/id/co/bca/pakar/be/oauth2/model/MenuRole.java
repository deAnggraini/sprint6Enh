package id.co.bca.pakar.be.oauth2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "r_menu_role")
public class MenuRole extends EntityBase {
	@Id
	@SequenceGenerator(name = "menuRoleSeqGen", sequenceName = "menuRoleSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "menuRoleSeqGen")
	private Long id;
	@ManyToOne
	@JoinColumn(name = "menu_id", nullable = false)
	private Menu menu;
	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}
