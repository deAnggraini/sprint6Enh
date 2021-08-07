package id.co.bca.pakar.be.wf.dao;

import id.co.bca.pakar.be.wf.model.WorkflowRequestDataModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkflowRequestDataRepository extends CrudRepository<WorkflowRequestDataModel, String> {
    @Query(value = "SELECT twrd.* FROM t_wf_request_data twrd " +
                "WHERE twrd.name = :name " +
                "AND twrd.request_id=:requestId " +
                "AND twrd.deleted IS FALSE ",
            nativeQuery = true)
    Optional<WorkflowRequestDataModel> findByNameAndRequestId(@Param("name") String name, @Param("requestId") String requestId);
}
