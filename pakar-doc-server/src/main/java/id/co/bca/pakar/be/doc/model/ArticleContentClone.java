package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_article_content_clone")
public class ArticleContentClone extends CommonArticleContent {
    @Id
    @Column(name = "id")
    private Long id = 0L;

    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0")
    private Long version;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
