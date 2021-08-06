package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_article_edit")
public class ArticleEdit {
    @Id
    @SequenceGenerator(name = "articleEditSeqGen", sequenceName = "articleEditSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleEditSeqGen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "current_edit")
    private String current_edit;

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

    public String getCurrent_edit() {
        return current_edit;
    }

    public void setCurrent_edit(String current_edit) {
        this.current_edit = current_edit;
    }
}
