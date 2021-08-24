package id.co.bca.pakar.be.wf.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Locale;

public abstract class BaseController {
	@Autowired
	protected MessageSource messageSource;

	protected Locale DEFAULT_LOCALE = null;

	protected Locale locale = DEFAULT_LOCALE;

	public Locale getDEFAULT_LOCALE() {
		return DEFAULT_LOCALE;
	}

	public void setDEFAULT_LOCALE(Locale DEFAULT_LOCALE) {
		this.DEFAULT_LOCALE = DEFAULT_LOCALE;
	}

	public Locale getLocale() {
		return locale;
	}

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
		return new ResponseEntity<>(new RestResponse<>(data, errorCode, errorMessage), HttpStatus.OK);
	}

	protected <T> ResponseEntity<RestResponse<List<T>>> createResponse(List<T> dataList, String errorCode, String errorMessage) {
		return new ResponseEntity<>(new RestResponse<>(dataList, errorCode, errorMessage), HttpStatus.OK);
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

	protected String getTokenFromHeader(String header) {
		String tokenValue = "";
		if (header != null && header.contains("Bearer")) {
			tokenValue = header.replace("Bearer", "").trim();
		}
		return tokenValue;
	}
}
