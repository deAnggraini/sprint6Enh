package id.co.bca.pakar.be.wf.dao;

import id.co.bca.pakar.be.wf.model.WorkflowRequestModel;
import id.co.bca.pakar.be.wf.model.WorkflowRequestUserTaskModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowRequestUserTaskRepository extends CrudRepository<WorkflowRequestUserTaskModel, Long> {
    @Query("SELECT m FROM WorkflowRequestUserTaskModel m " +
            "WHERE m.assigne=:assigne " +
            "AND m.deleted IS FALSE ")
    Iterable<WorkflowRequestUserTaskModel> findByAssigne(String assigne);

    @Query(value = "SELECT twr.* FROM t_wf_request_user_task twrut " +
            "LEFT JOIN t_wf_request twr ON twr.id = twrut.request_id " +
            "LEFT JOIN t_wf_request_data twrd ON twrd.request_id = twr.id " +
            "WHERE twrd.name = 'ARTICLE_ID' " +
            "AND twrd.value = :articleId",
            nativeQuery = true)
    WorkflowRequestModel findWorkflowRequest(@Param("articleId") String articleId);
}
