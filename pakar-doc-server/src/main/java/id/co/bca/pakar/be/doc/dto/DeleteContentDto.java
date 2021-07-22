package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class DeleteContentDto extends BaseDto {
    @NotNull(message = "id is required")
    @Min(value = 0, message = "minimum value is 0")
    @JsonProperty("id")
    protected Long contentId;

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }
}
