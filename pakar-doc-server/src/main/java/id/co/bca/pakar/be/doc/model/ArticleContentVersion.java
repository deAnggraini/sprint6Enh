package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_article_content_version")
public class ArticleContentVersion extends CommonArticleContent {
    @Id
    @Column(name = "id")
    private Long id = 0L;

    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;

    @ManyToOne
    @JoinColumn(name = "article_version_id")
    private ArticleVersion articleVersion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArticleVersion getArticleVersion() {
        return articleVersion;
    }

    public void setArticleVersion(ArticleVersion articleVersion) {
        this.articleVersion = articleVersion;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
