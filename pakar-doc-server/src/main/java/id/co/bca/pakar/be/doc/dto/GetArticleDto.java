package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetArticleDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("isEdit")
    private boolean isEdit = false;
    /*
    needClone = TRUE if is published = true and isClone = false
    else
    needClone = FALSE
     */
    @JsonProperty("needClone")
    private Boolean needClone = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(boolean edit) {
        isEdit = edit;
    }

    public Boolean getNeedClone() {
        return needClone;
    }

    public void setNeedClone(Boolean needClone) {
        this.needClone = needClone;
    }
}
