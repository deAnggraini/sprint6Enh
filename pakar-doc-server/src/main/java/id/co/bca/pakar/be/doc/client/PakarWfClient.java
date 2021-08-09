package id.co.bca.pakar.be.doc.client;

import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dto.ArticleDto;
import id.co.bca.pakar.be.doc.dto.MultipartArticleDto;
import id.co.bca.pakar.be.doc.dto.RequestTaskDto;
import id.co.bca.pakar.be.doc.dto.TaskDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "pakar-doc-server", url = "${spring.security.oauth2.resourceserver.doc-server}")
//Enabling feign
//@FeignClient(name="pakar-doc-service")
//enabling ribbon
//@RibbonClient(name="pakar-doc-service")
public interface PakarWfClient {
    @PostMapping(value = "/api/wf/draft", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ApiResponseWrapper.RestResponse<TaskDto>> saveDraft(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestHeader(Constant.Headers.X_USERNAME) String username, @RequestBody MultipartArticleDto articleDto);
    @PostMapping(value = "/api/wf/sendDraft", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ApiResponseWrapper.RestResponse<TaskDto>> sendDraft(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestHeader(Constant.Headers.X_USERNAME) String username, @RequestBody MultipartArticleDto articleDto);
    @PostMapping(value = "/api/wf/tasks", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ApiResponseWrapper.RestResponse<List<TaskDto>>> getTasks(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestHeader(Constant.Headers.X_USERNAME) String username, @RequestBody RequestTaskDto dto);

}
