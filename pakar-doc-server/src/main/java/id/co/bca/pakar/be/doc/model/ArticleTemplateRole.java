package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_article_template_role")
public class ArticleTemplateRole extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleTemplateRoleSeqGen", sequenceName = "articleTemplateRoleSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleTemplateRoleSeqGen")
    private Long id;
}
