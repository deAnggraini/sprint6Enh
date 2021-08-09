package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class MultipartArticleDto extends BaseArticleDto {
    @JsonProperty("desc")
    private String shortDescription;

    @JsonProperty("image")
    private MultipartFile image;

    @JsonProperty("video")
    private String videoLink;

    @JsonProperty("contents")
    private List<ArticleContentDto> contents = new ArrayList<>();

    @JsonProperty("references")
    private List<SkReffDto> skReff = new ArrayList<>();

    @JsonProperty("related")
    private List<MultipartArticleDto> related = new ArrayList<>();

    @JsonProperty("suggestions")
    private List<SuggestionArticleDto> suggestions = new ArrayList<>();

    @JsonProperty("isHasSend")
    private Boolean isHasSend;

    @JsonProperty("sendTo")
    private ArticleAssignerDto articleAssignerDto;

    @JsonProperty("sendNote")
    private String sendNote;

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
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

    public Boolean getHasSend() {
        return isHasSend;
    }

    public void setHasSend(Boolean hasSend) {
        isHasSend = hasSend;
    }

    public ArticleAssignerDto getArticleAssignerDto() {
        return articleAssignerDto;
    }

    public void setArticleAssignerDto(ArticleAssignerDto articleAssignerDto) {
        this.articleAssignerDto = articleAssignerDto;
    }

    public String getSendNote() {
        return sendNote;
    }

    public void setSendNote(String sendNote) {
        this.sendNote = sendNote;
    }
}
