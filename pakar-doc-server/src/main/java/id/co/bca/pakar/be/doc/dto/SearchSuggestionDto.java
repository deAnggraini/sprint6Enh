package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchSuggestionDto extends PageDto {
    @JsonProperty("keyword")
    private String keyword;
    @JsonProperty("exclude")
    private List<Long> exclude;
    @JsonProperty("structureId")
    private Long structureId;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Long> getExclude() {
        return exclude;
    }

    public void setExclude(List<Long> exclude) {
        this.exclude = exclude;
    }

    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }
}
