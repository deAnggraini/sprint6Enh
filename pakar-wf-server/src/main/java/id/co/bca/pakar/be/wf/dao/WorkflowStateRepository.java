package id.co.bca.pakar.be.wf.dao;

import id.co.bca.pakar.be.wf.model.WorkflowStateModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowStateRepository extends CrudRepository<WorkflowStateModel, String> {
    @Query("SELECT m From WorkflowStateModel m " +
            "WHERE m.workflowStateTypeModel.id=1 " +
            "AND m.deleted IS FALSE")
    WorkflowStateModel findDefaultStartStateById();

    @Query("SELECT m From WorkflowStateModel m " +
            "WHERE m.workflowStateTypeModel.name='Start' " +
            "AND m.deleted IS FALSE")
    WorkflowStateModel findDefaultStartStateByName();

    @Query("SELECT m From WorkflowStateModel m " +
            "WHERE m.workflowStateTypeModel.name='Start' " +
            "AND m.deleted IS FALSE")
    WorkflowStateModel findStateByStartTypeName();

    @Query("SELECT m From WorkflowStateModel m " +
            "WHERE m.workflowStateTypeModel.name='Start' " +
            "AND m.workflowProcessModel.id = :processKey " +
            "AND m.deleted IS FALSE")
    WorkflowStateModel findStateByStartTypeName(@Param("processKey") String processKey);

    @Query("SELECT m From WorkflowStateModel m " +
            "WHERE m.code = :state " +
            "AND m.workflowProcessModel.id = :processId " +
            "AND m.deleted IS FALSE")
    WorkflowStateModel findStateByName(@Param("state") String state, @Param("processId") String processId);
}
