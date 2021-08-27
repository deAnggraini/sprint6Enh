package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_article_content_version")
public class ArticleContentVersion extends CommonArticleContent {
    @Id
    @SequenceGenerator(name = "articleContentVersionSeqGen", sequenceName = "articleContentVersionSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleContentVersionSeqGen")
    @Column(name = "id")
    private Long id = 0L;

    @Column(name = "origin_article_content_id")
    private Long originArticleContentId;

//    @Version
//    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0")
    private Long version;

    @ManyToOne
    @JoinColumn(name = "article_version_id")
    private ArticleVersion articleVersion;

    @Column(name = "origin_article_id")
    private Long articleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
