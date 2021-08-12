package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchNotificationDto extends PageDto {
    @JsonProperty("keyword")
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
