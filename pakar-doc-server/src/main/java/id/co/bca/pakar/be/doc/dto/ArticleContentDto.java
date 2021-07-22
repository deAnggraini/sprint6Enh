package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ArticleContentDto extends BaseDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("intro")
    private String introduction;
    @JsonProperty("sort")
    private Long order;
    @JsonProperty("level")
    private Long level;
    @JsonProperty("topicTitle")
    private String topicTitle;
    @JsonProperty("topicContent")
    private String topicContent;
    @JsonProperty("children")
    private List<ArticleContentDto> childs;
    @JsonProperty("parent")
    private Long parent = 0L;
    @JsonProperty("articleId")
    private Long articleId;
    @JsonProperty("listParent")
    private List<ArticleContentDto> listParent = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getTopicContent() {
        return topicContent;
    }

    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }

    public List<ArticleContentDto> getChilds() {
        return childs;
    }

    public void setChilds(List<ArticleContentDto> childs) {
        this.childs = childs;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "ArticleContentDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", introduction='" + introduction + '\'' +
                ", order=" + order +
                ", level=" + level +
                ", topicTitle='" + topicTitle + '\'' +
                ", topicContent='" + topicContent + '\'' +
                ", childs=" + childs +
                ", parent=" + parent +
                ", articleId=" + articleId +
                '}';
    }
}
