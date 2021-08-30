package id.co.bca.pakar.be.doc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_article_version")
public class ArticleVersion extends EntityBase {
    @Id
    @GenericGenerator(name = "UUID",  strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    @Column(name = "id")
    private String id;

    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0")
    private Long version;

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "structure_id", nullable = false)
    private Long structure;

    @Column(name = "article_template_id")
    private Long articleTemplate;

    @Column(name = "title", nullable = false)
    private String judulArticle;

    @Column(name = "article_used_by")
    private String articleUsedBy;

    @Column(name = "short_desc", columnDefinition = "TEXT", length = Integer.MAX_VALUE)
    private String shortDescription = new String();

    @Column(name = "video_link")
    private String videoLink;

    @Column(name = "state")
    private String articleState; // NEW, DRAFT, PENDING, PUBLISHED, REJECTED

    @OneToMany(mappedBy = "articleVersion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ArticleContentVersion> articleContents = new ArrayList<>();

    @Column(name = "use_empty_template")
    private Boolean useEmptyTemplate = Boolean.FALSE;

    @Column(name = "new_article")
    private Boolean newArticle = Boolean.TRUE;

    @Column(name = "fn_modifier")
    private String fullNameModifier;

    @Column(name = "published_version")
    private Long publishedVersion = 0L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp_version")
    private Date timeStampVersion;

    @Column(name = "username")
    private String username;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approved_date")
    private Date approvedDate;

    @Column(name = "isPublished")
    private Boolean isPublished;

    @Column(name = "isAdd")
    private Boolean isAdd = Boolean.TRUE;

    @Column(name = "is_clone")
    private Boolean isClone = Boolean.TRUE;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
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

	public List<ArticleContentVersion> getArticleContents() {
		return articleContents;
	}

	public void setArticleContents(List<ArticleContentVersion> articleContents) {
		this.articleContents = articleContents;
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

    public Date getTimeStampVersion() {
        return timeStampVersion;
    }

    public void setTimeStampVersion(Date timeStampVersion) {
        this.timeStampVersion = timeStampVersion;
    }

    public Long getPublishedVersion() {
        return publishedVersion;
    }

    public void setPublishedVersion(Long publishedVersion) {
        this.publishedVersion = publishedVersion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public Boolean getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(Boolean add) {
        isAdd = add;
    }

    public Boolean getIsClone() {
        return isClone;
    }

    public void setIsClone(Boolean clone) {
        isClone = clone;
    }
}
