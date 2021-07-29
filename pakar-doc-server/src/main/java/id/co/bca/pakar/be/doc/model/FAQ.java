package id.co.bca.pakar.be.doc.model;


import javax.persistence.*;

@Entity
@Table(name = "t_article_faq")
public class FAQ extends EntityBase {
    @Id
    @SequenceGenerator(name = "skReffSeqGen", sequenceName = "skReffSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "skReffSeqGen")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "question")
    private String question;
    @Column(name = "answer")
    private String answer;
}
