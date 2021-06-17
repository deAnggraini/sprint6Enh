package id.co.bca.pakar.be.doc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "r_article_category")
public class ArticleCategory extends EntityBase {
	@Id
	@SequenceGenerator(name = "articleCategorySeqGen", sequenceName = "articleCategorySeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "articleCategorySeqGen")
	private Long id;
	@Column(name = "title", nullable = false, unique = true)
	private String title;
	@Column(name = "description")
	private String description;
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
}
