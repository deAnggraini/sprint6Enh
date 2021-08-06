package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "r_wf_action")
public class WorkflowActionModel extends EntityBase {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;
    @ManyToOne
    @JoinColumn(name = "action_type")
    private WorkflowActionTypeModel actionType;
    @ManyToOne
    @JoinColumn(name = "process")
    private WorkflowProcessModel process;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

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

    public WorkflowActionTypeModel getActionType() {
        return actionType;
    }

    public void setActionType(WorkflowActionTypeModel actionType) {
        this.actionType = actionType;
    }

    public WorkflowProcessModel getProcess() {
        return process;
    }

    public void setProcess(WorkflowProcessModel process) {
        this.process = process;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
