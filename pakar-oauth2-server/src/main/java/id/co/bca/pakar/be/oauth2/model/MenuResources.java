package id.co.bca.pakar.be.oauth2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//@Entity
//@Table(name = "r_menu_resources")
public class MenuResources {
	@Id
	@SequenceGenerator(name = "menuResourcesSeqGen", sequenceName = "menuResourcesSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "menuResourcesSeqGen")
	private Long id;
	@ManyToOne
	@JoinColumn(name = "menu_id")
	private Menu menu;
	@ManyToOne
	@JoinColumn(name = "resources_id")
	private Resources resources;
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
	public Resources getResources() {
		return resources;
	}
	public void setResources(Resources resources) {
		this.resources = resources;
	}
}
