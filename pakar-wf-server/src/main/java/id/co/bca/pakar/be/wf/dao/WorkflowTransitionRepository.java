package id.co.bca.pakar.be.wf.dao;

import id.co.bca.pakar.be.wf.model.WorkflowTransitionModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowTransitionRepository extends CrudRepository<WorkflowTransitionModel, Long> {
    @Query("SELECT m FROM WorkflowTransitionModel m " +
            "WHERE m.currentState.code =:state " +
            "AND m.deleted IS FALSE")
    Iterable<WorkflowTransitionModel> findTransitionByStartState(@Param("state") String state);
}
