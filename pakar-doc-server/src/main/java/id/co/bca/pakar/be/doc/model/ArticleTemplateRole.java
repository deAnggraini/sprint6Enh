package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_article_template_role")
public class ArticleTemplateRole extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleTemplateRoleSeqGen", sequenceName = "articleTemplateRoleSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleTemplateRoleSeqGen")
    private Long id;

    @Column(name = "role_id")
    private String roleId;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private ArticleTemplate articleTemplate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public ArticleTemplate getArticleTemplate() {
        return articleTemplate;
    }

    public void setArticleTemplate(ArticleTemplate articleTemplate) {
        this.articleTemplate = articleTemplate;
    }
}
