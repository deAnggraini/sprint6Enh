package id.co.bca.pakar.be.wf.dao;

import id.co.bca.pakar.be.wf.model.WorkflowPersonStateModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkflowPersonStateRepository extends CrudRepository<WorkflowPersonStateModel, String> {
    @Query(
            "SELECT m FROM WorkflowPersonStateModel m " +
                    "WHERE m.code=:code " +
                    "AND m.deleted IS FALSE"
    )
    @Override
    Optional<WorkflowPersonStateModel> findById(@Param("code") String code);
}
