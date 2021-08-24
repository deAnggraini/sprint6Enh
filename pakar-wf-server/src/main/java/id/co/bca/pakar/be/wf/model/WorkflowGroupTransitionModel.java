package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

/**
 * mapping transition to group of stakeholder
 */
@Entity
@Table(name = "r_wf_group_transition")
public class WorkflowGroupTransitionModel extends EntityBase {
    @Id
    @SequenceGenerator(name = "wfGroupTransitionSeqGen", sequenceName = "wfGroupTransitionSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "wfGroupTransitionSeqGen")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transition")
    private WorkflowTransitionModel workflowTransitionModel;

    @ManyToOne
    @JoinColumn(name = "rcv_group")
    private WokrflowGroupModel rcvGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkflowTransitionModel getWorkflowTransitionModel() {
        return workflowTransitionModel;
    }

    public void setWorkflowTransitionModel(WorkflowTransitionModel workflowTransitionModel) {
        this.workflowTransitionModel = workflowTransitionModel;
    }

    public WokrflowGroupModel getRcvGroup() {
        return rcvGroup;
    }

    public void setRcvGroup(WokrflowGroupModel rcvGroup) {
        this.rcvGroup = rcvGroup;
    }
}
