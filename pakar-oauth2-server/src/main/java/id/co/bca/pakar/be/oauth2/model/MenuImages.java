package id.co.bca.pakar.be.oauth2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "r_menu_image")
public class MenuImages extends EntityBase {
	@Id
	@SequenceGenerator(name = "menuImageSeqGen", sequenceName = "menuImageSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "menuImageSeqGen")
	private Long id;
	@ManyToOne
	@JoinColumn(name = "menu_id")
	private Menu menu;
	@ManyToOne
	@JoinColumn(name = "image_id")
	private Images images;
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
	public Images getImages() {
		return images;
	}
	public void setImages(Images images) {
		this.images = images;
	}
}
