package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.co.bca.pakar.be.doc.model.SkRefference;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class ArticleDto extends BaseArticleDto {
    @JsonProperty("desc")
    private String shortDescription;

    @JsonProperty("image")
    private MultipartFile image;

    @JsonProperty("video")
    private String videoLink;

    @JsonProperty("contents")
    private List<ArticleContentDto> contents = new ArrayList<>();

    @JsonProperty("references")
    private List<SkRefference> refferences = new ArrayList<>();

    @JsonProperty("related")
    private List<RelatedArticleDto> related = new ArrayList<>();

    @JsonProperty("suggestions")
    private List<SuggestionArticleDto> suggestions = new ArrayList<>();

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

    public List<SkRefference> getRefferences() {
        return refferences;
    }

    public void setRefferences(List<SkRefference> refferences) {
        this.refferences = refferences;
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
}
