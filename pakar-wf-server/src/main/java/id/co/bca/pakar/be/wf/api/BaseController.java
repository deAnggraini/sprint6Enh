package id.co.bca.pakar.be.wf.api;

import id.co.bca.pakar.be.wf.client.ApiResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public abstract class BaseController {
	@Autowired
	protected MessageSource messageSource;

	protected ApiResponseWrapper apiResponseWrapper;
}
