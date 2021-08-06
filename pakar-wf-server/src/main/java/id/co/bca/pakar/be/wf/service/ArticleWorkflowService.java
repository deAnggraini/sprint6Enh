package id.co.bca.pakar.be.wf.service;

import id.co.bca.pakar.be.wf.dao.WorkflowActionTargetDao;
import id.co.bca.pakar.be.wf.dao.WorkflowProcessDao;
import id.co.bca.pakar.be.wf.dao.WorkflowRequestDao;
import id.co.bca.pakar.be.wf.dto.TaskDto;
import id.co.bca.pakar.be.wf.exception.UndefinedProcessException;
import id.co.bca.pakar.be.wf.model.WorkflowActionTargetModel;
import id.co.bca.pakar.be.wf.model.WorkflowProcessModel;
import id.co.bca.pakar.be.wf.model.WorkflowRequestModel;
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
    private WorkflowProcessDao workflowProcessDao;

    @Autowired
    private WorkflowRequestDao workflowRequestDao;

    @Autowired
    private WorkflowActionTargetDao workflowActionTargetDao;

    /**
     * start process workflow article
     * @param username
     * @param map
     */
    @Transactional
    public void startProcess(String username, Map map) {
        try {
            logger.debug("start process workflow");
            Map<String, Object> variables = new HashMap<>();
            variables.put("sender", map.get("sender"));
            variables.put("receiver", map.get("receiver"));
            variables.put("article_id", map.get("article_id"));
            variables.put("title", map.get("judul_article"));
            Optional<WorkflowProcessModel> workflowProcessOpt = workflowProcessDao.findById(ARTICLE_PROCESS_DEF);
//            runtimeService.startProcessInstanceByKey("articleReview", variables);
            if(workflowProcessOpt.isEmpty()) {
                throw new UndefinedProcessException("undefined process "+ARTICLE_PROCESS_DEF);
            }
            WorkflowProcessModel workflowProcessModel = workflowProcessOpt.isPresent() ? workflowProcessOpt.get() : null;

            WorkflowRequestModel requestModel = new WorkflowRequestModel();
            requestModel.setCreatedBy(username);
            Date requestDate = new Date();
            requestModel.setCreatedDate(requestDate);
            requestModel.setRequestDate(requestDate);
            requestModel.setTitle((String)variables.get("title"));
            requestModel.setUserid(username);
            requestModel.setWfprocess(workflowProcessModel);

            logger.info("save request flow");
            workflowRequestDao.save(requestModel);

            logger.info("populate action target");
            WorkflowActionTargetModel workflowActionTargetModel = new WorkflowActionTargetModel();
            workflowActionTargetModel.setAssigne(username);
            workflowActionTargetModel.setCreatedBy(username);
            workflowActionTargetModel.setProposedBy(username);
            logger.info("save action target");
            workflowActionTargetDao.save(workflowActionTargetModel);

        } catch (Exception e) {
            logger.error("exception", e);
        }
    }

    @Transactional
    public List<TaskDto> getTasks(String assignee) {
//        List<Task> tasks = taskService.createTaskQuery()
//                .taskCandidateGroup(assignee)
//                .list();
//        return tasks.stream()
//                .map(task -> {
//                    Map<String, Object> variables = taskService.getVariables(task.getId());
//                    return new Article(task.getId(), (String) variables.get("author"), (String) variables.get("url"));
//                })
//                .collect(Collectors.toList());
        return null;
    }
//
//    @Transactional
//    public void submitReview(Approval approval) {
//        Map<String, Object> variables = new HashMap<String, Object>();
//        variables.put("approved", approval.isStatus());
//        taskService.complete(approval.getId(), variables);
//    }
}
