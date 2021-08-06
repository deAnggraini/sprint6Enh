package id.co.bca.pakar.be.wf.dao;

import id.co.bca.pakar.be.wf.model.WorkflowRequestModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowRequestDao extends CrudRepository<WorkflowRequestModel, String> {
}
