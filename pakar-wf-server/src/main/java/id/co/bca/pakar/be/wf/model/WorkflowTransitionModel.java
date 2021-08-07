package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

@Entity
@Table(name = "r_wf_transition")
public class WorkflowTransitionModel extends EntityBase {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;
    @ManyToOne
    @JoinColumn(name = "current_state", nullable = false)
    private WorkflowStateModel currentState;
    @ManyToOne
    @JoinColumn(name = "next_state", nullable = false)
    private WorkflowStateModel nextState;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "process_id")
    private WorkflowProcessModel process;

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

    public WorkflowStateModel getCurrentState() {
        return currentState;
    }

    public void setCurrentState(WorkflowStateModel currentState) {
        this.currentState = currentState;
    }

    public WorkflowStateModel getNextState() {
        return nextState;
    }

    public void setNextState(WorkflowStateModel nextState) {
        this.nextState = nextState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WorkflowProcessModel getProcess() {
        return process;
    }

    public void setProcess(WorkflowProcessModel process) {
        this.process = process;
    }
}
