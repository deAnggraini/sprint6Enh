package id.co.bca.pakar.be.wf.api;

import id.co.bca.pakar.be.wf.client.ApiResponseWrapper;
import id.co.bca.pakar.be.wf.client.PakarOauthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MyController extends BaseController {
    @Autowired
    private PakarOauthClient pakarOauthClient;

    @GetMapping("/api/wf/test")
    public String user() {
        return "test resource server";
    }

    @PostMapping(value = "/api/wf/getRoles", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponseWrapper.RestResponse<List<String>>> getRoles(@RequestHeader("Authorization") String authorization, @RequestHeader("X-USERNAME") String username) {
        return pakarOauthClient.getRoles(authorization, username);
    }
}
