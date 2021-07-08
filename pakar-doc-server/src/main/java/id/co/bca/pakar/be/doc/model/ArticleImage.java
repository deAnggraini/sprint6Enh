package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_article_image")
public class ArticleImage extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleImageSeqGen", sequenceName = "articleImageSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleImageSeqGen")
    private Long id;
}
