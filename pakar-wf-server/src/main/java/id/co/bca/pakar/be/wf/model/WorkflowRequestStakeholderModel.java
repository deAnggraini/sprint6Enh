package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

@Entity
@Table(name = "t_wf_request_sh")
public class WorkflowRequestStakeholderModel extends EntityBase {
    @Id
    @SequenceGenerator(name = "wfRequestShSeqGen", sequenceName = "wfRequestShSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "wfRequestShSeqGen")
    private String id;
    @ManyToOne
    @JoinColumn(name = "request_id")
    private WorkflowRequestModel wfRequest;
    @Column(name = " userid")
    private String userid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WorkflowRequestModel getWfRequest() {
        return wfRequest;
    }

    public void setWfRequest(WorkflowRequestModel wfRequest) {
        this.wfRequest = wfRequest;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
