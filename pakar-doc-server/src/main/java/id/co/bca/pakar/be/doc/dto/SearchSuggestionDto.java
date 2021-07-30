package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchSuggestionDto extends PageDto {
    @JsonProperty("keyword")
    private String keyword;
    @JsonProperty("exclude")
    private Long exclude;
    @JsonProperty("structureId")
    private Long structureId;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getExclude() {
        return exclude;
    }

    public void setExclude(Long exclude) {
        this.exclude = exclude;
    }

    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }
}
