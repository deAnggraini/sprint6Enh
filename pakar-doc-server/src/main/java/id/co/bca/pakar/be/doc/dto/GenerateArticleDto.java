package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GenerateArticleDto extends ArticleTitleDto {
    @JsonProperty("id")
    protected Long id;
    @NotEmpty(message = "desc is required")
    @JsonProperty("usedBy")
    protected String usedBy;
    @NotNull(message = "structure id is required")
    @Min(value = 1, message = "minimum value is 1")
    @JsonProperty("structureId")
    protected Long structureId;
    @NotNull(message = "template id required")
    @Min(value = 1, message = "minimum value is 1")
    @JsonProperty("templateId")
    protected Long templateId;
    @JsonProperty("paramKey")
    protected String paramKey;
    @JsonProperty("paramValue")
    protected String paramValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    private MultipartFile image;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
