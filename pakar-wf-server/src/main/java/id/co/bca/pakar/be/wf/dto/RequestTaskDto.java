package id.co.bca.pakar.be.wf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestTaskDto extends PageDto {
    @JsonProperty("assigne")
    private String assigne;
    @JsonProperty("pic")
    private String pic;
    @JsonProperty("state")
    private String state;
    @JsonProperty("wfReqId")
    private String wfReqId;
    @JsonProperty("wfProcessKey")
    private String wfProcessKey;

    public String getAssigne() {
        return assigne;
    }

    public void setAssigne(String assigne) {
        this.assigne = assigne;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWfReqId() {
        return wfReqId;
    }

    public void setWfReqId(String wfReqId) {
        this.wfReqId = wfReqId;
    }

    public String getWfProcessKey() {
        return wfProcessKey;
    }

    public void setWfProcessKey(String wfProcessKey) {
        this.wfProcessKey = wfProcessKey;
    }
}
