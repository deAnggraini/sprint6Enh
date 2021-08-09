package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestTaskDto extends PageDto {
    @JsonProperty("assigne")
    private String assigne;

    public String getAssigne() {
        return assigne;
    }

    public void setAssigne(String assigne) {
        this.assigne = assigne;
    }
}
