package id.co.bca.pakar.be.wf.dao;

import id.co.bca.pakar.be.wf.model.WorkflowLifeModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowLifeDao extends CrudRepository<WorkflowLifeModel, Long> {
    @Query("SELECT model FROM WorkflowLifeModel AS model " +
            "WHERE model.workflowModel.id=:code " +
            "AND model.deleted IS FALSE " +
            "ORDER BY model.id " +
            "ASC")
    WorkflowLifeModel getWorkflowLifeByWorkflowCode(@Param("code") String code);
}
