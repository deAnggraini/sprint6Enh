package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

@Entity
@Table(name = "r_wf_transition_service_task")
public class WorkflowTransitionServiceTaskModel extends EntityBase {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;
    @ManyToOne
    @JoinColumn(name = "transition", nullable = false)
    private WorkflowTransitionModel transition;
    @ManyToOne
    @JoinColumn(name = "service_task", nullable = false)
    private WorkflowServiceTaskModel serviceTask;

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

    public WorkflowTransitionModel getTransition() {
        return transition;
    }

    public void setTransition(WorkflowTransitionModel transition) {
        this.transition = transition;
    }

    public WorkflowServiceTaskModel getServiceTask() {
        return serviceTask;
    }

    public void setServiceTask(WorkflowServiceTaskModel serviceTask) {
        this.serviceTask = serviceTask;
    }
}
