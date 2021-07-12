package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ContentTemplateDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("desc")
    private String desc;
    @JsonProperty("sort")
    private Long order;
    @JsonProperty("level")
    private Long level;
    @JsonProperty("params")
    private List<String> params;
    @JsonProperty("children")
    private List<ContentTemplateDto> childs;
    @JsonProperty("parent")
    private Long parent=0L;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public List<ContentTemplateDto> getChilds() {
        return childs;
    }

    public void setChilds(List<ContentTemplateDto> childs) {
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

    @Override
    public String toString() {
        return "ContentTemplateDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", order=" + order +
                ", level=" + level +
                ", params=" + params +
                ", childs=" + childs +
                ", parent=" + parent +
                '}';
    }
}
