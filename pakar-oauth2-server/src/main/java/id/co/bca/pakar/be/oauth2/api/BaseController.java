package id.co.bca.pakar.be.oauth2.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class BaseController {
	@Autowired
	protected MessageSource messageSource;

	protected class RestResponse<T> {
		@JsonProperty("data")
		private T data;
		@JsonProperty("status")
		private ApiStatus apiStatus;

		public RestResponse(T pData) {
			this.data = pData;
		}

		public RestResponse(T pData, String errorCode, String errorMessage) {
			this.data = pData;
			this.apiStatus = new ApiStatus(errorCode, errorMessage);
		}

		public T getData() {
			return data;
		}

		public void setData(T pData) {
			this.data = pData;
		}
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

	protected <T> ResponseEntity<RestResponse<T>> createResponse(T data, String errorCode, String errorMessage) {
		return new ResponseEntity<>(new RestResponse<>(data, errorCode, errorMessage), HttpStatus.OK);
	}

	protected <T> ResponseEntity<RestResponse<List<T>>> createResponse(List<T> dataList, String errorCode,
			String errorMessage) {
		return new ResponseEntity<>(new RestResponse<>(dataList, errorCode, errorMessage), HttpStatus.OK);
	}

	// login
	protected class RestResponses<T> {
		@JsonProperty("data")
		private T data;
		@JsonProperty("status")
		private ApiStatus2 apiStatus;

		public RestResponses(T pData) {
			this.data = pData;
		}

		public RestResponses(T pData, String errorCode, String errorMessage, String alertMessage, Integer failCount) {
			this.data = pData;
			this.apiStatus = new ApiStatus2(errorCode, errorMessage, alertMessage, failCount);
		}

		public T getData() {
			return data;
		}

		public void setData(T pData) {
			this.data = pData;
		}
	}

	protected class ApiStatus2 {
		@JsonProperty("code")
		private String code;
		@JsonProperty("message")
		private String message;
		@JsonProperty("alert")
		private String alert;
		@JsonProperty("failCount")
		private Integer failCount;

		public ApiStatus2() {
			super();
		}

		public ApiStatus2(String code, String message, String alert, int failCount) {
			super();
			this.code = code;
			this.message = message;
			this.alert = alert;
			this.failCount = failCount;
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

		public String getAlert() {
			return alert;
		}

		public void setAlert(String alert) {
			this.alert = alert;
		}

		public Integer getFailCount() {
			return failCount;
		}

		public void setFailCount(Integer failCount) {
			this.failCount = failCount;
		}
	}

	protected <T> ResponseEntity<RestResponses<T>> createResponses(T data, String errorCode, String errorMessage, String alertMessage, int failCount) {
		return new ResponseEntity<>(new RestResponses<>(data, errorCode, errorMessage, alertMessage, failCount), HttpStatus.OK);
	}

	protected <T> ResponseEntity<RestResponses<List<T>>> createResponses(List<T> dataList, String errorCode,
																	   String errorMessage, String alertMessage, int failCount) {
		return new ResponseEntity<>(new RestResponses<>(dataList, errorCode, errorMessage, alertMessage, failCount), HttpStatus.OK);
	}
}
