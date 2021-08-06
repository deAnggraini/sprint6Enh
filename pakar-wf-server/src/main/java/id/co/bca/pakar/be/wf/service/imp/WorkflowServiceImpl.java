package id.co.bca.pakar.be.wf.service.imp;

import id.co.bca.pakar.be.wf.dao.*;
import id.co.bca.pakar.be.wf.model.*;
import id.co.bca.pakar.be.wf.service.WorkflowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class WorkflowServiceImpl implements WorkflowService {
    private Logger logger = LoggerFactory.getLogger(WorkflowServiceImpl.class);

//    @Autowired
//    private WorkflowLifeDao workflowLifeDao;
//    @Autowired
//    private WorkflowStateTransitionDao workflowStateTransitionDao;
//    @Autowired
//    private WorkflowTransitionDao workflowTransitionDao;
//    @Autowired
//    private WorkflowTransitionDetailDao workflowTransitionDetailDao;
//    @Autowired
//    private WorkflowUserStateDao workflowUserStateDao;
//
//    @Override
//    @Transactional(rollbackFor = {Exception.class})
//    public Object initialize(String workflowCode, String userId) throws Exception {
//        try {
//            WorkflowLifeModel workflowLifeModel = workflowLifeDao.getWorkflowLifeByWorkflowCode(workflowCode);
//            String startState = workflowLifeModel.getStartState();
//
//            WorkflowStateTransitionModel workflowStateTransitionModel = workflowStateTransitionDao.
//                    getWorkflowStateTransitionModelFromStartState(startState);
//
//            WorkflowTransitionModel transitionModel = new WorkflowTransitionModel();
//            transitionModel.setWorkflowStateTransitionModel(workflowStateTransitionModel);
//            transitionModel.setCreatedDate(new Date());
//            transitionModel.setCreatedBy(userId);
//            transitionModel = workflowTransitionDao.save(transitionModel);
//
//            WorkflowTransitionDetailModel transitionDetail = new WorkflowTransitionDetailModel();
//            transitionDetail.setWorkflowTransitionModel(transitionModel);
//            transitionDetail.setProposeBy(userId);
//            transitionDetail.setProposeDate(new Date());
//            transitionDetail.setCreatedBy(userId);
//            transitionDetail.setCreatedDate(new Date());
//            transitionDetail.setNote("");
//
//            workflowTransitionDetailDao.save(transitionDetail);
//
//            return transitionModel;
//        } catch (Exception e) {
//            throw new Exception("exception",e);
//        }
//    }
//
//    @Override
//    @Transactional(rollbackFor = {Exception.class})
//    public Object nextState(Map map) throws Exception {
//        try {
//            Long pid = (Long) map.get("pid");
//            Optional<WorkflowTransitionModel> currentTransitionOpt = workflowTransitionDao.findById(pid);
//            WorkflowTransitionModel currentTransition = currentTransitionOpt.isPresent() ? currentTransitionOpt.get() : null;
//            WorkflowStateTransitionModel currentStateTransition = currentTransition.getWorkflowStateTransitionModel();
//            WorkflowStateModel currentNextState = currentStateTransition.getNextState();
//
//            WorkflowStateTransitionModel workflowStateTransitionModel = workflowStateTransitionDao.
//                    getWorkflowStateTransitionModelFromStartState(currentNextState.getCode());
//
//            String userId = (String) map.get("userid");
//            currentTransition.setWorkflowStateTransitionModel(workflowStateTransitionModel);
//            currentTransition.setModifyBy(userId);
//            currentTransition.setModifyDate(new Date());
//            workflowTransitionDao.save(currentTransition);
//
//            String comment = (String) map.get("comment");
//            WorkflowTransitionDetailModel transitionDetail = new WorkflowTransitionDetailModel();
//            transitionDetail.setWorkflowTransitionModel(currentTransition);
//            transitionDetail.setProposeBy(userId);
//            transitionDetail.setProposeDate(new Date());
//            transitionDetail.setCreatedBy(userId);
//            transitionDetail.setCreatedDate(new Date());
//            transitionDetail.setNote(comment);
//            workflowTransitionDetailDao.save(transitionDetail);
//
//            return currentTransition;
//        } catch (Exception e) {
//            logger.error("exception",e);
//            throw new Exception("general exception",e);
//        }
//    }
//
//    @Override
//    @Transactional(rollbackFor = {Exception.class})
//    public Object backState(Map map) throws Exception {
//        try {
//            Long pid = (Long) map.get("pid");
//            Optional<WorkflowTransitionModel> currentTransitionOpt = workflowTransitionDao.findById(pid);
//            WorkflowTransitionModel currentTransition = currentTransitionOpt.isPresent() ? currentTransitionOpt.get() : null;
//            WorkflowStateTransitionModel currentStateTransition = currentTransition.getWorkflowStateTransitionModel();
//            WorkflowStateModel currentStartState = currentStateTransition.getStartState();
//
//            WorkflowStateTransitionModel workflowStateTransitionModel = workflowStateTransitionDao.
//                    getWorkflowStateTransitionModelFromNextState(currentStartState.getCode());
//
//            String userId = (String) map.get("userid");
//            currentTransition.setWorkflowStateTransitionModel(workflowStateTransitionModel);
//            currentTransition.setModifyBy(userId);
//            currentTransition.setModifyDate(new Date());
//            workflowTransitionDao.save(currentTransition);
//
//            String comment = (String) map.get("comment");
//            WorkflowTransitionDetailModel transitionDetail = new WorkflowTransitionDetailModel();
//            transitionDetail.setWorkflowTransitionModel(currentTransition);
//            transitionDetail.setProposeBy(userId);
//            transitionDetail.setProposeDate(new Date());
//            transitionDetail.setCreatedBy(userId);
//            transitionDetail.setCreatedDate(new Date());
//            transitionDetail.setNote(comment);
//            workflowTransitionDetailDao.save(transitionDetail);
//
//            return currentTransition;
//        } catch (Exception e) {
//            logger.error("exception",e);
//            throw new Exception("exception",e);
//        }
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<Long> listOfUserState(String userId) throws Exception {
//        Iterable<WorkflowUserStateModel> userStateList = workflowUserStateDao.findWorkflowUserStateByUserId(userId);
//        List<Long> stateList = new ArrayList<Long>();
//        for (WorkflowUserStateModel model : userStateList) {
//            stateList.add(model.getWorkflowStateTransitionModel().getId());
//        }
//        return stateList;
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public boolean isEndOfWorkflow(Map map) throws Exception {
//        Long pid = (Long) map.get("pid");
//        Optional<WorkflowTransitionModel> currentTransitionOpt = workflowTransitionDao.findById(pid);
//        WorkflowTransitionModel currentTransition = currentTransitionOpt.isPresent() ? currentTransitionOpt.get() : null;
//        WorkflowStateModel workflowStateModel = currentTransition
//                .getWorkflowStateTransitionModel().getNextState();
//        String nextCurrentState = workflowStateModel.getCode();
//        WorkflowLifeModel wflModel = workflowLifeDao
//                .getWorkflowLifeByWorkflowCode(workflowStateModel.getWorkflowModel().getId());
//        String endWorkflowState = wflModel.getEndState();
//        if (endWorkflowState.equalsIgnoreCase(nextCurrentState))
//            return true;
//        return false;
//    }


    @Override
    @Transactional
    public Object start(String workflowCode, String userId) throws Exception {
        return null;
    }
}
