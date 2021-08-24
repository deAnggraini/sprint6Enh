package id.co.bca.pakar.be.wf.dao;

import id.co.bca.pakar.be.wf.model.WorkflowTransitionModel;
import id.co.bca.pakar.be.wf.model.WorkflowTransitionUserTaskModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowTransitionUserTaskRepository extends CrudRepository<WorkflowTransitionUserTaskModel, Long> {
    @Query("SELECT m FROM WorkflowTransitionUserTaskModel m " +
            "WHERE m.transition.currentState.code =:state " +
            "AND m.deleted IS FALSE")
    Iterable<WorkflowTransitionModel> findTransitionByStartState(@Param("state") String state);

    @Query(value = " SELECT rwtut.* FROM r_wf_transition_user_task rwtut  " +
            "            inner join r_wf_transition rwt on rwt.id = rwtut.transition " +
            "            inner join r_wf_state rws on rws.code = rwt.current_state " +
            "            inner join r_wf_user_task rwut ON rwut.id = rwtut.user_task " +
            "            inner join r_wf_user_task_type rwutt on rwutt.id = rwut.user_task_type " +
            "            WHERE rwt.current_state = :state " +
            "            AND rwutt.id= :taskTypeId ",
    nativeQuery = true)
    WorkflowTransitionUserTaskModel findTransitionByCurrentStateAndActionType(@Param("state") String state, @Param("taskTypeId") Long taskTypeId);

    @Query(value = " SELECT rwtut.* FROM r_wf_transition_user_task rwtut  " +
            "            inner join r_wf_transition rwt on rwt.id = rwtut.transition " +
            "            inner join r_wf_state rws on rws.code = rwt.current_state " +
            "            inner join r_wf_user_task rwut ON rwut.id = rwtut.user_task " +
            "            inner join r_wf_user_task_type rwutt on rwutt.id = rwut.user_task_type " +
            "            WHERE rwt.current_state = :state " +
            "            AND rwt.id = :transitionId " +
            "            AND rwutt.id= :taskTypeId ",
            nativeQuery = true)
    WorkflowTransitionUserTaskModel findTransitionByCurrentStateAndActionType(@Param("state") String state, @Param("taskTypeId") Long taskTypeId, @Param("transitionId") Long transitionId);

}
