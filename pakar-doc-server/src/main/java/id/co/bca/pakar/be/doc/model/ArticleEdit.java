package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_article_edit")
public class ArticleEdit extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleEditSeqGen", sequenceName = "articleEditSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleEditSeqGen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "username")
    private String username;

    @Column(name = "editor_name")
    private String editorName;

    @Column(name = "status")
    private Boolean status = Boolean.FALSE; // sedang diedit = true, selesai diedit = false

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
