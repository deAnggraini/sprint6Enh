package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_article_notification")
public class ArticleNotification extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleNotificationSeqGen", sequenceName = "articleNotificationSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleNotificationSeqGen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Column(name = "sender")
    private String sender;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "status")
    private String status; // Informasi, Konflik, Terima, Edit

    @Column(name = "title")
    private String title;

    @Column(name = "is_read")
    private boolean isRead = false;

    @Column(name = "documentType")
    private String documentType; // Artikel, Virtual Pages, Formulir

    @Column(name = "notif_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date notifDate;

    @Column(name = "send_note")
    private String sendNote;

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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Date getNotifDate() {
        return notifDate;
    }

    public void setNotifDate(Date notifDate) {
        this.notifDate = notifDate;
    }

    public String getSendNote() {
        return sendNote;
    }

    public void setSendNote(String sendNote) {
        this.sendNote = sendNote;
    }

    @Override
    public String toString() {
        return "ArticleNotification{" +
                "id=" + id +
                ", article=" + article.getJudulArticle() +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", isRead=" + isRead +
                ", documentType='" + documentType + '\'' +
                ", notifDate=" + notifDate +
                '}';
    }
}
