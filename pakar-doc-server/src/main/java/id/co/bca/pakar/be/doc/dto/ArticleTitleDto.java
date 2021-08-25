package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArticleTitleDto extends BaseDto {
    @JsonProperty("title")
    protected String title;
    @JsonProperty("exclude")
    private Long exlude;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getExlude() {
        return exlude;
    }

    public void setExlude(Long exlude) {
        this.exlude = exlude;
    }
}
