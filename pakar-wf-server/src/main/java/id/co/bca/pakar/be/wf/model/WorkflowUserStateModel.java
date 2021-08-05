package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

@Entity
@Table(name = "r_workflow_user_state")
public class WorkflowUserStateModel extends EntityBase {
    @Id
    @SequenceGenerator(name = "wfUserStateSeqGen", sequenceName = "wfUserStateSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "wfUserStateSeqGen")
    @Column(name = "id", nullable = false, unique = true, length = 10)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private String userId;
    @ManyToOne
    @JoinColumn(name = "state_transition_id", nullable = false)
    private WorkflowStateTransitionModel workflowStateTransitionModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public WorkflowStateTransitionModel getWorkflowStateTransitionModel() {
        return workflowStateTransitionModel;
    }

    public void setWorkflowStateTransitionModel(WorkflowStateTransitionModel workflowStateTransitionModel) {
        this.workflowStateTransitionModel = workflowStateTransitionModel;
    }
}
