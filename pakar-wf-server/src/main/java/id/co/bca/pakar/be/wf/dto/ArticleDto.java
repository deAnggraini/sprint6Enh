package id.co.bca.pakar.be.wf.dto;

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

    @JsonProperty("contents")
    private List<ArticleContentDto> contents = new ArrayList<>();

    @JsonProperty("references")
    private List<SkReffDto> skReff = new ArrayList<>();

    @JsonProperty("related")
    private List<RelatedArticleDto> related = new ArrayList<>();

    @JsonProperty("suggestions")
    private List<SuggestionArticleDto> suggestions = new ArrayList<>();

    @JsonProperty("isHasSend")
    private Boolean isHasSend;

    @JsonProperty("sendTo")
    private AssignDto sendTo;

    @JsonProperty("sendNote")
    private String sendNote;

    @JsonProperty("taskType")
    private String taskType; // APPROVE, DENY, CANCEL

    @JsonProperty("isEmptyTemplate")
    private Boolean emptyTemplate = false;

    @JsonProperty("structureId")
    private Long structureId;

    @JsonProperty("structureParentList")
    private List<BreadcumbStructureDto> structureParentList = new ArrayList<>();

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

    public Boolean getIsHasSend() {
        return isHasSend;
    }

    public void setIsHasSend(Boolean hasSend) {
        isHasSend = hasSend;
    }

    public AssignDto getSendTo() {
        return sendTo;
    }

    public void setSendTo(AssignDto sendTo) {
        this.sendTo = sendTo;
    }

    public String getSendNote() {
        return sendNote;
    }

    public void setSendNote(String sendNote) {
        this.sendNote = sendNote;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
