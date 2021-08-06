package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MyPagesDto {
    @JsonProperty("draft")
    private List<ResponseMyPages> draft;
    @JsonProperty("pending")
    private List<ResponseMyPages> pending;
    @JsonProperty("approved")
    private List<ResponseMyPages> approved;

    public List<ResponseMyPages> getDraft() {
        return draft;
    }

    public void setDraft(List<ResponseMyPages> draft) {
        this.draft = draft;
    }

    public List<ResponseMyPages> getPending() {
        return pending;
    }

    public void setPending(List<ResponseMyPages> pending) {
        this.pending = pending;
    }
}
