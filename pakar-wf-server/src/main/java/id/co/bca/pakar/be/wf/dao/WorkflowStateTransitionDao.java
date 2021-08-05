package id.co.bca.pakar.be.wf.dao;

import id.co.bca.pakar.be.wf.model.WorkflowStateTransitionModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowStateTransitionDao extends CrudRepository<WorkflowStateTransitionModel, Long> {
    @Query("SELECT model FROM WorkflowStateTransitionModel AS model " +
            "WHERE model.startState.code=:code " +
            "AND model.deleted is FALSE " +
            "ORDER BY model.id " +
            "ASC")
    WorkflowStateTransitionModel getWorkflowStateTransitionModelFromStartState(@Param("code") String code);
    @Query("SELECT model FROM WorkflowStateTransitionModel AS model " +
            "WHERE model.nextState.code=:code " +
            "AND model.deleted is FALSE " +
            "ORDER BY model.id " +
            "ASC")
    WorkflowStateTransitionModel getWorkflowStateTransitionModelFromNextState(@Param("code") String code);
}
