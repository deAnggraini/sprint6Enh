package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_article_content")
public class ArticleContent extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleContentSeqGen", sequenceName = "articleSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleContentSeqGen")
    private Long id;
    @Column(name = "topic_caption")
    private String topicCaption;
    @Column(name = "topic_content", columnDefinition = "TEXT", length =50000)
    private String topicContent;

    @ManyToOne
    @JoinColumn(name = "article")
    private Article article;
    @ManyToOne
    @JoinColumn(name = "content_structure")
    private ArticleContentStructure contentStructure;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopicCaption() {
        return topicCaption;
    }

    public void setTopicCaption(String topicCaption) {
        this.topicCaption = topicCaption;
    }

    public String getTopicContent() {
        return topicContent;
    }

    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public ArticleContentStructure getContentStructure() {
        return contentStructure;
    }

    public void setContentStructure(ArticleContentStructure contentStructure) {
        this.contentStructure = contentStructure;
    }
}
