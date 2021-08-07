package id.co.bca.pakar.be.wf.dao;

import id.co.bca.pakar.be.wf.model.WorkflowRequestModel;
import id.co.bca.pakar.be.wf.model.WorkflowRequestUserTaskModel;
import id.co.bca.pakar.be.wf.model.WorkflowUserTaskModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkflowRequestUserTaskRepository extends CrudRepository<WorkflowRequestUserTaskModel, Long> {
    Iterable<WorkflowRequestModel> findRequestByUserTask(Long userTask);
}
