package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

@Entity
@Table(name = "r_wf_transition_action")
public class WorkflowTransitionActionModel extends EntityBase {
    @Id
    @SequenceGenerator(name = "wfTransActionSeqGen", sequenceName = "wfTransActionSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "wfTransActionSeqGen")
    @Column(name = "id", nullable = false, unique = true, length = 10)
    private Long id;
    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;
    @ManyToOne
    @JoinColumn(name = "transition", nullable = false)
    private WorkflowTransitionModel transition;
    @ManyToOne
    @JoinColumn(name = "action", nullable = false)
    private WorkflowActionModel action;

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

    public WorkflowActionModel getAction() {
        return action;
    }

    public void setAction(WorkflowActionModel action) {
        this.action = action;
    }
}
