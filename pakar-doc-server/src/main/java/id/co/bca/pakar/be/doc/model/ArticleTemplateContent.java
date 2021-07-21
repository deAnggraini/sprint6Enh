package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

/**
 * structure of article template content
 * every article could have have parent, and parent could have e child
 */
@Entity
@Table(name = "t_article_template_content")
public class ArticleTemplateContent extends CommonArticleContent {
    @Id
    @SequenceGenerator(name = "articleTemplateContentSeqGen", sequenceName = "articleTemplateContentSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleTemplateContentSeqGen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_template_id")
    private ArticleTemplate articleTemplate;

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
}
