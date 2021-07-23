package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class PageDto extends BaseDto {
    @JsonProperty("page")
    protected Long page = 0L;
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
