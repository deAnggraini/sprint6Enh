package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

@Entity
@Table(name = "t_workflow_transition")
public class WorkflowTransitionModel extends EntityBase {
    @Id
    @SequenceGenerator(name = "wfTransSeqGen", sequenceName = "wfTransSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "wfTransSeqGen")
    @Column(name = "id", nullable = false, unique = true, length = 10)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "current_state", nullable = false)
    private WorkflowStateTransitionModel workflowStateTransitionModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkflowStateTransitionModel getWorkflowStateTransitionModel() {
        return workflowStateTransitionModel;
    }

    public void setWorkflowStateTransitionModel(WorkflowStateTransitionModel workflowStateTransitionModel) {
        this.workflowStateTransitionModel = workflowStateTransitionModel;
    }
}
