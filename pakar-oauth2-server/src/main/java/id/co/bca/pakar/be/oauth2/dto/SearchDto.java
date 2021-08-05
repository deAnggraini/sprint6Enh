package id.co.bca.pakar.be.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchDto {
    @JsonProperty("keyword")
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
