package id.co.bca.pakar.be.wf.service;

import id.co.bca.pakar.be.wf.dao.*;
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
    private WorkflowUserTaskAssignmentRepository workflowUserTaskAssignmentRepository;

    @Autowired
    private WorkflowStateRepository workflowStateRepository;

    @Autowired
    private WorkflowUserTaskRepository workflowUserTaskRepository;

    @Autowired
    private WorkflowRequestUserTaskRepository workflowRequestUserTaskRepository;

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
            workflowRequestRepository.save(requestModel);

            Optional<WorkflowUserTaskModel> userTaskOpt = workflowUserTaskRepository.findById(USER_TASK_DEFAULT_DEF);
            if (userTaskOpt.isEmpty()) {
                throw new UndefinedUserTaskException("undefined user task");
            }

            WorkflowUserTaskModel userTaskModel = userTaskOpt.get();

            logger.info("populate user assignment ");
            WorkflowUserTaskAssignmentModel workflowUserTaskAssignmentModel = new WorkflowUserTaskAssignmentModel();
            workflowUserTaskAssignmentModel.setAssigne(username); // assign to self pic
            workflowUserTaskAssignmentModel.setCreatedBy(username);
            workflowUserTaskAssignmentModel.setProposedBy(username);
            workflowUserTaskAssignmentModel.setUserTaskModel(userTaskModel);
            logger.info("save user task assignment");
            workflowUserTaskAssignmentRepository.save(workflowUserTaskAssignmentModel);

            WorkflowRequestUserTaskModel requestUserTaskModel = new WorkflowRequestUserTaskModel();
            requestUserTaskModel.setCreatedBy(username);
            requestUserTaskModel.setUserTaskModel(userTaskModel);
            requestUserTaskModel.setRequestModel(requestModel);
            workflowRequestUserTaskRepository.save(requestUserTaskModel);

            TaskDto taskDto = new TaskDto();
            taskDto.setCurrentState(initState.getCode());
            taskDto.setArticleId((Long)variables.get("article_id"));
            taskDto.setProcessId(requestModel.getId());
            taskDto.setAssigne(workflowUserTaskAssignmentModel.getAssigne());

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

    @Transactional
    public List<TaskDto> getTasks(String assignee) throws Exception {
        logger.debug("get all task for assigne {}", assignee);
        try {
            Iterable<WorkflowUserTaskAssignmentModel> tasks = workflowUserTaskAssignmentRepository.findByAssigne(assignee);
            List<TaskDto> dtos = new MapperHelper().mapToTaskDtos(tasks);
            return dtos;
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("exception", e);
        }
    }
//
//    @Transactional
//    public void submitReview(Approval approval) {
//        Map<String, Object> variables = new HashMap<String, Object>();
//        variables.put("approved", approval.isStatus());
//        taskService.complete(approval.getId(), variables);
//    }

    private class MapperHelper {
        public List<TaskDto> mapToTaskDtos(Iterable<WorkflowUserTaskAssignmentModel> iterable) {
            List<TaskDto> dtos = new ArrayList<>();
            for (WorkflowUserTaskAssignmentModel model : iterable) {
                TaskDto dto = new TaskDto();
                dto.setAssigne(model.getAssigne());
                Long userTask = model.getUserTaskModel().getId();
                dto.setCurrentState("DRAFT");
                dtos.add(dto);
            }
            return dtos;
        }
    }
}
