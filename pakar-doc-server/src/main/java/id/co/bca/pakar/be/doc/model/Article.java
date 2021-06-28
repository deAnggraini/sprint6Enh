package id.co.bca.pakar.be.doc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "t_article")
public class Article extends EntityBase {
	@Id
	@SequenceGenerator(name = "articleSeqGen", sequenceName = "articleSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "articleSeqGen")
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "article_category_id", nullable = false)
	private ArticleCategory articleCategory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}
}
