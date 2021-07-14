package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_article_image")
public class ArticleImage extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleImageSeqGen", sequenceName = "articleImageSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleImageSeqGen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne
    @JoinColumn(name = "image_id", nullable = false)
    private Images image;

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

    public Images getImage() {
        return image;
    }

    public void setImage(Images image) {
        this.image = image;
    }
}
