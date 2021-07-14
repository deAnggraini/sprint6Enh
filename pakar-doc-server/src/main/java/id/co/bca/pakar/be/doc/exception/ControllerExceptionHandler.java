package id.co.bca.pakar.be.doc.exception;

import id.co.bca.pakar.be.doc.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		String errMessage = "";
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
//			errors.add(error.getField() + ": " + error.getDefaultMessage());
			errors.add(error.getDefaultMessage());
			errMessage = error.getDefaultMessage();
		}
//		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
//			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
//		}
		logger.debug("validate failed with error {}", errors);
        HashMap<String, String> responseStatus = new HashMap<>();
        responseStatus.put("code", Constant.ApiResponseCode.REQUEST_PARAM_INVALID.getAction()[0]);
//		responseStatus.put("message", Constant.ApiResponseCode.REQUEST_PARAM_INVALID.getAction()[1]);
		responseStatus.put("message", errMessage);

		Map<String, Object> body = new LinkedHashMap<>();
        body.put("data", 0);
        body.put("status", responseStatus);

		return new ResponseEntity<>(body, headers, HttpStatus.OK);
	}
}
