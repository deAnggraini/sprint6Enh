package id.co.bca.pakar.be.wf.service;

import id.co.bca.pakar.be.wf.dao.*;
import id.co.bca.pakar.be.wf.dto.TaskDto;
import id.co.bca.pakar.be.wf.exception.UndefinedProcessException;
import id.co.bca.pakar.be.wf.exception.UndefinedStartedStateException;
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

    @Autowired
    private WorkflowTransitionUserTaskRepository workflowTransitionUserTaskRepository;

    @Autowired
    private WorkflowPersonStateRepository workflowPersonStateRepository;

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
            logger.debug("request parameter map {}", map);
            Map<String, Object> variables = new HashMap<>();
            variables.put("sender", map.get("sender"));
            variables.put("receiver", map.get("receiver"));
            variables.put("article_id", map.get("id"));
            variables.put("title", map.get("judulArticle"));
            Optional<WorkflowProcessModel> workflowProcessOpt = workflowProcessRepository.findById(ARTICLE_PROCESS_DEF);
            if (workflowProcessOpt.isEmpty()) {
                throw new UndefinedProcessException("undefined process " + ARTICLE_PROCESS_DEF);
            }
            WorkflowProcessModel workflowProcessModel = workflowProcessOpt.isPresent() ? workflowProcessOpt.get() : null;

            logger.info("find started state");
            WorkflowStateModel initState = workflowStateRepository.findStateByStartTypeName();
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

            WorkflowRequestUserTaskModel requestUserTaskModel = new WorkflowRequestUserTaskModel();
            requestUserTaskModel.setCreatedBy(username);
            requestUserTaskModel.setRequestModel(requestModel);
            requestUserTaskModel.setProposedBy(username);
            requestUserTaskModel.setAssigne(username); // assign to self pic
            Optional<WorkflowPersonStateModel> receiverStateOpt = workflowPersonStateRepository.findById("DRAFT");
            requestUserTaskModel.setReceiverState(receiverStateOpt.isPresent()? receiverStateOpt.get() : null);

            workflowRequestUserTaskRepository.save(requestUserTaskModel);

            TaskDto taskDto = new TaskDto();
            taskDto.setCurrentState(initState.getCode());
            taskDto.setArticleId((Long) variables.get("article_id"));
            taskDto.setRequestId(requestModel.getId());
            taskDto.setSender(requestUserTaskModel.getProposedBy());
            taskDto.setCurrentSenderState(requestUserTaskModel.getSenderState().getCode());
            taskDto.setAssigne(requestUserTaskModel.getAssigne());
            taskDto.setCurrentReceiverState(requestUserTaskModel.getReceiverState().getCode());

            return taskDto;
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
            variables.put("sendNote", map.get("sendNote"));
            Optional<WorkflowProcessModel> workflowProcessOpt = workflowProcessRepository.findById(ARTICLE_PROCESS_DEF);
            if (workflowProcessOpt.isEmpty()) {
                throw new UndefinedProcessException("undefined process " + ARTICLE_PROCESS_DEF);
            }

            logger.debug("find workflow request by article id {}", variables.get("article_id"));
            WorkflowRequestUserTaskModel currentWfRequestUt = workflowRequestUserTaskRepository.findWorkflowRequestUserTask("" + (Long) variables.get("article_id"), username);

            WorkflowRequestModel currentWfRequest = currentWfRequestUt.getRequestModel();
            logger.debug("current state is {}", currentWfRequest.getCurrentState().getCode());

            logger.debug("find workflow state by current state ");
            WorkflowStateModel currentState = workflowStateRepository.findStateByName(currentWfRequest.getCurrentState().getCode(), ARTICLE_PROCESS_DEF);
            logger.debug("current state is {}", currentState.getCode());

            WorkflowTransitionUserTaskModel wfTransUtask = workflowTransitionUserTaskRepository.findTransitionByCurrentStateAndActionType(currentState.getCode()
                    , new Helper().convertTaskTypeToNumeric((String) map.get("taskType")));
            WorkflowStateModel nextState = wfTransUtask.getTransition().getNextState();
            logger.debug("next state transition is {}", nextState.getCode());

            logger.debug("update current workflow request user task");
            currentWfRequestUt.setModifyDate(new Date());
            currentWfRequestUt.setApprovedDate(new Date());
            currentWfRequestUt = workflowRequestUserTaskRepository.save(currentWfRequestUt);

            logger.debug("set current state of current workflow request to new state {}", nextState.getCode());
            currentWfRequest.setCurrentState(nextState);
            currentWfRequest.setModifyBy(username);
            currentWfRequest.setModifyDate(new Date());

            logger.debug("save request to db");
            currentWfRequest = workflowRequestRepository.save(currentWfRequest);

            logger.debug("task type from user {}", map.get("taskType"));
            Map assignDto = (Map) map.get("sendTo");

            logger.debug("save new workflow request user task assigned to {}", assignDto);
            WorkflowRequestUserTaskModel newRequestUserTaskModel = new WorkflowRequestUserTaskModel();
            newRequestUserTaskModel.setCreatedBy(username);
            newRequestUserTaskModel.setRequestModel(currentWfRequest);
            newRequestUserTaskModel.setProposedBy(username);
            newRequestUserTaskModel.setAssigne((String) assignDto.get("username"));
            newRequestUserTaskModel.setNote((String)variables.get("sendNote"));
            Optional<WorkflowPersonStateModel> receiverStateOpt = workflowPersonStateRepository.findById("DRAFT");
            newRequestUserTaskModel.setReceiverState(receiverStateOpt.isPresent()? receiverStateOpt.get() : null);
            Optional<WorkflowPersonStateModel> senderStateOpt = workflowPersonStateRepository.findById("PENDING");
            newRequestUserTaskModel.setSenderState(senderStateOpt.isPresent()? senderStateOpt.get() : null);
            newRequestUserTaskModel = workflowRequestUserTaskRepository.save(newRequestUserTaskModel);

            TaskDto taskDto = new TaskDto();
            taskDto.setCurrentState(nextState.getCode());
            taskDto.setArticleId((Long) variables.get("article_id"));
            taskDto.setRequestId(currentWfRequest.getId());
            taskDto.setAssigne((String) assignDto.get("username"));
            taskDto.setSender(newRequestUserTaskModel.getProposedBy());
            taskDto.setCurrentSenderState(newRequestUserTaskModel.getSenderState().getCode());
            taskDto.setAssigne(newRequestUserTaskModel.getAssigne());
            taskDto.setCurrentReceiverState(newRequestUserTaskModel.getReceiverState().getCode());
            return taskDto;
        } catch (UndefinedProcessException e) {
            logger.error("exception", e);
            throw new UndefinedProcessException("process not found");
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception(e);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
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

    /**
     *
     * @param pic
     * @param state
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class})
    public List<TaskDto> getTasksWithPicState(String pic, String state) throws Exception {
        logger.debug("get all task for pic {}", pic);
        try {
            Iterable<WorkflowRequestUserTaskModel> tasks = workflowRequestUserTaskRepository.findByPicAndState(pic, state);
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
                dto.setSender(model.getProposedBy());
                dto.setCurrentState(model.getRequestModel().getCurrentState().getCode());
                dto.setRequestId(model.getRequestModel().getId());
                dto.setCurrentSenderState(model.getSenderState() != null ? model.getSenderState().getCode() : "");
                dto.setCurrentReceiverState(model.getReceiverState() != null ? model.getReceiverState().getCode() : "");
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

    private class Helper {
        Long convertTaskTypeToNumeric(String type) {
            if (type.equalsIgnoreCase("Approve"))
                return 1L;
            else if (type.equalsIgnoreCase("Deny")) {
                return 2L;
            } else if (type.equalsIgnoreCase("Cancel")) {
                return 3L;
            } else {
                return 0L; // undefined
            }
        }
    }
}
