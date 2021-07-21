package id.co.bca.pakar.be.doc.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.bca.pakar.be.doc.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected MessageSource messageSource;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws ServletException {

        this.log.info("authException : " +authException.getMessage());

        String code = Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0];
        String message = authException.getMessage();
        if(message.toLowerCase().contains(Constant.ApiResponseCode.ACCESS_TOKEN_EXPIRED.getAction()[1].toLowerCase())) {
            message = messageSource.getMessage("accesstoken.auth.expired", null, new Locale("en", "US"));
        } else if(message.toLowerCase().contains(Constant.ApiResponseCode.INVALID_TOKEN.getAction()[1].toLowerCase())) {
            message = messageSource.getMessage("token.auth.invalid", null, new Locale("en", "US"));
        } else if(message.toLowerCase().contains(Constant.ApiResponseCode.INVALID_ACCESS_TOKEN.getAction()[1].toLowerCase())) {
            message = messageSource.getMessage("accesstoken.auth.invalid", null, new Locale("en", "US"));
        } else if(message.toLowerCase().contains(Constant.ApiResponseCode.INVALID_REFRESH_TOKEN.getAction()[1].toLowerCase())) {
            message = messageSource.getMessage("refreshtoken.auth.invalid", null, new Locale("en", "US"));
        } else if(message.toLowerCase().contains(Constant.ApiResponseCode.INVALID_GRANT.getAction()[1].toLowerCase())) {
            message = messageSource.getMessage("grant.auth.expired", null, new Locale("en", "US"));
        } else if(message.toLowerCase().contains(Constant.ApiResponseCode.REFRESH_TOKEN_EXPIRED.getAction()[1].toLowerCase())) {
            message = messageSource.getMessage("refreshtoken.auth.expired", null, new Locale("en", "US"));
        } else if(message.toLowerCase().contains(Constant.ApiResponseCode.INVALID_CLIENT_ID.getAction()[1].toLowerCase())) {
            message = messageSource.getMessage("clientid.auth.invalid", null, new Locale("en", "US"));
        } else if (message.toLowerCase().contains(Constant.ApiResponseCode.TOKEN_HAS_EXPIRED.getAction()[1].toLowerCase())) {
            message = messageSource.getMessage("token.auth.expired", null, new Locale("en", "US"));
        }

        HashMap<String, String> apiStatus = new HashMap<>();
        apiStatus.put("code", code);
        apiStatus.put("message", message);
        HashMap<String, Object> restResponse = new HashMap();
        restResponse.put("data", 0);
        restResponse.put("status", apiStatus);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), restResponse);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}
