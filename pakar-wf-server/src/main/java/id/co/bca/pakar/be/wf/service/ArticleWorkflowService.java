package id.co.bca.pakar.be.wf.service;

import id.co.bca.pakar.be.wf.common.Constant;
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

import static id.co.bca.pakar.be.wf.common.Constant.ArticleWfState.*;
import static id.co.bca.pakar.be.wf.common.Constant.Workflow.*;

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

    @Autowired
    private WorkflowGroupTransitionRepository workflowGroupTransitionRepository;

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
            Optional<WorkflowStateModel> receiverStateOpt = workflowStateRepository.findById(DRAFT);
            requestUserTaskModel.setReceiverState(receiverStateOpt.isPresent() ? receiverStateOpt.get() : null);

            workflowRequestUserTaskRepository.save(requestUserTaskModel);

            TaskDto taskDto = new TaskDto();
            taskDto.setCurrentState(initState.getCode());
            taskDto.setArticleId((Long) variables.get("article_id"));
            taskDto.setRequestId(requestModel.getId());
            taskDto.setSender(requestUserTaskModel.getProposedBy());
            taskDto.setCurrentSenderState(requestUserTaskModel.getSenderState() != null ?
                    requestUserTaskModel.getSenderState().getCode() : null);
            taskDto.setAssigne(requestUserTaskModel.getAssigne());
            taskDto.setCurrentReceiverState(requestUserTaskModel.getReceiverState() != null ? requestUserTaskModel.getReceiverState().getCode() : "");

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
    public TaskDto startProcess(String username, String token, Map<String, Object> map) throws Exception {
        try {
            logger.debug("start process workflow {} and param received is {}", map.get(PROCESS_KEY), map);
            Map<String, Object> variables = new HashMap<>();
            variables.put(GROUP_PARAM, map.get(GROUP_PARAM));
            variables.put(ARTICLE_ID_PARAM, map.get(ARTICLE_ID_PARAM));
            variables.put(TITLE_PARAM, map.get(TITLE_PARAM));
            String processKey = (String) map.get(PROCESS_KEY);

            logger.debug("get registered process in system {}", processKey);
            Optional<WorkflowProcessModel> workflowProcessOpt = workflowProcessRepository.findById(processKey);
            if (workflowProcessOpt.isEmpty()) {
                throw new UndefinedProcessException("undefined process " + processKey);
            }

            WorkflowProcessModel workflowProcessModel = workflowProcessOpt.isPresent() ? workflowProcessOpt.get() : null;

            logger.info("find registered started state process {}", processKey);
            WorkflowStateModel initState = workflowStateRepository.findStateByStartTypeName(processKey);
            if (initState == null) {
                throw new UndefinedStartedStateException(processKey + "not have registered start state");
            }

            logger.info("create workflow request");
            WorkflowRequestModel requestModel = new WorkflowRequestModel();
            requestModel.setCreatedBy(username);
            Date requestDate = new Date();
            requestModel.setCreatedDate(requestDate);
            requestModel.setRequestDate(requestDate);
            requestModel.setTitle((String) variables.get(TITLE_PARAM));
            requestModel.setUserid(username);
            requestModel.setCurrentState(initState);
            requestModel.setWfprocess(workflowProcessModel);

            logger.info("save request flow");
            requestModel = workflowRequestRepository.save(requestModel);

            WorkflowRequestDataModel requestDataModel = new WorkflowRequestDataModel();
            requestDataModel.setCreatedBy(username);
            requestDataModel.setWfRequest(requestModel);
            requestDataModel.setName(ARTICLE_ID);
            requestDataModel.setValue("" + variables.get(ARTICLE_ID_PARAM));
            requestDataModel = workflowRequestDataRepository.save(requestDataModel);

            /*
             * assign to sender her self, than if sender self can track task with api tasks
             */
            logger.debug("assign to sender self");
            WorkflowRequestUserTaskModel requestUserTaskModel = new WorkflowRequestUserTaskModel();
            requestUserTaskModel.setCreatedBy(username);
            requestUserTaskModel.setRequestModel(requestModel);
            requestUserTaskModel.setProposedBy(username);
            requestUserTaskModel.setAssigne(username); // assign to self pic
            Optional<WorkflowStateModel> receiverStateOpt = workflowStateRepository.findById(DRAFT);
            requestUserTaskModel.setReceiverState(receiverStateOpt.isPresent() ? receiverStateOpt.get() : null);

            logger.info("save request user task model");
            workflowRequestUserTaskRepository.save(requestUserTaskModel);

            /*
            populate response dto
             */
            TaskDto taskDto = new TaskDto();
            taskDto.setCurrentState(initState.getCode());
            String articleId = (new StringBuffer().append(variables.get(ARTICLE_ID_PARAM))).toString();
            taskDto.setArticleId(Long.parseLong(articleId));
            taskDto.setRequestId(requestModel.getId());
            taskDto.setSender(requestUserTaskModel.getProposedBy());
            taskDto.setCurrentSenderState(requestUserTaskModel.getSenderState() != null ?
                    requestUserTaskModel.getSenderState().getCode() : null);
            taskDto.setAssigne(requestUserTaskModel.getAssigne());
            taskDto.setCurrentReceiverState(requestUserTaskModel.getReceiverState() != null ? requestUserTaskModel.getReceiverState().getCode() : "");

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
            newRequestUserTaskModel.setNote((String) variables.get("sendNote"));
            Optional<WorkflowStateModel> receiverStateOpt = workflowStateRepository.findById("DRAFT");
            newRequestUserTaskModel.setReceiverState(receiverStateOpt.isPresent() ? receiverStateOpt.get() : null);
            Optional<WorkflowStateModel> senderStateOpt = workflowStateRepository.findById("PENDING");
            newRequestUserTaskModel.setSenderState(senderStateOpt.isPresent() ? senderStateOpt.get() : null);
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

    /**
     * @param username
     * @param token
     * @param body
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class,
            UndefinedProcessException.class,
            UndefinedStartedStateException.class})
    public TaskDto completeTask(String username, String token, Map<String, Object> body) throws Exception {
        try {
            logger.debug("completing task for user {} and for article {}", username, body.get(ARTICLE_ID_PARAM));
            String receiverGroup = (String) body.get(GROUP_PARAM);
            String processKey = (String) body.get(PROCESS_KEY);
            String wfReqId = (String) body.get(WORKFLOW_REQ_ID_PARAM);

            Optional<WorkflowProcessModel> workflowProcessOpt = workflowProcessRepository.findById(processKey);
            if (workflowProcessOpt.isEmpty()) {
                throw new UndefinedProcessException("undefined process " + processKey);
            }

            /*
            get assigne user task for certain article id
             */
            logger.debug("find workflow request by article id {} and workflow request id {}, current receiver {}", new Object[] {body.get(ARTICLE_ID_PARAM), wfReqId, username});
            WorkflowRequestUserTaskModel currentWfRequestUt = workflowRequestUserTaskRepository
                    .findWorkflowRequestUserTask(new StringBuffer().append(body.get(ARTICLE_ID_PARAM)).toString(), username, processKey, wfReqId);

            /*
            get current workflow state of request
             */
            if(currentWfRequestUt == null) {
                logger.info("not found task workflow request {} for receiver {}", wfReqId, username);
            }
            WorkflowRequestModel currentWfRequest = currentWfRequestUt.getRequestModel();
            logger.debug("current state is {}", currentWfRequest.getCurrentState().getCode());

            logger.debug("find workflow state by current state ");
            WorkflowStateModel currentState = workflowStateRepository
                    .findStateByName(currentWfRequest.getCurrentState().getCode(), processKey);
            logger.debug("current state is {}", currentState.getCode());

            /*
            get workflow group transition
             */
            logger.debug("find workflow group transition using receiver group {} and current state code {}", receiverGroup, currentState.getCode());
            WorkflowGroupTransitionModel wfGroupTrans = workflowGroupTransitionRepository
                    .findByReceiverGroup(receiverGroup, currentState.getCode());
            logger.debug("transition id of receiver group {} and state {} is {}", new Object[]{receiverGroup, currentState.getCode(), wfGroupTrans.getWorkflowTransitionModel().getId()});

            logger.info("get next transition state from start state {} of receiver group {}", currentState.getCode(), receiverGroup);
            WorkflowTransitionUserTaskModel wfTransUtask = workflowTransitionUserTaskRepository
                    .findTransitionByCurrentStateAndActionType(currentState.getCode()
                            , new Helper().convertTaskTypeToNumeric((String) body.get(TASK_TYPE_PARAM))
                            , wfGroupTrans.getWorkflowTransitionModel().getId());
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

            Map assignDto = (Map) body.get(SEND_TO_PARAM);
            logger.info("create new task for other user {}", assignDto);
            WorkflowRequestUserTaskModel newRequestUserTaskModel = new WorkflowRequestUserTaskModel();
            newRequestUserTaskModel.setCreatedBy(username);
            newRequestUserTaskModel.setRequestModel(currentWfRequest);
            newRequestUserTaskModel.setProposedBy(username);
            newRequestUserTaskModel.setAssigne((String) assignDto.get(RECEIVER_PARAM));
            newRequestUserTaskModel.setNote((String) body.get(SEND_NOTE_PARAM));
            Optional<WorkflowStateModel> receiverStateOpt = workflowStateRepository.findById(nextState.getCode());
            newRequestUserTaskModel.setReceiverState(receiverStateOpt.isPresent() ? receiverStateOpt.get() : null);
            Optional<WorkflowStateModel> senderStateOpt = workflowStateRepository.findById(Constant.ArticleWfState.PENDING);
            newRequestUserTaskModel.setSenderState(senderStateOpt.isPresent() ? senderStateOpt.get() : null);
            newRequestUserTaskModel = workflowRequestUserTaskRepository.save(newRequestUserTaskModel);

            /*
            populate response task dto
             */
            TaskDto taskDto = new TaskDto();
            taskDto.setCurrentState(nextState.getCode());
            String articleId_ = ""+body.get(ARTICLE_ID_PARAM);
            taskDto.setArticleId(Long.parseLong(articleId_));
            taskDto.setRequestId(currentWfRequest.getId());
            taskDto.setAssigne((String) assignDto.get(RECEIVER_PARAM));
            taskDto.setSender(newRequestUserTaskModel.getProposedBy());
            taskDto.setCurrentSenderState(newRequestUserTaskModel.getSenderState().getCode());
            taskDto.setAssigne(newRequestUserTaskModel.getAssigne());
            taskDto.setCurrentReceiverState(newRequestUserTaskModel.getReceiverState().getCode());

            logger.info("response task to client {}", taskDto.toString());
            return taskDto;
        } catch (UndefinedProcessException e) {
            logger.error("exception", e);
            throw new UndefinedProcessException("process not found");
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception(e);
        }
    }

    @Transactional(rollbackFor = {Exception.class,
            UndefinedProcessException.class,
            UndefinedStartedStateException.class})
    public TaskDto cancelTask(String username, String token, Map<String, Object> body) throws Exception {
        try {
            logger.debug("cancel task for user {} and for article {}", username, body.get(ARTICLE_ID_PARAM));
            Map assignTo = (Map) body.get(SEND_TO_PARAM);
            String receiver = (String) assignTo.get(USERNAME_PARAM);
            String processKey = (String) body.get(PROCESS_KEY);
            String wfReqId = (String) body.get(WORKFLOW_REQ_ID_PARAM);
            Long articleId = Long.parseLong((String)body.get(ARTICLE_ID_PARAM));

            Optional<WorkflowProcessModel> workflowProcessOpt = workflowProcessRepository.findById(processKey);
            if (workflowProcessOpt.isEmpty()) {
                throw new UndefinedProcessException("undefined process " + processKey);
            }

            /*
            get assigne user task for certain article id
             */
            logger.debug("find workflow request by article id {} and workflow request id {}, current receiver {}", new Object[] {articleId, wfReqId, receiver});
            WorkflowRequestUserTaskModel currentWfRequestUt = workflowRequestUserTaskRepository
                    .findWorkflowRequestUserTask(new StringBuffer().append(body.get(ARTICLE_ID_PARAM)).toString(), username, processKey, wfReqId);

            /*
            get current workflow state of request
             */
            if(currentWfRequestUt == null) {
                logger.info("not found task workflow request {} for receiver {}", wfReqId, username);
            }
            WorkflowRequestModel currentWfRequest = currentWfRequestUt.getRequestModel();
            logger.debug("current state is {}", currentWfRequest.getCurrentState().getCode());

            logger.debug("find workflow state by current state ");
            WorkflowStateModel currentState = workflowStateRepository
                    .findStateByName(currentWfRequest.getCurrentState().getCode(), processKey);
            logger.debug("current state is {}", currentState.getCode());

            Optional<WorkflowStateModel> nextStateOpt = workflowStateRepository.findById(DRAFT);
            WorkflowStateModel nextState = (nextStateOpt.isPresent() ? nextStateOpt.get() : null);
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

            Map assignDto = (Map) body.get(SEND_TO_PARAM);
            logger.info("create new task for other user {}", assignDto);
            WorkflowRequestUserTaskModel newRequestUserTaskModel = new WorkflowRequestUserTaskModel();
            newRequestUserTaskModel.setCreatedBy(username);
            newRequestUserTaskModel.setRequestModel(currentWfRequest);
            newRequestUserTaskModel.setProposedBy(username);
            newRequestUserTaskModel.setAssigne((String) assignDto.get(RECEIVER_PARAM));
            newRequestUserTaskModel.setNote((String) body.get(SEND_NOTE_PARAM));
            Optional<WorkflowStateModel> receiverStateOpt = workflowStateRepository.findById(nextState.getCode());
            newRequestUserTaskModel.setReceiverState(receiverStateOpt.isPresent() ? receiverStateOpt.get() : null);
            Optional<WorkflowStateModel> senderStateOpt = workflowStateRepository.findById(Constant.ArticleWfState.PENDING);
            newRequestUserTaskModel.setSenderState(senderStateOpt.isPresent() ? senderStateOpt.get() : null);
            newRequestUserTaskModel = workflowRequestUserTaskRepository.save(newRequestUserTaskModel);

            /*
            populate response task dto
             */
            TaskDto taskDto = new TaskDto();
            taskDto.setCurrentState(nextState.getCode());
            String articleId_ = ""+body.get(ARTICLE_ID_PARAM);
            taskDto.setArticleId(Long.parseLong(articleId_));
            taskDto.setRequestId(currentWfRequest.getId());
            taskDto.setAssigne((String) assignDto.get(RECEIVER_PARAM));
            taskDto.setSender(newRequestUserTaskModel.getProposedBy());
            taskDto.setCurrentSenderState(newRequestUserTaskModel.getSenderState().getCode());
            taskDto.setAssigne(newRequestUserTaskModel.getAssigne());
            taskDto.setCurrentReceiverState(newRequestUserTaskModel.getReceiverState().getCode());

            logger.info("response task to client {}", taskDto.toString());
            return taskDto;
        } catch (UndefinedProcessException e) {
            logger.error("exception", e);
            throw new UndefinedProcessException("process not found");
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception(e);
        }
    }

    /**
     * @param assignee
     * @return
     * @throws Exception
     */
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
     * @param pic
     * @param state
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class})
    public List<TaskDto> getTasksWithPicState(String pic, String state, String wfProcess) throws Exception {
        logger.debug("get all task for pic {}", pic);
        try {
            Iterable<WorkflowRequestUserTaskModel> tasks = workflowRequestUserTaskRepository
                    .findByPicAndState(pic, state, wfProcess);
            List<TaskDto> dtos = new MapperHelper().mapToTaskDtos(tasks);
            return dtos;
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("exception", e);
        }
    }

    /**
     * @param sender
     * @param body
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class})
    public TaskDto requestDelete(String sender, Map<String, Object> body) throws Exception {
        logger.debug("get sender -> {}", sender);
        logger.debug("get body -> {}", body);

        try {
            Map<String, Object> variables = new HashMap<>();
            variables.put(PROCESS_KEY, body.get(PROCESS_KEY));
            variables.put(TITLE_PARAM, body.get(TITLE_PARAM));
            variables.put(ARTICLE_ID_PARAM, body.get(ARTICLE_ID_PARAM));
            variables.put(SENDER_PARAM, body.get(SENDER_PARAM));
            variables.put(SEND_TO_PARAM, body.get(SEND_TO_PARAM));
            variables.put(SEND_NOTE_PARAM, body.get(SEND_NOTE_PARAM));
            variables.put(GROUP_PARAM, body.get(GROUP_PARAM));

            String processKey = (String) body.get(PROCESS_KEY);

            logger.debug("get registered process in system {}", processKey);
            Optional<WorkflowProcessModel> workflowProcessOpt = workflowProcessRepository.findById(processKey);
            if (workflowProcessOpt.isEmpty()) {
                throw new UndefinedProcessException("undefined process " + processKey);
            }
            WorkflowProcessModel workflowProcessModel = workflowProcessOpt.isPresent() ? workflowProcessOpt.get() : null;

            logger.info("find registered started state process {}", processKey);
            WorkflowStateModel initState = workflowStateRepository.findStateByStartTypeName(processKey);
            if (initState == null) {
                throw new UndefinedStartedStateException(processKey + "not have registered start state");
            }

            WorkflowRequestModel requestModel = new WorkflowRequestModel();
            requestModel.setCreatedBy(sender);
            Date requestDate = new Date();
            requestModel.setCreatedDate(requestDate);
            requestModel.setRequestDate(requestDate);
            requestModel.setTitle((String) variables.get(TITLE_PARAM));
            requestModel.setUserid(sender);
            requestModel.setCurrentState(initState);
            requestModel.setWfprocess(workflowProcessModel);

            logger.info("save request flow");
            requestModel = workflowRequestRepository.save(requestModel);

            WorkflowRequestDataModel requestDataModel = new WorkflowRequestDataModel();
            requestDataModel.setCreatedBy(sender);
            requestDataModel.setWfRequest(requestModel);
            requestDataModel.setName(ARTICLE_ID);
            requestDataModel.setValue("" + variables.get(ARTICLE_ID_PARAM));
            requestDataModel = workflowRequestDataRepository.save(requestDataModel);

            /*
             * assign to send_To
             */
            logger.debug("assign to receiver");
            WorkflowRequestUserTaskModel requestUserTaskModel = new WorkflowRequestUserTaskModel();
            requestUserTaskModel.setCreatedBy(sender);
            requestUserTaskModel.setRequestModel(requestModel);
            requestUserTaskModel.setProposedBy(sender);
            requestUserTaskModel.setAssigne(variables.get(SEND_TO_PARAM).toString()); // assign to receiver
            Optional<WorkflowStateModel> receiverStateOpt = workflowStateRepository.findById(DRAFT_DELETED);
            requestUserTaskModel.setReceiverState(receiverStateOpt.isPresent() ? receiverStateOpt.get() : null);
            Optional<WorkflowStateModel> senderStateOpt = workflowStateRepository.findById(PENDING_DELETED);
            requestUserTaskModel.setSenderState(senderStateOpt.isPresent() ? senderStateOpt.get() : null);

            logger.info("save request user task model");
            requestUserTaskModel = workflowRequestUserTaskRepository.save(requestUserTaskModel);

            /*
            populate response dto
             */
            TaskDto taskDto = new TaskDto();
            taskDto.setCurrentState(initState.getCode());
            String articleId = (new StringBuffer().append(variables.get(ARTICLE_ID_PARAM))).toString();
            taskDto.setArticleId(Long.parseLong(articleId));
            taskDto.setRequestId(requestModel.getId());
            taskDto.setSender(requestUserTaskModel.getProposedBy());
            taskDto.setCurrentSenderState(requestUserTaskModel.getSenderState() != null ?
                    requestUserTaskModel.getSenderState().getCode() : null);
            taskDto.setAssigne(requestUserTaskModel.getAssigne());
            taskDto.setCurrentReceiverState(requestUserTaskModel.getReceiverState() != null ? requestUserTaskModel.getReceiverState().getCode() : "");
            logger.debug("task Dto from workflow delete = "+ taskDto);

            return taskDto;
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("exception", e);
        }
    }

    /**
     *
     */
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
                logger.debug("worflow request id {}", model.getRequestModel().getId());
                logger.debug("current state {}", model.getRequestModel().getCurrentState().getCode());
                Optional<WorkflowRequestDataModel> requestDataModelOpt = workflowRequestDataRepository.findByNameAndRequestId(ARTICLE_ID, model.getRequestModel().getId());
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
            if (type.equalsIgnoreCase("APPROVE"))
                return 1L;
            else if (type.equalsIgnoreCase("DENY")) {
                return 2L;
            } else if (type.equalsIgnoreCase("CANCEL")) {
                return 3L;
            } else {
                return 0L; // undefined
            }
        }
    }
}
