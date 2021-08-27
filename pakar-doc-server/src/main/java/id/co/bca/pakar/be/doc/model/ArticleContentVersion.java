package id.co.bca.pakar.be.doc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "t_article_content_version")
public class ArticleContentVersion extends CommonArticleContent {
    @Id
    @GenericGenerator(name = "UUID",  strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    @Column(name = "id")
    private String id;

    @Column(name = "origin_article_content_id")
    private Long originArticleContentId;

    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0")
    private Long version;

    @ManyToOne
    @JoinColumn(name = "article_version_id")
    private ArticleVersion articleVersion;

    @Column(name = "origin_article_id")
    private Long articleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOriginArticleContentId() {
        return originArticleContentId;
    }

    public void setOriginArticleContentId(Long originArticleContentId) {
        this.originArticleContentId = originArticleContentId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public ArticleVersion getArticleVersion() {
        return articleVersion;
    }

    public void setArticleVersion(ArticleVersion articleVersion) {
        this.articleVersion = articleVersion;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
}
