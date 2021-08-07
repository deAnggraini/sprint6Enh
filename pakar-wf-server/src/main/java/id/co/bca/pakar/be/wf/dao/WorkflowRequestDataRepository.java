package id.co.bca.pakar.be.wf.dao;

import id.co.bca.pakar.be.wf.model.WorkflowRequestDataModel;
import id.co.bca.pakar.be.wf.model.WorkflowRequestModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowRequestDataRepository extends CrudRepository<WorkflowRequestDataModel, String> {
}
