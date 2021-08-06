package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

@Entity
@Table(name = "t_wf_request_data")
public class WorkflowRequestDataModel extends EntityBase {
    @Id
    @SequenceGenerator(name = "wfRequestDataSeqGen", sequenceName = "wfRequestDataSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "wfRequestDataSeqGen")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "request_id")
    private WorkflowRequestModel wfRequest;
    @Column(name = " name")
    private String name;
    @Column(name = " value")
    private String value;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
