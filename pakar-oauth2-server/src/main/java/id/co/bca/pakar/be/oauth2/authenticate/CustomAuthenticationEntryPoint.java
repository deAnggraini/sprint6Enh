package id.co.bca.pakar.be.oauth2.authenticate;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.bca.pakar.be.oauth2.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws ServletException {

        this.log.info("authException : " +authException.getMessage());

        String code = Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0];
        String message = authException.getMessage();
        if(message.toLowerCase().contains(Constant.ApiResponseCode.ACCESS_TOKEN_EXPIRED.getAction()[1].toLowerCase())) {
            code = Constant.ApiResponseCode.ACCESS_TOKEN_EXPIRED.getAction()[0];
            message = Constant.ApiResponseCode.ACCESS_TOKEN_EXPIRED.getAction()[1];
        } else if(message.toLowerCase().contains(Constant.ApiResponseCode.INVALID_TOKEN.getAction()[1].toLowerCase())) {
            code = Constant.ApiResponseCode.INVALID_TOKEN.getAction()[0];
            message = Constant.ApiResponseCode.INVALID_TOKEN.getAction()[1];
        } else if(message.toLowerCase().contains(Constant.ApiResponseCode.INVALID_ACCESS_TOKEN.getAction()[1].toLowerCase())) {
            code = Constant.ApiResponseCode.INVALID_ACCESS_TOKEN.getAction()[0];
            message = Constant.ApiResponseCode.INVALID_ACCESS_TOKEN.getAction()[1];
        } else if(message.toLowerCase().contains(Constant.ApiResponseCode.INVALID_REFRESH_TOKEN.getAction()[1].toLowerCase())) {
            code = Constant.ApiResponseCode.INVALID_REFRESH_TOKEN.getAction()[0];
            message = Constant.ApiResponseCode.INVALID_REFRESH_TOKEN.getAction()[1];
        } else if(message.toLowerCase().contains(Constant.ApiResponseCode.INVALID_GRANT.getAction()[1].toLowerCase())) {
            code = Constant.ApiResponseCode.INVALID_GRANT.getAction()[0];
            message = Constant.ApiResponseCode.INVALID_GRANT.getAction()[1];
        } else if(message.toLowerCase().contains(Constant.ApiResponseCode.REFRESH_TOKEN_EXPIRED.getAction()[1].toLowerCase())) {
            code = Constant.ApiResponseCode.REFRESH_TOKEN_EXPIRED.getAction()[0];
            message = Constant.ApiResponseCode.REFRESH_TOKEN_EXPIRED.getAction()[1];
        } else if(message.toLowerCase().contains(Constant.ApiResponseCode.INVALID_CLIENT_ID.getAction()[1].toLowerCase())) {
            code = Constant.ApiResponseCode.INVALID_CLIENT_ID.getAction()[0];
            message = Constant.ApiResponseCode.INVALID_CLIENT_ID.getAction()[1];
        } else if (message.toLowerCase().contains(Constant.ApiResponseCode.TOKEN_HAS_EXPIRED.getAction()[1].toLowerCase())) {
            code = Constant.ApiResponseCode.TOKEN_HAS_EXPIRED.getAction()[0];
            message = Constant.ApiResponseCode.TOKEN_HAS_EXPIRED.getAction()[1];
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
