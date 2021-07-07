package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_article_sk_reff")
public class ArticleSkReff extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleSkReffSeqGen", sequenceName = "articleSkReffSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleSkReffSeqGen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "sk_reff_id")
    private SkRefference skRefference;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public SkRefference getSkRefference() {
        return skRefference;
    }

    public void setSkRefference(SkRefference skRefference) {
        this.skRefference = skRefference;
    }
}
