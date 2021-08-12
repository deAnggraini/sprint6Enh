package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RequestUpdateNotificationDto {
    @JsonProperty("isAll")
    private boolean isAll;

    @JsonProperty("id")
    private List<Long> ids;

    public boolean getIsAll() {
        return isAll;
    }

    public void setIsAll(boolean all) {
        isAll = all;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
