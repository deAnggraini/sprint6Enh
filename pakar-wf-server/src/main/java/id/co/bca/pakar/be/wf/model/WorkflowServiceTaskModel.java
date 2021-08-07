package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

/**
 * define service action after transition
 */
@Entity
@Table(name = "r_wf_services_task")
public class WorkflowServiceTaskModel extends EntityBase {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;
    @ManyToOne
    @JoinColumn(name = "service_task_type")
    private WorkflowServiceTaskTypeModel workflowServiceTaskTypeModel;
    @Column(name = "service_name")
    private String serviceName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public WorkflowServiceTaskTypeModel getWorkflowServiceTaskTypeModel() {
        return workflowServiceTaskTypeModel;
    }

    public void setWorkflowServiceTaskTypeModel(WorkflowServiceTaskTypeModel workflowServiceTaskTypeModel) {
        this.workflowServiceTaskTypeModel = workflowServiceTaskTypeModel;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
