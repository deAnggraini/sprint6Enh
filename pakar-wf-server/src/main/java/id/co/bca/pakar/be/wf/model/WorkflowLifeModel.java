package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "r_workflow_life")
public class WorkflowLifeModel extends EntityBase {
    @Id
    @SequenceGenerator(name = "wfLifeSeqGen", sequenceName = "wfLifeTransSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "wfLifeSeqGen")
    @Column(name = "id", nullable = false, unique = true, length = 10)
    private Long id;
    @Column(name = "start_state", nullable = false)
    private String startState;
    @Column(name = "end_state", nullable = false)
    private String endState;
    @ManyToOne
    @JoinColumn(name = "workflow_id", nullable = false)
    private WorkflowModel workflowModel;


    public String getStartState() {
        return startState;
    }

    public void setStartState(String startState) {
        this.startState = startState;
    }


    public String getEndState() {
        return endState;
    }

    public void setEndState(String endState) {
        this.endState = endState;
    }


    public WorkflowModel getWorkflowModel() {
        return workflowModel;
    }

    public void setWorkflowModel(WorkflowModel workflowModel) {
        this.workflowModel = workflowModel;
    }
}
