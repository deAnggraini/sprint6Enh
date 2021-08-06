package id.co.bca.pakar.be.wf.service;

public interface WorkflowService {
//    Object initialize(String workflowCode, String userId) throws Exception;
//    Object nextState(Map map) throws Exception ;
//    Object backState(Map map)throws Exception ;
//    List<Long> listOfUserState(String userId) throws Exception;
//    boolean isEndOfWorkflow(Map map) throws Exception;

    Object start(String workflowCode, String userId) throws Exception;
}
