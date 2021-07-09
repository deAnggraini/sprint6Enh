package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

/**
 * class model to define relation between structure (category with article template)
 * 1 template could have more than 1 structure and
 * 1 structure could have more than 1 template
 */
@Entity
@Table(name = "t_article_template_structure")
public class ArticleTemplateStructure extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleTemplateStructureSeqGen", sequenceName = "articleTemplateStructureSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleTemplateStructureSeqGen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_template_id")
    private ArticleTemplate articleTemplate;

    @ManyToOne
    @JoinColumn(name = "structure_id")
    private Structure structure;

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

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }
}
