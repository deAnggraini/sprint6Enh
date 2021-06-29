package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangeToStructureDto {
    @JsonProperty("id")
    protected Long structureId;
    @JsonProperty("title")
    protected String title;
    @JsonProperty("changeTo")
    protected Long changeTo;
    @JsonProperty("error")
    protected String error="false";

    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getChangeTo() {
        return changeTo;
    }

    public void setChangeTo(Long changeTo) {
        this.changeTo = changeTo;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
