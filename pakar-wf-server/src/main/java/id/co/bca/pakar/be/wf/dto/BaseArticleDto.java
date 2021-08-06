package id.co.bca.pakar.be.wf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseArticleDto extends ArticleTitleDto {
    @JsonProperty("id")
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
