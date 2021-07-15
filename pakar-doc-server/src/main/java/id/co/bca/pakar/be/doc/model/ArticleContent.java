package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_article_content")
public class ArticleContent extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleContentSeqGen", sequenceName = "articleSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleContentSeqGen")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "sort", columnDefinition = "integer DEFAULT 1")
    private Long sort;
    @Column(name = "level", columnDefinition = "integer DEFAULT 1")
    private Long level;
    @Column(name = "parent", columnDefinition = "integer DEFAULT 0")
    private Long parent;
    @Column(name = "topic_caption")
    private String topicCaption;
    @Column(name = "topic_content", columnDefinition = "TEXT", length = 50000)
    private String topicContent;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }
}
