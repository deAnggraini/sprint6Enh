package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestTemplateDto {
    @JsonProperty("structureId")
    private Long structureId;
    @JsonProperty("articleUsedBy")
    private String articleUsedBy;

    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }

    public String getArticleUsedBy() {
        return articleUsedBy;
    }

    public void setArticleUsedBy(String articleUsedBy) {
        this.articleUsedBy = articleUsedBy;
    }

    @Override
    public String toString() {
        return "RequestTemplateDto{" +
                "structureId=" + structureId +
                ", articleUsedBy='" + articleUsedBy + '\'' +
                '}';
    }
}
