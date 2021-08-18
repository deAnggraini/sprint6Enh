package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "v_my_pages")
public class MyPages extends EntityBase {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "sender")
    private String sender;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "fn_receiver")
    private String fullNameReceiver;

    @Column(name = "fn_sender")
    private String fullNameSender;

    @Column(name = "sender_state", columnDefinition = "VARCHAR(255) default 'PENDING'")
    private String senderState; // DRAFT, PENDING, APPROVE

    @Column(name = "receiver_state")
    private String receiverState;

    @Column(name = "optlock")
    private Long version;

    @Column(name = "structure_id", nullable = false)
    private Long structure;

    @Column(name ="article_template_id")
    private Long articleTemplate;

    @Column(name = "title", unique = true, nullable = false)
    private String judulArticle;

    @Column(name = "article_used_by")
    private String articleUsedBy;

    @Column(name = "short_desc", columnDefinition="TEXT", length = 1000, nullable = false)
    private String shortDescription = new String();

    @Column(name = "video_link")
    private String videoLink;

    @Column(name = "state", columnDefinition = "VARCHAR(255) default 'PREDRAFT'")
    private String articleState; // NEW, DRAFT, PENDING, PUBLISHED, REJECTED

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ArticleContent> articleContents = new ArrayList<>();

    @Column(name = "use_empty_template", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean useEmptyTemplate = Boolean.FALSE;

    @Column(name = "new_article", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean newArticle = Boolean.TRUE;

    @Column(name = "fn_modifier")
    private String fullNameModifier;

    @Column(name = "location")
    private String location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getStructure() {
        return structure;
    }

    public void setStructure(Long structure) {
        this.structure = structure;
    }

    public Long getArticleTemplate() {
        return articleTemplate;
    }

    public void setArticleTemplate(Long articleTemplate) {
        this.articleTemplate = articleTemplate;
    }

    public String getJudulArticle() {
        return judulArticle;
    }

    public void setJudulArticle(String judulArticle) {
        this.judulArticle = judulArticle;
    }

    public String getArticleUsedBy() {
        return articleUsedBy;
    }

    public void setArticleUsedBy(String articleUsedBy) {
        this.articleUsedBy = articleUsedBy;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getArticleState() {
        return articleState;
    }

    public void setArticleState(String articleState) {
        this.articleState = articleState;
    }

    public List<ArticleContent> getArticleContents() {
        return articleContents;
    }

    public void setArticleContents(List<ArticleContent> articleContents) {
        this.articleContents = articleContents;
    }

    public Boolean getUseEmptyTemplate() {
        return useEmptyTemplate;
    }

    public void setUseEmptyTemplate(Boolean useEmptyTemplate) {
        this.useEmptyTemplate = useEmptyTemplate;
    }

    public Boolean getNewArticle() {
        return newArticle;
    }

    public void setNewArticle(Boolean newArticle) {
        this.newArticle = newArticle;
    }

    public String getFullNameModifier() {
        return fullNameModifier;
    }

    public void setFullNameModifier(String fullNameModifier) {
        this.fullNameModifier = fullNameModifier;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFullNameReceiver() {
        return fullNameReceiver;
    }

    public void setFullNameReceiver(String fullNameReceiver) {
        this.fullNameReceiver = fullNameReceiver;
    }

    public String getFullNameSender() {
        return fullNameSender;
    }

    public void setFullNameSender(String fullNameSender) {
        this.fullNameSender = fullNameSender;
    }
}
