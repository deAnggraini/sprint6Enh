package id.co.bca.pakar.be.wf.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.bca.pakar.be.wf.common.Constant;
import id.co.bca.pakar.be.wf.dto.ArticleDto;
import id.co.bca.pakar.be.wf.dto.RequestTaskDto;
import id.co.bca.pakar.be.wf.dto.TaskDto;
import id.co.bca.pakar.be.wf.exception.UndefinedProcessException;
import id.co.bca.pakar.be.wf.exception.UndefinedStartedStateException;
import id.co.bca.pakar.be.wf.exception.UndefinedUserTaskException;
import id.co.bca.pakar.be.wf.service.ArticleWorkflowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ArticleProcessController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ArticleProcessController.class);

    @Autowired
    private ArticleWorkflowService articleWorkflowService;

    /**
     * start workflow request
     *
     * @param authorization
     * @param username
     * @param articleDto
     */
    @PostMapping(value = "/api/wf/start", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<TaskDto>> start(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody ArticleDto articleDto) {
        try {
            logger.info("receive request to start article workflow");
            ObjectMapper oMapper = new ObjectMapper();
            TaskDto taskDto = articleWorkflowService.startProcess(username, oMapper.convertValue(articleDto, Map.class));
            return createResponse(taskDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, locale));
        } catch (UndefinedUserTaskException e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("usertask.not.found", null, locale));
        } catch (UndefinedProcessException e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, locale));
        } catch (UndefinedStartedStateException e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, locale));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, locale));
        }
    }

    /**
     *
     * @param authorization
     * @param username
     * @param body
     * @return
     */
    @PostMapping(value = "/api/wf/startProcess", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<TaskDto>> startProcess(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody Map<String, Object> body) {
        try {
            logger.info("request to start workflow {}", body.get(Constant.Workflow.PROCESS_KEY));
            TaskDto taskDto = articleWorkflowService.startProcess(username, getTokenFromHeader(authorization), body);
            return createResponse(taskDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, locale));
        } catch (UndefinedUserTaskException e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("usertask.not.found", null, locale));
        } catch (UndefinedProcessException e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, locale));
        } catch (UndefinedStartedStateException e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, locale));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, locale));
        }
    }


    @PostMapping(value = "/api/wf/next", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<TaskDto>> next(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody ArticleDto articleDto) {
        try {
            logger.info("receive request to process workflow");
            ObjectMapper oMapper = new ObjectMapper();
            TaskDto taskDto = articleWorkflowService.next(username, oMapper.convertValue(articleDto, Map.class));
            return createResponse(taskDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, locale));
        } catch (UndefinedUserTaskException e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("usertask.not.found", null, locale));
        } catch (UndefinedProcessException e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, locale));
        } catch (UndefinedStartedStateException e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, locale));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, locale));
        }
    }

    /**
     * complete task of assigner
     * <p>
     *     user make action to complete received task from other stakeholder
     *     the user action likes : approve, reject, delete etc
     * </p>
     * @param authorization
     * @param username
     * @param body
     * @return
     */
    @PostMapping(value = "/api/wf/completeTask", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<TaskDto>> completeTask(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody Map<String,Object> body) {
        try {
            logger.info("receive request to complete task");
            TaskDto taskDto = articleWorkflowService.completeTask(username, getTokenFromHeader(authorization), body);
            return createResponse(taskDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, locale));
        } catch (UndefinedUserTaskException e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("usertask.not.found", null, locale));
        } catch (UndefinedProcessException e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("process.key.undefined", null, locale));
        } catch (UndefinedStartedStateException e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("started.state.undefined", null, locale));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, locale));
        }
    }


    /**
     *
     * @param authorization
     * @param username
     * @param body
     * @return
     */
    @PostMapping(value = "/api/wf/cancelTask", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<TaskDto>> cancelTask(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody Map<String,Object> body) {
        try {
            logger.info("receive request to cancel task");
            TaskDto taskDto = articleWorkflowService.cancelTask(username, getTokenFromHeader(authorization), body);
            return createResponse(taskDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, locale));
        } catch (UndefinedUserTaskException e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("usertask.not.found", null, locale));
        } catch (UndefinedProcessException e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("process.key.undefined", null, locale));
        } catch (UndefinedStartedStateException e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("started.state.undefined", null, locale));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, locale));
        }
    }

    /**
     * reject task
     * @param authorization
     * @param username
     * @param articleDto
     * @return
     */
    @PostMapping(value = "/api/wf/rejectTask", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<TaskDto>> rejectTask(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody ArticleDto articleDto) {
        try {
            logger.info("receive request to process workflow");
            ObjectMapper oMapper = new ObjectMapper();
            TaskDto taskDto = articleWorkflowService.next(username, oMapper.convertValue(articleDto, Map.class));
            return createResponse(taskDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, locale));
        } catch (UndefinedUserTaskException e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("usertask.not.found", null, locale));
        } catch (UndefinedProcessException e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, locale));
        } catch (UndefinedStartedStateException e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, locale));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, locale));
        }
    }

    /**
     * API for get all task for certain assigne
     *
     * @param authorization
     * @param username
     * @param dto
     * @return
     */
    @PostMapping("/api/wf/tasks")
    public ResponseEntity<RestResponse<List<TaskDto>>> getTasks(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody RequestTaskDto dto) {
        try {
            logger.info("get All Task");
            logger.info("received token bearer --- {}", authorization);
            dto.setUsername(username);
            dto.setToken(getTokenFromHeader(authorization));
            List<TaskDto> taskDtos = articleWorkflowService.getTasks(username);
            return createResponse(taskDtos, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, locale));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<TaskDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, locale));
        }
    }

    /**
     * API for get all task for certain assigne
     *
     * @param authorization
     * @param username
     * @param dto
     * @return
     */
    @PostMapping("/api/wf/tasksWithState")
    public ResponseEntity<RestResponse<List<TaskDto>>> getTasksWithState(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody RequestTaskDto dto) {
        try {
            logger.info("get All Task");
            logger.info("received token bearer --- {}", authorization);
            dto.setUsername(username);
            dto.setToken(getTokenFromHeader(authorization));
            List<TaskDto> taskDtos = articleWorkflowService.getTasksWithPicState(username, dto.getState(), dto.getWfProcessKey());
            return createResponse(taskDtos, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, locale));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<TaskDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, locale));
        }
    }

    @PostMapping("/api/wf/taskRequest")
    public ResponseEntity<RestResponse<List<TaskDto>>> getTaskRequest(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody RequestTaskDto dto) {
        try {
            logger.info("get All Task");
            logger.info("received token bearer --- {}", authorization);
            dto.setUsername(username);
            dto.setToken(getTokenFromHeader(authorization));
            List<TaskDto> taskDtos = articleWorkflowService.getTasks(username);
            return createResponse(taskDtos, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, locale));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<TaskDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, locale));
        }
    }

    @PostMapping("/api/wf/requestDelete")
    public ResponseEntity<RestResponse<TaskDto>> requestDelete(@RequestHeader(name = "Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody Map<String, Object> body) {
        try {
            logger.info("body requestDelete " + body);
            logger.info("received token bearer --- {}", authorization);
            TaskDto taskDtos = articleWorkflowService.requestDelete(username, body);
            return createResponse(taskDtos, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, locale));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new TaskDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, locale));
        }
    }
}
