package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_article_content_history")
public class ArticleContentHistory extends CommonArticleContent {
    @Id
    @SequenceGenerator(name = "articleContentHistorySeqGen", sequenceName = "articleContentHistorySeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleContentHistorySeqGen")
    private Long id = 0L;
    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;

    @Column(name = "article_id")
    private Long article;

    @Column(name = "article_content_id")
    private Long articleContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setArticle(Long article) {
        this.article = article;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getArticle() {
        return article;
    }

    public Long getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(Long articleContent) {
        this.articleContent = articleContent;
    }
}
