package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_article_template_image")
public class ArticleTemplateImage extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleTemplateImageSeqGen", sequenceName = "articleTemplateImageSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleTemplateImageSeqGen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_template_id")
    private ArticleTemplate articleTemplate;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Images images;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArticleTemplate getArticleTemplate() {
        return articleTemplate;
    }

    public void setArticleTemplate(ArticleTemplate articleTemplate) {
        this.articleTemplate = articleTemplate;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }
}
