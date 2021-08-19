package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchPublishedArticleDto extends PageDto {
    @JsonProperty("keyword")
    private String keyword;
    @JsonProperty("isLatest")
    private Boolean latest;
    @JsonProperty("structureId")
    private Long structureId;
    @JsonProperty("sorting")
    private SortingPageDto sorting;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Boolean getIsLatest() {
        return latest;
    }

    public void setLatest(Boolean latest) {
        this.latest = latest;
    }

    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }

    public SortingPageDto getSorting() {
        return sorting;
    }

    public void setSorting(SortingPageDto sorting) {
        this.sorting = sorting;
    }
}
