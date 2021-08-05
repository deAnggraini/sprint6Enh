package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_workflow_transition_detail")
public class WorkflowTransitionDetailModel extends EntityBase {
    @Id
    @SequenceGenerator(name = "wfTransDetailSeqGen", sequenceName = "wfTransDetailSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "wfTransDetailSeqGen")
    @Column(name = "id", nullable = false, unique = true, length = 10)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "transition_pid", nullable = false)
    private WorkflowTransitionModel workflowTransitionModel;
    @Temporal(TemporalType.DATE)
    @Column(name = "propose_date", nullable = false)
    private Date proposeDate;
    @Column(name = "propose_by", nullable = false)
    private String proposeBy;
    @Temporal(TemporalType.DATE)
    @Column(name = "process_date")
    private Date processDate;
    @Column(name = "process_by")
    private String processBy;
    @Column(name = "note")
    private String note;

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

    public Date getProposeDate() {
        return proposeDate;
    }

    public void setProposeDate(Date proposeDate) {
        this.proposeDate = proposeDate;
    }

    public String getProposeBy() {
        return proposeBy;
    }

    public void setProposeBy(String proposeBy) {
        this.proposeBy = proposeBy;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getProcessBy() {
        return processBy;
    }

    public void setProcessBy(String processBy) {
        this.processBy = processBy;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
