package id.co.bca.pakar.be.doc.model;


import javax.persistence.*;

@Entity
@Table(name = "t_article_faq")
public class FAQ extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleFaqSeqGen", sequenceName = "articleFaqSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleFaqSeqGen")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "question")
    private String question;
    @Column(name = "answer")
    private String answer;

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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
