package id.co.bca.pakar.be.wf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public abstract class PageDto extends BaseDto {
    @NotNull
    @Min(value = 1, message = "minimum page value 1")
    @JsonProperty("page")
    protected Long page = 0L;
    @NotNull
    @Min(value = 1, message = "minimum limit value 1")
    @JsonProperty("limit")
    protected Long size = 10L;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
