package id.co.bca.pakar.be.doc.client;

import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dto.RequestTaskDto;
import id.co.bca.pakar.be.doc.dto.TaskDto;
import id.co.bca.pakar.be.doc.dto.wf.WfArticleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

@FeignClient(name = "pakar-wf-server", url = "${spring.security.oauth2.resourceserver.wf-server}")
public interface PakarWfClient {
    @PostMapping(value = "/api/wf/start", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ApiResponseWrapper.RestResponse<TaskDto>> start(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestHeader(Constant.Headers.X_USERNAME) String username, @RequestBody WfArticleDto articleDto);

    @PostMapping(value = "/api/wf/startProcess", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ApiResponseWrapper.RestResponse<TaskDto>> startProcess(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestHeader(Constant.Headers.X_USERNAME) String username, @RequestBody Map<String,Object> body);

    @PostMapping(value = "/api/wf/next", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ApiResponseWrapper.RestResponse<TaskDto>> next(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestHeader(Constant.Headers.X_USERNAME) String username, @RequestBody WfArticleDto articleDto);

    @PostMapping(value = "/api/wf/completeTask", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ApiResponseWrapper.RestResponse<TaskDto>> completeTask(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestHeader(Constant.Headers.X_USERNAME) String username, @RequestBody Map<String,Object> body);

    @PostMapping(value = "/api/wf/cancelTask", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ApiResponseWrapper.RestResponse<TaskDto>> cancelTask(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody Map<String,Object> body);

    @PostMapping(value = "/api/wf/tasks", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ApiResponseWrapper.RestResponse<List<TaskDto>>> getTasks(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestHeader(Constant.Headers.X_USERNAME) String username, @RequestBody RequestTaskDto dto);

    @PostMapping(value = "/api/wf/tasksWithState", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ApiResponseWrapper.RestResponse<List<TaskDto>>> getTasksWithState(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestHeader(Constant.Headers.X_USERNAME) String username, @RequestBody RequestTaskDto dto);

    @PostMapping(value = "/api/wf/requestDelete", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ApiResponseWrapper.RestResponse<TaskDto>> requestDelete(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestHeader(Constant.Headers.X_USERNAME) String username, @RequestBody Map<String,Object> body);

}
