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
	@JoinColumn(name = "structure_id", nullable = false)
	private Structure structure;

	@Column(name ="article_template_id")
	private Long articleTemplate;

	@Column(name = "title", unique = true, nullable = false)
	private String judulArticle;

	@Column(name = "article_used_by")
	private String articleUsedBy;

	@Column(name = "short_desc", columnDefinition="TEXT", length = 1000)
	private String shortDescription;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public Long getArticleTemplate() {
		return articleTemplate;
	}

	public void setArticleTemplate(Long articleTemplate) {
		this.articleTemplate = articleTemplate;
	}

	public String getJudulArticle() {
		return judulArticle;
	}

	public void setJudulArticle(String judulArticle) {
		this.judulArticle = judulArticle;
	}

	public String getArticleUsedBy() {
		return articleUsedBy;
	}

	public void setArticleUsedBy(String articleUsedBy) {
		this.articleUsedBy = articleUsedBy;
	}
}
