package id.co.bca.pakar.be.wf.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");

        this.log.info("##### accessDeniedException : " +accessDeniedException.getMessage());

        // --- ALGORITHM
        HashMap<String, Object> map = new HashMap();
        HashMap<String, String> status = new HashMap<>();

        status.put("code", "400");
        status.put("message", accessDeniedException.getMessage());

        map.put("data", 0);
        map.put("status", status);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), map);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}
