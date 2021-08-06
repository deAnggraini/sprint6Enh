package id.co.bca.pakar.be.wf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuggestionArticleDto extends ArticleTitleDto {
    @JsonProperty("id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
