package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_article_state")
public class ArticleState extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleStateSeqGen", sequenceName = "articleEditSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleEditSeqGen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "sender")
    private String sender;

    @Column(name = "sender_state", columnDefinition = "VARCHAR(255) default 'PENDING'")
    private String senderState; // PREDRAFT, DRAFT, PENDING, PUBLISHED, REJECTED

    @Column(name = "receiver_state")
    private String receiverState;

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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderState() {
        return senderState;
    }

    public void setSenderState(String senderState) {
        this.senderState = senderState;
    }

    public String getReceiverState() {
        return receiverState;
    }

    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState;
    }
}
