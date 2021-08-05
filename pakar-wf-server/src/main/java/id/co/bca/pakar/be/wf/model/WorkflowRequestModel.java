package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_wf_request")
public class WorkflowRequestModel extends EntityBase {
    @Id
    @SequenceGenerator(name = "wfRequestSeqGen", sequenceName = "wfRequestSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "wfRequestSeqGen")
    private String id;
    @ManyToOne
    @JoinColumn(name = "process_id")
    private WorkflowProcessModel wfprocess;
    @Column(name = " title")
    private String title;
    @Column(name = "request_date", nullable = false)
    private Date requestDate;
    @Column(name =" current_state_id")
    private Long currentState;
    @Column(name = "userid", nullable = false)
    private String userid;
    @Column(name = "username", nullable = false)
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WorkflowProcessModel getWfprocess() {
        return wfprocess;
    }

    public void setWfprocess(WorkflowProcessModel wfprocess) {
        this.wfprocess = wfprocess;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Long getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Long currentState) {
        this.currentState = currentState;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
