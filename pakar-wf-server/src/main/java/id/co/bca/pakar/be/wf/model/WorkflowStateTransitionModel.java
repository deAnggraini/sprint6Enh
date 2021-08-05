package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

@Entity
@Table(name = "r_workflow_state_transition")
public class WorkflowStateTransitionModel extends EntityBase {
    @Id
    @SequenceGenerator(name = "wfStateTransSeqGen", sequenceName = "wfStateTransSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "wfStateTransSeqGen")
    @Column(name = "id", nullable = false, unique = true, length = 10)
    private Long id;
    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;
    @ManyToOne
    @JoinColumn(name = "start_state_id")
    private WorkflowStateModel startState;
    @ManyToOne
    @JoinColumn(name = "next_state_id")
    private WorkflowStateModel nextState;
    @Column(name = "transition_name")
    private String transitionName;

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

    public WorkflowStateModel getStartState() {
        return startState;
    }

    public void setStartState(WorkflowStateModel startState) {
        this.startState = startState;
    }

    public WorkflowStateModel getNextState() {
        return nextState;
    }

    public void setNextState(WorkflowStateModel nextState) {
        this.nextState = nextState;
    }

    public String getTransitionName() {
        return transitionName;
    }

    public void setTransitionName(String transitionName) {
        this.transitionName = transitionName;
    }
}
