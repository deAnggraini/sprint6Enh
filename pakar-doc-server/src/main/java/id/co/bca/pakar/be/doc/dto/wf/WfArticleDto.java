package id.co.bca.pakar.be.doc.dto.wf;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import id.co.bca.pakar.be.doc.dto.ArticleAssignerDto;
import id.co.bca.pakar.be.doc.dto.ArticleContentDto;
import id.co.bca.pakar.be.doc.dto.SkReffDto;
import id.co.bca.pakar.be.doc.dto.SuggestionArticleDto;

import java.util.ArrayList;
import java.util.List;

public class WfArticleDto {
    @JsonProperty("id")
    protected Long id;

    @JsonProperty("title")
    protected String title;

    @JsonIgnore
    protected String token;

    @JsonIgnore
    protected String username;

    @JsonProperty("desc")
    private String desc;

//    @JsonProperty("image")
//    private MultipartFile image;

//    @JsonProperty("video")
//    private String video;

    @JsonProperty("contents")
    private List<ArticleContentDto> contents = new ArrayList<>();

    @JsonProperty("references")
    private List<SkReffDto> references = new ArrayList<>();

    @JsonProperty("related")
    private List<WfArticleDto> related = new ArrayList<>();

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public MultipartFile getImage() {
//        return image;
//    }
//
//    public void setImage(MultipartFile image) {
//        this.image = image;
//    }
//
//    public String getVideo() {
//        return video;
//    }
//
//    public void setVideo(String video) {
//        this.video = video;
//    }

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

    public List<WfArticleDto> getRelated() {
        return related;
    }

    public void setRelated(List<WfArticleDto> related) {
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
