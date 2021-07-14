package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;


public class StructureDto {
    @JsonProperty("id")
    protected Long id;
    @NotEmpty(message = "name is required")
    @Size(max = 50, message = "maximum length 50 characters")
    @JsonProperty("name")
    protected String name;
    @NotEmpty(message = "desc is required")
    @Size(max = 200, message = "maximum length 200 characters")
    @JsonProperty("desc")
    protected String desc;
    @NotNull(message = "sort is required")
    @Min(value = 1, message = "minimum value is 1")
    @JsonProperty("sort")
    protected Long sort=1L;
    @NotNull(message = "level is required")
    @Min(value = 1, message = "minimum value is 1")
    @Max(value=4, message = "maximum value is 4")
    @JsonProperty("level")
    protected Long level=1L;
    @NotNull(message = "parent param must exist in request")
    @Min(value = 0, message = "minimum value is 0")
    @JsonProperty("parent")
    protected Long parent = 0L;
    @JsonProperty("uri")
    protected String uri;
    @JsonProperty("edit")
    protected Boolean edit;
    @JsonProperty("location")
    protected String location;
    @JsonProperty("location_text")
    protected String location_text;
    @JsonProperty("hasArticle")
    private Boolean hasArticle = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation_text() {
        return location_text;
    }

    public void setLocation_text(String location_text) {
        this.location_text = location_text;
    }

    public Boolean getHasArticle() {
        return hasArticle;
    }

    public void setHasArticle(Boolean hasArticle) {
        this.hasArticle = hasArticle;
    }

    @Override
    public String toString() {
        return "StructureDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", sort=" + sort +
                ", level=" + level +
                ", parent=" + parent +
                ", uri='" + uri + '\'' +
                ", edit=" + edit +
                ", location='" + location + '\'' +
                ", location_text='" + location_text + '\'' +
                ", hasArticle=" + hasArticle +
                '}';
    }
}
