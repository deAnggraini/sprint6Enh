package id.co.bca.pakar.be.wf.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_wf_request")
public class WorkflowRequestModel extends EntityBase {
    @Id
    @GenericGenerator(name = "UUID",  strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    @Column(name = "id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "process_id")
    private WorkflowProcessModel wfprocess;
    @Column(name = " title")
    private String title;
    @Column(name = "request_date", nullable = false)
    private Date requestDate;
    @ManyToOne
    @JoinColumn(name =" current_state")
    private WorkflowStateModel currentState;
    @Column(name = "userid", nullable = false)
    private String userid;

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

    public WorkflowStateModel getCurrentState() {
        return currentState;
    }

    public void setCurrentState(WorkflowStateModel currentState) {
        this.currentState = currentState;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
