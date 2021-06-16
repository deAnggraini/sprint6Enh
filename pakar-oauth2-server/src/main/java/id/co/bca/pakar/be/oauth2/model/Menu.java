package id.co.bca.pakar.be.oauth2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "r_menu")
public class Menu extends EntityBase {
	@Id
	@SequenceGenerator(name = "menuSeqGen", sequenceName = "menuSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "menuSeqGen")
	private Long id;
	@Column(name = "menu_name")
	private String menuName;
	@Column(name = "menu_description")
	private String menuDescription;
	@ManyToOne
	@JoinColumn(name = "parent_menu")
	private Menu parentMenu;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the menuName
	 */
	public String getMenuName() {
		return menuName;
	}
	/**
	 * @param menuName the menuName to set
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	/**
	 * @return the menuDescription
	 */
	public String getMenuDescription() {
		return menuDescription;
	}
	/**
	 * @param menuDescription the menuDescription to set
	 */
	public void setMenuDescription(String menuDescription) {
		this.menuDescription = menuDescription;
	}
	/**
	 * @return the parentMenu
	 */
	public Menu getParentMenu() {
		return parentMenu;
	}
	/**
	 * @param parentMenu the parentMenu to set
	 */
	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}
	
}
