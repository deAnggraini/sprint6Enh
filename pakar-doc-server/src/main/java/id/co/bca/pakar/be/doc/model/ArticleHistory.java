package id.co.bca.pakar.be.doc.model;

import id.co.bca.pakar.be.doc.util.ShaUtils;

import javax.persistence.*;
import java.security.SecureRandom;

import static java.nio.charset.StandardCharsets.UTF_8;

@Entity
@Table(name = "t_article_history")
//@TypeDefs({@TypeDef(name = "MyJsonType", typeClass = MyJsonType.class)})
public class ArticleHistory extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleHistorySeqGen", sequenceName = "articleHistorySeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleHistorySeqGen")
    private Long id;
    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;

    @Column(name = "revision")
    private String revision = generateRevision();

    @Column(name = "structure_id", nullable = false)
    private Long structure;

    @Column(name = "article_template_id")
    private Long articleTemplate;

    @Column(name = "title", unique = true, nullable = false)
    private String judulArticle;

    @Column(name = "article_used_by")
    private String articleUsedBy;

    @Column(name = "short_desc", columnDefinition = "TEXT", length = 1000, nullable = false)
    private String shortDescription = new String();

    @Column(name = "video_link")
    private String videoLink;

    @Column(name = "state", columnDefinition = "VARCHAR(255) default 'PREDRAFT'")
    private String articleState; // PREDRAFT, DRAFT, PENDING, PUBLISHED, REJECTED

    @Column(name = "use_empty_template", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean useEmptyTemplate = Boolean.FALSE;

    @Column(name = "article")
    private Long article;

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

    public Boolean getUseEmptyTemplate() {
        return useEmptyTemplate;
    }

    public void setUseEmptyTemplate(Boolean useEmptyTemplate) {
        this.useEmptyTemplate = useEmptyTemplate;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    /**
     * generate revision value
     *
     * @return
     */
    public static String generateRevision() {
        SecureRandom rand = new SecureRandom();

        // Generate random integers in range 0 to 999
        int randomValue = rand.nextInt(1000);
        long milis = System.currentTimeMillis() + randomValue;
        byte[] shaInBytes = ShaUtils.digest(new StringBuffer().append(milis).toString().getBytes(UTF_8));
        return ShaUtils.bytesToHex(shaInBytes);
    }
}
