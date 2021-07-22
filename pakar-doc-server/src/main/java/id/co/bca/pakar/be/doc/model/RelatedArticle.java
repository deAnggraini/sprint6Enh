package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_related_article")
public class RelatedArticle extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleRelatedSeqGen", sequenceName = "articleRelatedSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleRelatedSeqGen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_article")
    private Article sourceArticle;

    @ManyToOne
    @JoinColumn(name = "related_article")
    private Article relatedArticle;

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

    public Article getRelatedArticle() {
        return relatedArticle;
    }

    public void setRelatedArticle(Article relatedArticle) {
        this.relatedArticle = relatedArticle;
    }
}
