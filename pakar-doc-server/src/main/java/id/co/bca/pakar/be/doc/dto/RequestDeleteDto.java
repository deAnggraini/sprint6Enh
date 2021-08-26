package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestDeleteDto {
    @JsonProperty("id")
    protected Long id;

    @JsonProperty("isHasSend")
    protected Boolean isHasSend;

    @JsonProperty("sendTo")
    protected ArticleAssignerDto sendTo;

    @JsonProperty("sendNote")
    protected String sendNote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsHasSend() {
        return isHasSend;
    }

    public void setIsHasSend(Boolean isHasSend) {
        isHasSend = isHasSend;
    }

    public ArticleAssignerDto getSendTo() {
        return sendTo;
    }

    public void setSendTo(ArticleAssignerDto sendTo) {
        this.sendTo = sendTo;
    }

    public String getSendNote() {
        return sendNote;
    }

    public void setSendNote(String sendNote) {
        this.sendNote = sendNote;
    }
}
