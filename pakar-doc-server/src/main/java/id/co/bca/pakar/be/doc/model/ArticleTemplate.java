package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_article_template")
public class ArticleTemplate extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleTemplateSeqGen", sequenceName = "articleTemplateSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleTemplateSeqGen")
    private Long id;

    @Column(name = "template_name")
    private String templateName;

    @ManyToOne
    @JoinColumn(name = "structure_id")
    private Structure structure;

    @Column(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
