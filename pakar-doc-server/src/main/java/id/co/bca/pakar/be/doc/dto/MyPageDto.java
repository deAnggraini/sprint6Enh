package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class MyPageDto extends BaseDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("type")
    private String type = "article";
    @JsonProperty("title")
    private String title;
    @JsonProperty("location")
    private String location;
    @JsonProperty("isNew")
    private Boolean isNew;
    @JsonProperty("created_by")
    private String createdBy;
    @JsonProperty("created_date")
    private Date createdDate;
    @JsonProperty("modified_by")
    private String modifiedBy;
    @JsonProperty("modified_date")
    private Date modifiedDate;
    @JsonProperty("approved_by")
    private String approved_by;
    @JsonProperty("approved_date")
    private Date approvedDate;
    @JsonProperty("state")
    private String state;
    @JsonProperty("effective_date")
    private Date effectiveDate;
    @JsonProperty("send_to")
    private String sendTo;
    @JsonProperty("current_by")
    private String currentBy;
    @JsonProperty("receiver")
    private String receiver;
    @JsonProperty("isClone")
    private Boolean isClone;
    @JsonProperty("isPublished")
    private Boolean isPublished;
    @JsonProperty("isAdd")
    private Boolean isAdd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean aNew) {
        isNew = aNew;
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

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getApproved_by() {
        return approved_by;
    }

    public void setApproved_by(String approved_by) {
        this.approved_by = approved_by;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getCurrentBy() {
        return currentBy;
    }

    public void setCurrentBy(String currentBy) {
        this.currentBy = currentBy;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Boolean getIsClone() {
        return isClone;
    }

    public void setIsClone(Boolean clone) {
        isClone = clone;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean published) {
        isPublished = published;
    }

    public Boolean getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(Boolean add) {
        isAdd = add;
    }
}
