package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_refference_article")
public class RefferenceArticle extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleReffSeqGen", sequenceName = "articleReffSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleReffSeqGen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_article")
    private Article sourceArticle;

    @ManyToOne
    @JoinColumn(name = "reff_article")
    private Article reffArticle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Article getSourceArticle() {
        return sourceArticle;
    }

    public void setSourceArticle(Article sourceArticle) {
        this.sourceArticle = sourceArticle;
    }

    public Article getReffArticle() {
        return reffArticle;
    }

    public void setReffArticle(Article reffArticle) {
        this.reffArticle = reffArticle;
    }
}
