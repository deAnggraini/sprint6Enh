package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_suggestion_article")
public class SuggestionArticle extends EntityBase{
    @Id
    @SequenceGenerator(name = "suggestionArticleSeqGen", sequenceName = "suggestionArticleSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "suggestionArticleSeqGen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne
    @JoinColumn(name = "structure_id", nullable = false)
    private Structure structure;

    @Column(name = "hit_count")
    private Long hit_count;

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

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public Long getHit_count() {
        return hit_count;
    }

    public void setHit_count(Long hit_count) {
        this.hit_count = hit_count;
    }
}
