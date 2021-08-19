package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_article_version")
public class ArticleVersion extends EntityBase {
    @Id
	@SequenceGenerator(name = "articleVersionSeqGen", sequenceName = "articleVersionSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "articleVersionSeqGen")
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "structure_id", nullable = false)
    private Long structure;

    @Column(name = "article_template_id")
    private Long articleTemplate;

    @Column(name = "title", unique = true, nullable = false)
    private String judulArticle;

    @Column(name = "article_used_by")
    private String articleUsedBy;

    @Lob
    @Column(name = "short_desc", columnDefinition = "TEXT")
    private String shortDescription = new String();

    @Column(name = "video_link")
    private String videoLink;

    @Column(name = "state", columnDefinition = "VARCHAR(255) default 'NEW'")
    private String articleState; // NEW, DRAFT, PENDING, PUBLISHED, REJECTED

    @OneToMany(mappedBy = "articleVersion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ArticleContentVersion> articleContents = new ArrayList<>();

    @Column(name = "use_empty_template", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean useEmptyTemplate = Boolean.FALSE;

    @Column(name = "new_article", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean newArticle = Boolean.TRUE;

    @Column(name = "fn_modifier")
    private String fullNameModifier;

    @Column(name = "release_version")
    private String timeStampVersion;

    @Column(name = "timestamp_version")
    private String releaseVersion;

    @Column(name = "username")
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getTimeStampVersion() {
        return timeStampVersion;
    }

    public void setTimeStampVersion(String timeStampVersion) {
        this.timeStampVersion = timeStampVersion;
    }

    public String getReleaseVersion() {
        return releaseVersion;
    }

    public void setReleaseVersion(String releaseVersion) {
        this.releaseVersion = releaseVersion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
