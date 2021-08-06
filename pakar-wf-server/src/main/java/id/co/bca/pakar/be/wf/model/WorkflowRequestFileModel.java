package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

@Entity
@Table(name = "t_wf_request_file")
public class WorkflowRequestFileModel extends EntityBase {
    @Id
    @SequenceGenerator(name = "wfRequestFileSeqGen", sequenceName = "wfRequestFileSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "wfRequestFileSeqGen")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "request_id")
    private WorkflowRequestModel wfRequest;
    @Column(name = "filename")
    private String filename;
    @Column(name = "content")
    private String content;
    @Column(name = "mime_type")
    private String mimeType;
    @Column(name = "userid")
    private String userid;

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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
