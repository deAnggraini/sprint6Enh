package id.co.bca.pakar.be.oauth2.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class BaseController {
	protected class RestResponse<T> {
		@JsonProperty("data")
		private T data;
		@JsonProperty("error")
		private String code = "00";
		@JsonProperty("msg")
		private String message = "SUCCESS";

		public RestResponse(T pData) {
			this.data = pData;
		}

		public RestResponse(T pData, String errorCode, String errorMessage) {
			this.data = pData;
			this.code = errorCode;
			this.message = errorMessage;
		}

		public T getData() {
			return data;
		}

		public void setData(T pData) {
			this.data = pData;
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
	
	protected <T> ResponseEntity<RestResponse<T>> createResponse(T data, String errorCode, String errorMessage) {
		if(errorCode.equals("00")) {
			return new ResponseEntity<>(new RestResponse<>(data, errorCode, errorMessage), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new RestResponse<>(data, errorCode, errorMessage), HttpStatus.OK);
		}
	}
}
