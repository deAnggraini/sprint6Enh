package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "r_wf_state")
public class WorkflowStateModel extends EntityBase {
    @Id
    @Column(name = "code", nullable = false, length = 50)
    private String code;
    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @ManyToOne
    @JoinColumn(name = "state_type", nullable = false)
    private WorkflowStateTypeModel workflowStateTypeModel;
    @ManyToOne
    @JoinColumn(name = "process_id", nullable = false, referencedColumnName = "id")
    private WorkflowProcessModel workflowProcessModel;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WorkflowStateTypeModel getWorkflowStateTypeModel() {
        return workflowStateTypeModel;
    }

    public void setWorkflowStateTypeModel(WorkflowStateTypeModel workflowStateTypeModel) {
        this.workflowStateTypeModel = workflowStateTypeModel;
    }

    public WorkflowProcessModel getWorkflowProcessModel() {
        return workflowProcessModel;
    }

    public void setWorkflowProcessModel(WorkflowProcessModel workflowProcessModel) {
        this.workflowProcessModel = workflowProcessModel;
    }
}
