package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "r_wf_user_task")
public class WorkflowUserTaskModel extends EntityBase {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;
    @ManyToOne
    @JoinColumn(name = "user_task_type")
    private WorkflowUserTaskTypeModel userTaskType;
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
