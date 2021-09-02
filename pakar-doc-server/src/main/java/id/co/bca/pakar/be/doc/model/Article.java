package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_article")
public class Article extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleSeqGen", sequenceName = "articleSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleSeqGen")
    @Column(name = "id")
    private Long id;

    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0")
    private Long version;

    @ManyToOne
    @JoinColumn(name = "structure_id", nullable = false)
    private Structure structure;

    @Column(name = "article_template_id")
    private Long articleTemplate;

    @Column(name = "title", unique = true, nullable = false)
    private String judulArticle;

    @Column(name = "article_used_by")
    private String articleUsedBy;

	@Column(name = "short_desc", columnDefinition= "TEXT", length = Integer.MAX_VALUE, nullable = false)
    private String shortDescription = new String();

    @Column(name = "video_link")
    private String videoLink;

    @Column(name = "state")
    private String articleState; // NEW, DRAFT, PENDING, PUBLISHED, REJECTED

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ArticleContent> articleContents = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ArticleContentClone> articleContentClones = new ArrayList<>();

    @Column(name = "use_empty_template")
    private Boolean useEmptyTemplate = Boolean.FALSE;

    @Column(name = "new_article")
    private Boolean newArticle = Boolean.TRUE;

    @Column(name = "fn_modifier")
    private String fullNameModifier;

    @Column(name = "is_published")
    private Boolean isPublished = Boolean.FALSE;

    @Column(name = "is_add")
    private Boolean isAdd = Boolean.TRUE;

    @Column(name = "is_clone")
    private Boolean isClone = Boolean.TRUE;

    @Column(name = "effective_date")
    private Date effectiveDate;

    @Column(name = "approved_date")
    private Date approvedDate;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "fn_approver")
    private String fullNameApprover;

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

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
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

    public List<ArticleContent> getArticleContents() {
        return articleContents;
    }

    public void setArticleContents(List<ArticleContent> articleContents) {
        this.articleContents = articleContents;
    }

    public List<ArticleContentClone> getArticleContentClones() {
        return articleContentClones;
    }

    public void setArticleContentClones(List<ArticleContentClone> articleContentClones) {
        this.articleContentClones = articleContentClones;
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

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getFullNameApprover() {
        return fullNameApprover;
    }

    public void setFullNameApprover(String fullNameApprover) {
        this.fullNameApprover = fullNameApprover;
    }
}
