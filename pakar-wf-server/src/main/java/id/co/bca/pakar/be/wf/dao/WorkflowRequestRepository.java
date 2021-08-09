package id.co.bca.pakar.be.wf.dao;

import id.co.bca.pakar.be.wf.model.WorkflowRequestModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowRequestRepository extends CrudRepository<WorkflowRequestModel, String> {
    @Query(value = "select twr.* FROM t_wf_user_task_assginment twuta " +
            "left join t_wf_request_user_task twrut ON twrut.user_task_id = twuta.user_task " +
            "left join t_wf_request twr on twr.id = twrut.request_id " +
            "where twuta.assigne = :assignee",
            nativeQuery = true)
    Iterable<WorkflowRequestModel> findByAssigne(@Param("assigne") String assigne);

//    @Query(value = "select twr.* FROM t_wf_request_user_task twrut " +
//            "left join t_wf_request twr on twr.id = twrut.request_id " +
//            "where twrut.user_task_id = :id",
//            nativeQuery = true)
//    Iterable<WorkflowRequestModel> findByUserTask(@Param("id") Long id);
//
//    @Query(value = "select twr.* FROM t_wf_request_user_task twrut " +
//            "left join t_wf_request twr on twr.id = twrut.request_id " +
//            "where twrut.user_task_id = :id",
//            nativeQuery = true)
//    Iterable<WorkflowRequestModel> findByUserTask(@Param("id") Long id);
}
