package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_wf_request_user_task")
public class WorkflowRequestUserTaskModel extends EntityBase {
    @Id
    @SequenceGenerator(name = "wfRequestUserTaskSeqGen", sequenceName = "wfRequestUserTaskSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "wfRequestUserTaskSeqGen")
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "request_id")
    private WorkflowRequestModel requestModel;
    @Column(name = "proposed_by")
    private String proposedBy;
    @Column(name = "proposed_date")
    private Date proposedDate = new Date();
    @Column(name = "note", length = 100)
    private String note;
    @Column(name = "assigne")
    private String assigne;
    @Column(name = "approved_date")
    private Date approvedDate;
    @ManyToOne
    @JoinColumn(name = "sender_state")
    private WorkflowStateModel senderState;
    @ManyToOne
    @JoinColumn(name = "receiver_state")
    private WorkflowStateModel receiverState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkflowRequestModel getRequestModel() {
        return requestModel;
    }

    public void setRequestModel(WorkflowRequestModel requestModel) {
        this.requestModel = requestModel;
    }

    public String getProposedBy() {
        return proposedBy;
    }

    public void setProposedBy(String proposedBy) {
        this.proposedBy = proposedBy;
    }

    public Date getProposedDate() {
        return proposedDate;
    }

    public void setProposedDate(Date proposedDate) {
        this.proposedDate = proposedDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAssigne() {
        return assigne;
    }

    public void setAssigne(String assigne) {
        this.assigne = assigne;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public WorkflowStateModel getSenderState() {
        return senderState;
    }

    public void setSenderState(WorkflowStateModel senderState) {
        this.senderState = senderState;
    }

    public WorkflowStateModel getReceiverState() {
        return receiverState;
    }

    public void setReceiverState(WorkflowStateModel receiverState) {
        this.receiverState = receiverState;
    }
}
