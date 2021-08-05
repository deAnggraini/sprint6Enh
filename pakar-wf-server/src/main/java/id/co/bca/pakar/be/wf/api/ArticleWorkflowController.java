package id.co.bca.pakar.be.wf.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleWorkflowController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ArticleWorkflowController.class);

    @PostMapping(value = "/api/wf/start", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public String startProcess(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username) {
        return "";
    }
}
