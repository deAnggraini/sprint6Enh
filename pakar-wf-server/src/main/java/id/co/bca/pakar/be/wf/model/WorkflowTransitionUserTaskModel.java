package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

@Entity
@Table(name = "r_wf_transition_user_task")
public class WorkflowTransitionUserTaskModel extends EntityBase {
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
    @JoinColumn(name = "user_task", nullable = false)
    private WorkflowUserTaskModel userTask;

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

    public WorkflowUserTaskModel getUserTask() {
        return userTask;
    }

    public void setUserTask(WorkflowUserTaskModel userTask) {
        this.userTask = userTask;
    }
}
