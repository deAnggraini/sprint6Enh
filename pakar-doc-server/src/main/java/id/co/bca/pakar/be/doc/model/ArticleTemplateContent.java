package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

/**
 * structure of article template content
 * every article could have have parent, and parent could have e child
 */
@Entity
@Table(name = "t_article_template_content")
public class ArticleTemplateContent extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleTemplateContentSeqGen", sequenceName = "articleTemplateContentSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleTemplateContentSeqGen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_template_id")
    private ArticleTemplate articleTemplate;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArticleTemplate getArticleTemplate() {
        return articleTemplate;
    }

    public void setArticleTemplate(ArticleTemplate articleTemplate) {
        this.articleTemplate = articleTemplate;
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
