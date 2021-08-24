package id.co.bca.pakar.be.wf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskDto {
    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("articleId")
    private Long articleId;

    @JsonProperty("sender")
    private String sender;

    @JsonProperty("assigne")
    private String assigne;

    @JsonProperty("current_state")
    private String currentState;

    @JsonProperty("current_sender_state")
    private String currentSenderState;

    @JsonProperty("current_receiver_state")
    private String currentReceiverState;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
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

    public String getCurrentSenderState() {
        return currentSenderState;
    }

    public void setCurrentSenderState(String currentSenderState) {
        this.currentSenderState = currentSenderState;
    }

    public String getCurrentReceiverState() {
        return currentReceiverState;
    }

    public void setCurrentReceiverState(String currentReceiverState) {
        this.currentReceiverState = currentReceiverState;
    }

    @Override
    public String toString() {
        return "TaskDto{" +
                "requestId='" + requestId + '\'' +
                ", articleId=" + articleId +
                ", sender='" + sender + '\'' +
                ", assigne='" + assigne + '\'' +
                ", currentState='" + currentState + '\'' +
                ", currentSenderState='" + currentSenderState + '\'' +
                ", currentReceiverState='" + currentReceiverState + '\'' +
                '}';
    }
}
