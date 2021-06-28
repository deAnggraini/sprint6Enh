package id.co.bca.pakar.be.oauth2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "r_menu_icon")
public class MenuIcons extends EntityBase {
	@Id
	@SequenceGenerator(name = "menuIconSeqGen", sequenceName = "menuIconSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "menuIconSeqGen")
	private Long id;
	@ManyToOne
	@JoinColumn(name = "menu_id")
	private Menu menu;
	@ManyToOne
	@JoinColumn(name = "icon_id")
	private Icons icons;
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
	public Icons getIcons() {
		return icons;
	}
	public void setIcons(Icons icons) {
		this.icons = icons;
	}
}
