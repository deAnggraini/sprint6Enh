package id.co.bca.pakar.be.wf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ArticleContentDto extends BaseDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("intro")
    private String intro;
    @NotNull(message = "sort value is required")
    @Min(value = 1, message = "sort minimum value is 1")
    @JsonProperty("sort")
    private Long order;
    @NotNull(message = "level is required")
    @Min(value = 1, message = "minimum value is 1")
    @Max(value = 5, message = "maximum value is 5")
    @JsonProperty("level")
    private Long level;
    @JsonProperty("topicTitle")
    private String topicTitle;
    @JsonProperty("topicContent")
    private String topicContent;
    @JsonProperty("children")
    private List<ArticleContentDto> childs;
    @NotNull(message = "minimum parent value is required")
    @Min(value = 0, message = "minimum value is 0")
    @JsonProperty("parent")
    private Long parent = 0L;
    @NotNull(message = "minimum article value is required")
    @Min(value = 0, message = "minimum article value is 0")
    @JsonProperty("articleId")
    private Long articleId;
    @JsonProperty("listParent")
    private List<BreadcumbArticleContentDto> breadcumbArticleContentDtos = new ArrayList<>();
    @JsonProperty("expanded")
    private Boolean expanded = Boolean.FALSE;
    @JsonProperty("isEdit")
    private Boolean isEdit = Boolean.FALSE;
    @JsonProperty("no")
    private Long no = 0L;

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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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

    public List<BreadcumbArticleContentDto> getBreadcumbArticleContentDtos() {
        return breadcumbArticleContentDtos;
    }

    public void setBreadcumbArticleContentDtos(List<BreadcumbArticleContentDto> breadcumbArticleContentDtos) {
        this.breadcumbArticleContentDtos = breadcumbArticleContentDtos;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public Boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Boolean edit) {
        isEdit = edit;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "ArticleContentDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", intro='" + intro + '\'' +
                ", order=" + order +
                ", level=" + level +
                ", topicTitle='" + topicTitle + '\'' +
                ", topicContent='" + topicContent + '\'' +
                ", childs=" + childs +
                ", parent=" + parent +
                ", articleId=" + articleId +
                ", breadcumbArticleContentDtos=" + breadcumbArticleContentDtos +
                ", expanded=" + expanded +
                ", isEdit=" + isEdit +
                ", no=" + no +
                '}';
    }
}
