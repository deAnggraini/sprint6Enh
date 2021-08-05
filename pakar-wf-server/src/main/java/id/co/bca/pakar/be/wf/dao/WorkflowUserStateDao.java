package id.co.bca.pakar.be.wf.dao;

import id.co.bca.pakar.be.wf.model.WorkflowUserStateModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowUserStateDao extends CrudRepository<WorkflowUserStateModel, Long> {
    @Query("SELECT model FROM WorkflowUserStateModel AS model " +
            "WHERE model.userId=:userId " +
            "AND model.deleted IS FALSE")
    Iterable<WorkflowUserStateModel> findWorkflowUserStateByUserId(String userId);
}
