package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_article_template_content")
public class ArticleTemplateContent extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleTemplateContentSeqGen", sequenceName = "articleTemplateContentSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleTemplateContentSeqGen")
    private Long id;
}
