package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

@Entity
@Table(name = "t_wf_request_note")
public class WorkflowRequestNoteModel extends EntityBase {
    @Id
    @SequenceGenerator(name = "wfRequestNoteSeqGen", sequenceName = "wfRequestNoteSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "wfRequestNoteSeqGen")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "request_id")
    private WorkflowRequestModel wfRequest;
    @Column(name = " note")
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkflowRequestModel getWfRequest() {
        return wfRequest;
    }

    public void setWfRequest(WorkflowRequestModel wfRequest) {
        this.wfRequest = wfRequest;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
