package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestResponseWrapperDto<T> {
    @JsonProperty("data")
    private T data;
    @JsonProperty("status")
    private ApiStatus apiStatus;

    public RestResponseWrapperDto(T pData) {
        this.data = pData;
    }

    public RestResponseWrapperDto(T pData, String errorCode, String errorMessage) {
        this.data = pData;
        this.apiStatus = new ApiStatus(errorCode, errorMessage);
    }

    public T getData() {
        return data;
    }

    public void setData(T pData) {
        this.data = pData;
    }

    protected class ApiStatus {
        @JsonProperty("code")
        private String code;
        @JsonProperty("message")
        private String message;

        public ApiStatus() {
            super();
        }

        public ApiStatus(String code, String message) {
            super();
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
