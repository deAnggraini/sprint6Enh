package id.co.bca.pakar.be.doc.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public abstract class BaseController {
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

		public ApiStatus getApiStatus() {
			return apiStatus;
		}

		public void setApiStatus(ApiStatus apiStatus) {
			this.apiStatus = apiStatus;
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
		if(errorCode.equals("00")) {
			return new ResponseEntity<>(new RestResponse<>(data, errorCode, errorMessage), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new RestResponse<>(data, errorCode, errorMessage), HttpStatus.OK);
		}
	}
	
	protected <T> ResponseEntity<RestResponse<List<T>>> createResponse(List<T> dataList, String errorCode, String errorMessage) {
        if(errorCode.equals("00")) {
			return new ResponseEntity<>(new RestResponse<>(dataList, errorCode, errorMessage), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new RestResponse<>(dataList, errorCode, errorMessage), HttpStatus.OK);
		}
    }
	
	protected String getOriginalToken(String bearerToken) {
		String tokenValue = "";
		if (bearerToken != null && bearerToken.contains("Bearer")) {
            tokenValue = bearerToken.replace("Bearer", "").trim();            
        }
		return tokenValue;
	}
	
	protected Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey("pakar123").parseClaimsJws(token).getBody();
	}
}
