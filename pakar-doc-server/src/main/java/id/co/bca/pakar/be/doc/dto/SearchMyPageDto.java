package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchMyPageDto extends PageDto {
    @JsonProperty("keyword")
    private String keyword;
    @JsonProperty("sorting")
    private SortingPageDto sorting;
    @JsonProperty("state")
    private String state;
    @JsonProperty("type")
    private String type;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public SortingPageDto getSorting() {
        return sorting;
    }

    public void setSorting(SortingPageDto sorting) {
        this.sorting = sorting;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
