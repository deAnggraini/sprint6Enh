package id.co.bca.pakar.be.wf.service;

import id.co.bca.pakar.be.wf.dao.*;
import id.co.bca.pakar.be.wf.dto.AssignDto;
import id.co.bca.pakar.be.wf.dto.TaskDto;
import id.co.bca.pakar.be.wf.exception.UndefinedProcessException;
import id.co.bca.pakar.be.wf.exception.UndefinedStartedStateException;
import id.co.bca.pakar.be.wf.exception.UndefinedUserTaskException;
import id.co.bca.pakar.be.wf.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ArticleWorkflowService {
    private static Logger logger = LoggerFactory.getLogger(ArticleWorkflowService.class);
    private static String ARTICLE_PROCESS_DEF = "ARTICLE_REVIEW";
    private static Long USER_TASK_DEFAULT_DEF = 1L;

    @Autowired
    private WorkflowProcessRepository workflowProcessRepository;

    @Autowired
    private WorkflowRequestRepository workflowRequestRepository;

    @Autowired
    private WorkflowStateRepository workflowStateRepository;

    @Autowired
    private WorkflowUserTaskRepository workflowUserTaskRepository;

    @Autowired
    private WorkflowRequestUserTaskRepository workflowRequestUserTaskRepository;

    @Autowired
    private WorkflowRequestDataRepository workflowRequestDataRepository;

    @Autowired
    private WorkflowTransitionRepository workflowTransitionRepository;

    /**
     * start process workflow article
     *
     * @param username
     * @param map
     */
    @Transactional(rollbackFor = {Exception.class,
            UndefinedProcessException.class,
            UndefinedStartedStateException.class})
    public TaskDto startProcess(String username, Map map) throws Exception {
        try {
            logger.debug("start process workflow");
            logger.debug("article id {}", map.get("id"));
            Map<String, Object> variables = new HashMap<>();
            variables.put("sender", map.get("sender"));
            variables.put("receiver", map.get("receiver"));
            variables.put("article_id", map.get("id"));
            variables.put("title", map.get("judul_article"));
            Optional<WorkflowProcessModel> workflowProcessOpt = workflowProcessRepository.findById(ARTICLE_PROCESS_DEF);
            if (workflowProcessOpt.isEmpty()) {
                throw new UndefinedProcessException("undefined process " + ARTICLE_PROCESS_DEF);
            }
            WorkflowProcessModel workflowProcessModel = workflowProcessOpt.isPresent() ? workflowProcessOpt.get() : null;

            logger.info("find started state");
            WorkflowStateModel initState = workflowStateRepository.findDefaultStartStateById();
            if (initState == null) {
                throw new UndefinedStartedStateException("undefined started state ");
            }

            WorkflowRequestModel requestModel = new WorkflowRequestModel();
            requestModel.setCreatedBy(username);
            Date requestDate = new Date();
            requestModel.setCreatedDate(requestDate);
            requestModel.setRequestDate(requestDate);
            requestModel.setTitle((String) variables.get("title"));
            requestModel.setUserid(username);
            requestModel.setCurrentState(initState);
            requestModel.setWfprocess(workflowProcessModel);

            logger.info("save request flow");
            requestModel = workflowRequestRepository.save(requestModel);

            WorkflowRequestDataModel requestDataModel = new WorkflowRequestDataModel();
            requestDataModel.setCreatedBy(username);
            requestDataModel.setWfRequest(requestModel);
            requestDataModel.setName("ARTICLE_ID");
            requestDataModel.setValue("" + variables.get("article_id"));
            requestDataModel = workflowRequestDataRepository.save(requestDataModel);

            Optional<WorkflowUserTaskModel> userTaskOpt = workflowUserTaskRepository.findById(USER_TASK_DEFAULT_DEF);
            if (userTaskOpt.isEmpty()) {
                throw new UndefinedUserTaskException("undefined user task");
            }

            WorkflowUserTaskModel userTaskModel = userTaskOpt.get();

            WorkflowRequestUserTaskModel requestUserTaskModel = new WorkflowRequestUserTaskModel();
            requestUserTaskModel.setCreatedBy(username);
//            requestUserTaskModel.setUserTaskModel(userTaskModel);
            requestUserTaskModel.setRequestModel(requestModel);
            requestUserTaskModel.setProposedBy(username);
            requestUserTaskModel.setAssigne(username); // assign to self pic
            workflowRequestUserTaskRepository.save(requestUserTaskModel);

            TaskDto taskDto = new TaskDto();
            taskDto.setCurrentState(initState.getCode());
            taskDto.setArticleId((Long) variables.get("article_id"));
            taskDto.setRequestId(requestModel.getId());
            taskDto.setAssigne(requestUserTaskModel.getAssigne());

            return taskDto;
        } catch (UndefinedUserTaskException e) {
            logger.error("exception", e);
            throw new UndefinedUserTaskException("user task not found");
        } catch (UndefinedProcessException e) {
            logger.error("exception", e);
            throw new UndefinedProcessException("process not found");
        } catch (UndefinedStartedStateException e) {
            logger.error("exception", e);
            throw new UndefinedStartedStateException("start state not found");
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception(e);
        }
    }

    /**
     * start process workflow article
     *
     * @param username
     * @param map
     */
    @Transactional(rollbackFor = {Exception.class,
            UndefinedProcessException.class,
            UndefinedStartedStateException.class})
    public TaskDto next(String username, Map map) throws Exception {
        try {
            logger.debug("move to next state");
            logger.debug("article id {}", map.get("id"));
            Map<String, Object> variables = new HashMap<>();
            variables.put("sendTo", map.get("sendTo"));
            variables.put("article_id", map.get("id"));
            Optional<WorkflowProcessModel> workflowProcessOpt = workflowProcessRepository.findById(ARTICLE_PROCESS_DEF);
            if (workflowProcessOpt.isEmpty()) {
                throw new UndefinedProcessException("undefined process " + ARTICLE_PROCESS_DEF);
            }
            WorkflowProcessModel workflowProcessModel = workflowProcessOpt.isPresent() ? workflowProcessOpt.get() : null;

            logger.debug("find workflow request by article id {}", variables.get("article_id"));
            WorkflowRequestModel currentWfRequest = workflowRequestUserTaskRepository.findWorkflowRequest((String)variables.get("article_id"));
            logger.debug("current state is {}", currentWfRequest.getCurrentState().getCode());

            logger.debug("find workflow state by current state");
            WorkflowStateModel currentState = workflowStateRepository.findStateByName(currentWfRequest.getCurrentState().getCode(), ARTICLE_PROCESS_DEF);

            Iterable<WorkflowTransitionModel> wfTransitions = workflowTransitionRepository.findTransitionByStartState(currentState.getCode());

            logger.debug("task type from user {}", map.get("taskType"));

            AssignDto assignDto = (AssignDto) map.get("sendTo");

//            WorkflowRequestModel currentWfRequest
//
//            requestModel.setCreatedBy(username);
//            Date requestDate = new Date();
//            requestModel.setCreatedDate(requestDate);
//            requestModel.setRequestDate(requestDate);
//            requestModel.setTitle((String) variables.get("title"));
//            requestModel.setUserid(username);
//            requestModel.setCurrentState(initState);
//            requestModel.setWfprocess(workflowProcessModel);
//
//            logger.info("save request flow");
//            requestModel = workflowRequestRepository.save(requestModel);
//
//            WorkflowRequestDataModel requestDataModel = new WorkflowRequestDataModel();
//            requestDataModel.setCreatedBy(username);
//            requestDataModel.setWfRequest(requestModel);
//            requestDataModel.setName("ARTICLE_ID");
//            requestDataModel.setValue("" + variables.get("article_id"));
//            requestDataModel = workflowRequestDataRepository.save(requestDataModel);
//
//            Optional<WorkflowUserTaskModel> userTaskOpt = workflowUserTaskRepository.findById(USER_TASK_DEFAULT_DEF);
//            if (userTaskOpt.isEmpty()) {
//                throw new UndefinedUserTaskException("undefined user task");
//            }
//
//            WorkflowUserTaskModel userTaskModel = userTaskOpt.get();
//
//            WorkflowRequestUserTaskModel requestUserTaskModel = new WorkflowRequestUserTaskModel();
//            requestUserTaskModel.setCreatedBy(username);
//            requestUserTaskModel.setUserTaskModel(userTaskModel);
//            requestUserTaskModel.setRequestModel(requestModel);
//            requestUserTaskModel.setProposedBy(username);
//            requestUserTaskModel.setAssigne(username); // assign to self pic
//            workflowRequestUserTaskRepository.save(requestUserTaskModel);

            TaskDto taskDto = new TaskDto();
//            taskDto.setCurrentState(initState.getCode());
            taskDto.setArticleId((Long) variables.get("article_id"));
            taskDto.setRequestId(currentWfRequest.getId());

            taskDto.setAssigne(assignDto.getUsername());

            return taskDto;
        } catch (UndefinedProcessException e) {
            logger.error("exception", e);
            throw new UndefinedProcessException("process not found");
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception(e);
        }
    }

    @Transactional
    public List<TaskDto> getTasks(String assignee) throws Exception {
        logger.debug("get all task for assigne {}", assignee);
        try {
            Iterable<WorkflowRequestUserTaskModel> tasks = workflowRequestUserTaskRepository.findByAssigne(assignee);
            List<TaskDto> dtos = new MapperHelper().mapToTaskDtos(tasks);
            return dtos;
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("exception", e);
        }
    }

//    @Transactional
//    public List<TaskDto> getTaskRequest(String assignee) throws Exception {
//        logger.debug("get all request task for assigne {}", assignee);
//        try {
//            Iterable<WorkflowRequestModel> tasks = workflowRequestRepository.findByAssigne(assignee);
//            List<TaskDto> dtos = new MapperHelper().mapToTaskDtos(tasks);
//            return dtos;
//        } catch (Exception e) {
//            logger.error("exception", e);
//            throw new Exception("exception", e);
//        }
//    }
//
//    @Transactional
//    public void submitReview(Approval approval) {
//        Map<String, Object> variables = new HashMap<String, Object>();
//        variables.put("approved", approval.isStatus());
//        taskService.complete(approval.getId(), variables);
//    }

    private class MapperHelper {
        public List<TaskDto> mapToTaskDtos(Iterable<WorkflowRequestUserTaskModel> iterable) {
            List<TaskDto> dtos = new ArrayList<>();
            for (WorkflowRequestUserTaskModel model : iterable) {
                TaskDto dto = new TaskDto();
                dto.setAssigne(model.getAssigne());
                dto.setCurrentState(model.getRequestModel().getCurrentState().getCode());
                dto.setRequestId(model.getRequestModel().getId());
                logger.debug("article id {}", model.getRequestModel().getId());
                Optional<WorkflowRequestDataModel> requestDataModelOpt = workflowRequestDataRepository.findByNameAndRequestId("ARTICLE_ID", model.getRequestModel().getId());
                if (requestDataModelOpt.isPresent()) {
                    WorkflowRequestDataModel requestDataModel = requestDataModelOpt.get();
                    logger.debug("article id {}", requestDataModel.getValue());
                    dto.setArticleId(Long.parseLong(requestDataModel.getValue()));
                }
                dtos.add(dto);
            }
            return dtos;
        }
    }
}
