package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "r_category")
public class Category extends EntityBase {
	@Id
	@SequenceGenerator(name = "categorySeqGen", sequenceName = "categorySeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "categorySeqGen")
	private Long id;
	@Column(name = "title", nullable = false, unique = true)
	private String title;
	@Column(name = "description")
	private String description;
	@Column(name = "sort")
	private Long sort;
	@Column(name = "level")
	private Long level;
	@ManyToOne
	@JoinColumn(name = "parent_category")
	private Category parentCategory;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getSort() {
		return sort;
	}
	public void setSort(Long sort) {
		this.sort = sort;
	}
	public Long getLevel() {
		return level;
	}
	public void setLevel(Long level) {
		this.level = level;
	}
	public Category getParentCategory() {
		return parentCategory;
	}
	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}
}
