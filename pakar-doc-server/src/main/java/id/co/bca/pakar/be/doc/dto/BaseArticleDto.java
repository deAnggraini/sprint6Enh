package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;

public class BaseArticleDto extends ArticleTitleDto {
    @JsonProperty("id")
    protected Long id;
    @NotEmpty(message = "desc is required")
    @JsonProperty("usedBy")
    protected String usedBy;
    @NotNull(message = "structure id is required")
    @Min(value = 1, message = "minimum value is 1")
    @JsonProperty("lokasi")
    protected Long structureId;
    @NotNull(message = "template id required")
    @Min(value = 1, message = "minimum value is 1")
    @JsonProperty("templateId")
    protected Long templateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJudulArticle() {
        return judulArticle;
    }

    public void setJudulArticle(String judulArticle) {
        this.judulArticle = judulArticle;
    }

    public String getUsedBy() {
        return usedBy;
    }

    public void setUsedBy(String usedBy) {
        this.usedBy = usedBy;
    }

    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    @Override
    public String toString() {
        return "BaseArticleDto{" +
                "id=" + id +
                ", judulArticle='" + judulArticle + '\'' +
                ", usedBy='" + usedBy + '\'' +
                ", structureId=" + structureId +
                ", templateId=" + templateId +
                '}';
    }
}
