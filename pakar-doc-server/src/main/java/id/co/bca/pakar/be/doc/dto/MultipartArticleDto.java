package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class MultipartArticleDto extends BaseArticleDto {
    @JsonProperty("desc")
    private String desc;

    @JsonProperty("image")
    private MultipartFile image;

    @JsonProperty("video")
    private String video;

    @JsonProperty("contents")
    private List<ArticleContentDto> contents = new ArrayList<>();

    @JsonProperty("references")
    private List<SkReffDto> references = new ArrayList<>();

    @JsonProperty("related")
    private List<MultipartArticleDto> related = new ArrayList<>();

    @JsonProperty("suggestions")
    private List<SuggestionArticleDto> suggestions = new ArrayList<>();

    @JsonProperty("isHasSend")
    private Boolean isHasSend;

    @JsonProperty("sendTo")
    private ArticleAssignerDto sendTo;

    @JsonProperty("sendNote")
    private String sendNote;

    @JsonProperty("taskType")
    private String taskType; // APPROVE, DENY, CANCEL

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public List<ArticleContentDto> getContents() {
        return contents;
    }

    public void setContents(List<ArticleContentDto> contents) {
        this.contents = contents;
    }

    public List<SkReffDto> getReferences() {
        return references;
    }

    public void setReferences(List<SkReffDto> references) {
        this.references = references;
    }

    public List<MultipartArticleDto> getRelated() {
        return related;
    }

    public void setRelated(List<MultipartArticleDto> related) {
        this.related = related;
    }

    public List<SuggestionArticleDto> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<SuggestionArticleDto> suggestions) {
        this.suggestions = suggestions;
    }

    public Boolean getIsHasSend() {
        return isHasSend;
    }

    public void setIsHasSend(Boolean hasSend) {
        isHasSend = hasSend;
    }

    public ArticleAssignerDto getSendTo() {
        return sendTo;
    }

    public void setSendTo(ArticleAssignerDto sendTo) {
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
