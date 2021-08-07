package id.co.bca.pakar.be.wf.dao;

import id.co.bca.pakar.be.wf.model.WorkflowUserTaskAssignmentModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowUserTaskAssignmentRepository extends CrudRepository<WorkflowUserTaskAssignmentModel, Long> {
    @Query("SELECT m FROM WorkflowUserTaskAssignmentModel m " +
            "WHERE m.assigne=:assigne " +
            "AND m.deleted IS FALSE ")
    Iterable<WorkflowUserTaskAssignmentModel> findByAssigne(String assigne);
}
