package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
//@Table(name = "t_article_in_review")
public class ArticleInReview extends EntityBase {
    @Id
    @Column(name = "id")
    private Long id;
    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;

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
    private String articleState;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ArticleContentInReview> articleContents = new ArrayList<>();

    @Column(name = "use_empty_template", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean useEmptyTemplate = Boolean.FALSE;

    @Column(name = "new_article", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean newArticle = Boolean.TRUE;

    @Column(name = "fn_modifier")
    private String fullNameModifier;

    @Column(name = "origin_article_id")
    private Long originArticleId;

    @Column(name = "username")
    private String username;

    @Column(name = "editor_name")
    private String editorName;

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

    public List<ArticleContentInReview> getArticleContents() {
        return articleContents;
    }

    public void setArticleContents(List<ArticleContentInReview> articleContents) {
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

    public Long getOriginArticleId() {
        return originArticleId;
    }

    public void setOriginArticleId(Long originArticleId) {
        this.originArticleId = originArticleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }
}
