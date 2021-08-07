package id.co.bca.pakar.be.wf.dao;

import id.co.bca.pakar.be.wf.model.WorkflowUserTaskModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkflowUserTaskRepository extends CrudRepository<WorkflowUserTaskModel, Long> {
    @Query("SELECT m FROM WorkflowUserTaskModel m " +
            "WHERE m.id=:id " +
            "AND m.deleted IS FALSE ")
    Optional<WorkflowUserTaskModel> findById(@Param("id") Long id);
}
