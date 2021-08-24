package id.co.bca.pakar.be.wf.dao;

import id.co.bca.pakar.be.wf.model.WorkflowGroupTransitionModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowGroupTransitionRepository extends CrudRepository<WorkflowGroupTransitionModel, String> {
    @Query(
            "SELECT m FROM WorkflowGroupTransitionModel m " +
                    "WHERE m.rcvGroup.name = :group " +
                    "AND m.workflowTransitionModel.currentState.code = :startState " +
                    "AND m.deleted IS FALSE"
    )
    WorkflowGroupTransitionModel findByReceiverGroup(@Param("group") String group, @Param("startState") String startState);
}
