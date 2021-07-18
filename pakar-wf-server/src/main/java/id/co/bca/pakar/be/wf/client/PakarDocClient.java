package id.co.bca.pakar.be.wf.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "pakar-doc-server", url = "localhost:12082")
public interface PakarDocClient {
    @GetMapping("/api/v1/doc/theme")
//    String getUser(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader, @HeaderParam("X-USERNAME") String username);
    String getTheme();
    @GetMapping("/api/doc/popular")
    String popular(@RequestHeader("Authorization") String authorizationHeader, @RequestHeader("X-USERNAME") String username, @RequestBody String msg);
}
