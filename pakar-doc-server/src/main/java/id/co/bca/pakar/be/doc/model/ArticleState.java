package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_article_state")
public class ArticleState extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleStateSeqGen", sequenceName = "articleStateSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleStateSeqGen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "wf_req_id")
    private String wfReqId;

    @Column(name = "sender")
    private String sender;

    @Column(name = "fn_sender")
    private String fnSender;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "fn_receiver")
    private String fnReceiver;

    @Column(name = "sender_state", columnDefinition = "VARCHAR(255) default 'PENDING'")
    private String senderState; // DRAFT, PENDING, APPROVE

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

    public String getWfReqId() {
        return wfReqId;
    }

    public void setWfReqId(String wfReqId) {
        this.wfReqId = wfReqId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getFnSender() {
        return fnSender;
    }

    public void setFnSender(String fnSender) {
        this.fnSender = fnSender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getFnReceiver() {
        return fnReceiver;
    }

    public void setFnReceiver(String fnReceiver) {
        this.fnReceiver = fnReceiver;
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
