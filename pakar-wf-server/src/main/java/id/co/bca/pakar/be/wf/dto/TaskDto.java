package id.co.bca.pakar.be.wf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskDto {
    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("articleId")
    private Long articleId;

    @JsonProperty("assigne")
    private String assigne;

    @JsonProperty("current_state")
    private String currentState;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getAssigne() {
        return assigne;
    }

    public void setAssigne(String assigne) {
        this.assigne = assigne;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
