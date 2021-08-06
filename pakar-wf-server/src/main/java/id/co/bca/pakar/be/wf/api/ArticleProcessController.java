package id.co.bca.pakar.be.wf.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.bca.pakar.be.wf.common.Constant;
import id.co.bca.pakar.be.wf.dto.ArticleDto;
import id.co.bca.pakar.be.wf.dto.RequestTaskDto;
import id.co.bca.pakar.be.wf.dto.TaskDto;
import id.co.bca.pakar.be.wf.service.ArticleWorkflowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
public class ArticleProcessController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ArticleProcessController.class);

    @Autowired
    private ArticleWorkflowService articleWorkflowService;

//    @PostMapping(value = "/api/wf/start", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
//            MediaType.APPLICATION_JSON_VALUE})
//    public String startProcess(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username) {
//        return "";
//    }

//    @PostMapping(value="/api/ps/submit", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
//            MediaType.APPLICATION_JSON_VALUE})
//    public void submit(@RequestBody Article article) {
////        service.startProcess(article);
//    }
//
//    @GetMapping("/api/ps/tasks")
//    public List<Article> getTasks(@RequestParam String assignee) {
////        return service.getTasks(assignee);
//    }
//
//    @PostMapping("/api/ps/review")
//    public void review(@RequestBody Approval approval) {
////        service.submitReview(approval);
//    }

    @PostMapping(value = "/api/wf/draft", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public void submitDraft(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody ArticleDto articleDto) {
        try {
            ObjectMapper oMapper = new ObjectMapper();
            articleWorkflowService.startProcess(username, oMapper.convertValue(articleDto, Map.class));
        } catch (Exception e) {
            logger.error("exception", e);
        }
    }

    @PostMapping(value = "/api/wf/draftAndSend", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public void submitDraftAndSend(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody ModelMap modelMap) {
//        service.startProcess(article);
    }

    @PostMapping("/api/wf/tasks")
    public ResponseEntity<RestResponse<List<TaskDto>>> getTasks(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody RequestTaskDto dto) {
        try {
            logger.info("get All Task");
            logger.info("received token bearer --- {}", authorization);
            dto.setUsername(username);
            dto.setToken(getTokenFromHeader(authorization));
//            Boolean status = articleWorkflowService.deleteContent(deleteContentDto);
            return createResponse(new ArrayList<TaskDto>(), Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, Locale.ENGLISH));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<TaskDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, Locale.ENGLISH));
        }
    }
}
