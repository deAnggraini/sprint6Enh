package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleDto extends BaseArticleDto {
    @JsonProperty("desc")
    private String shortDescription;

    @JsonProperty("image")
    private String image;

    @JsonProperty("video")
    private String videoLink;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_date")
    private Date createdDate = new Date();

    @JsonProperty("modified_by")
    private String modifiedBy;

    @JsonProperty("modified_name")
    private String modifiedName;

    @JsonProperty("modified_date")
    private Date modifiedDate = new Date();

    @JsonProperty("contents")
    private List<ArticleContentDto> contents = new ArrayList<>();

    @JsonProperty("references")
    private List<SkReffDto> skReff = new ArrayList<>();

    @JsonProperty("related")
    private List<RelatedArticleDto> related = new ArrayList<>();

    @JsonProperty("suggestions")
    private List<SuggestionArticleDto> suggestions = new ArrayList<>();

    @JsonProperty("isEmptyTemplate")
    private Boolean emptyTemplate = false;

    @JsonProperty("structureId")
    private Long structureId;

    @JsonProperty("structureParentList")
    private List<BreadcumbStructureDto> structureParentList = new ArrayList<>();

    /**
     * jika artikel sudah send maka isNew = true
     */
    @JsonProperty("isNew")
    private Boolean isNew = Boolean.TRUE;

    @JsonProperty("releaseVersion")
    private String releaseVersion;

    @JsonProperty("timestampVersion")
    private String timestampVersion;

    @JsonProperty("isPublished")
    private Boolean isPublished = Boolean.FALSE;

    /**
     * jika artikel belum pernah save atau kirim, maka isAdd = true
     */
    @JsonProperty("isAdd")
    private Boolean isAdd = Boolean.TRUE;

    @JsonProperty("isClone")
    private Boolean isClone = Boolean.FALSE;

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<ArticleContentDto> getContents() {
        return contents;
    }

    public void setContents(List<ArticleContentDto> contents) {
        this.contents = contents;
    }

    public List<SkReffDto> getSkReff() {
        return skReff;
    }

    public void setSkReff(List<SkReffDto> skReff) {
        this.skReff = skReff;
    }

    public List<RelatedArticleDto> getRelated() {
        return related;
    }

    public void setRelated(List<RelatedArticleDto> related) {
        this.related = related;
    }

    public List<SuggestionArticleDto> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<SuggestionArticleDto> suggestions) {
        this.suggestions = suggestions;
    }

    public Boolean getEmptyTemplate() {
        return emptyTemplate;
    }

    public void setEmptyTemplate(Boolean emptyTemplate) {
        this.emptyTemplate = emptyTemplate;
    }

    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }

    public List<BreadcumbStructureDto> getStructureParentList() {
        return structureParentList;
    }

    public void setStructureParentList(List<BreadcumbStructureDto> structureParentList) {
        this.structureParentList = structureParentList;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public String getReleaseVersion() {
        return releaseVersion;
    }

    public void setReleaseVersion(String releaseVersion) {
        this.releaseVersion = releaseVersion;
    }

    public String getTimestampVersion() {
        return timestampVersion;
    }

    public void setTimestampVersion(String timestampVersion) {
        this.timestampVersion = timestampVersion;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedName() {
        return modifiedName;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
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
}
