package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_article_content")
public class ArticleContent extends CommonArticleContent {
    @Id
    @SequenceGenerator(name = "articleContentSeqGen", sequenceName = "articleContentSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleContentSeqGen")
    private Long id;
    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ArticleContent> children = new ArrayList<>();

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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    //returns direct children
    public List<ArticleContent> getChildren(){
        return children;
    }

    public void setChildren(List<ArticleContent> children) {
        this.children = children;
    }

    //returns all children to any level
    @Transient
    public List<ArticleContent> getAllChildren(){
        return getAllChildren(this);
    }

    //recursive function to walk the tree
    @Transient
    private List<ArticleContent> getAllChildren(ArticleContent parent){
        List<ArticleContent> allChildren = new ArrayList<>();
        for(ArticleContent child : children){
            allChildren.add(child);
            allChildren.addAll(getAllChildren(child));
        }
        return allChildren;
    }

    //returns all children to any level
    @Transient
    public List<ArticleContent> getDeletedAllChildren(String username){
        return getDeletedAllChildren(this, username);
    }

    //recursive function to walk the tree
    @Transient
    private List<ArticleContent> getDeletedAllChildren(ArticleContent parent, String username){
        List<ArticleContent> allChildren = new ArrayList<>();
        for(ArticleContent child : children){
            child.setDeleted(Boolean.TRUE);
            child.setModifyBy(username);
            child.setModifyDate(new Date());
            allChildren.add(child);
            allChildren.addAll(getDeletedAllChildren(child, username));
        }
        return allChildren;
    }
}
