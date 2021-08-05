package id.co.bca.pakar.be.wf.dao;

import id.co.bca.pakar.be.wf.model.WorkflowTransitionDetailModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowTransitionDetailDao extends CrudRepository<WorkflowTransitionDetailModel, Long> {
}
