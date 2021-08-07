package id.co.bca.pakar.be.wf.dao;

import id.co.bca.pakar.be.wf.model.WorkflowRequestUserTaskModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowRequestUserTaskRepository extends CrudRepository<WorkflowRequestUserTaskModel, Long> {
    @Query("SELECT m FROM WorkflowRequestUserTaskModel m " +
            "WHERE m.assigne=:assigne " +
            "AND m.deleted IS FALSE ")
    Iterable<WorkflowRequestUserTaskModel> findByAssigne(String assigne);
}
