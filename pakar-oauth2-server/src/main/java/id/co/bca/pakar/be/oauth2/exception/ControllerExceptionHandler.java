package id.co.bca.pakar.be.oauth2.exception;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import id.co.bca.pakar.be.oauth2.common.Constant;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();

		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		logger.info("validate failed "+errors);
        HashMap<String, String> responseStatus = new HashMap<>();
        responseStatus.put("code", Constant.ApiResponseCode.INCORRECT_USERNAME_PASSWORD.getAction()[0]);
        responseStatus.put("message", Constant.ApiResponseCode.INCORRECT_USERNAME_PASSWORD.getAction()[1]);

        body.put("data", 0);
        body.put("status", responseStatus);

		return new ResponseEntity<>(body, headers, HttpStatus.OK);
	}
}
