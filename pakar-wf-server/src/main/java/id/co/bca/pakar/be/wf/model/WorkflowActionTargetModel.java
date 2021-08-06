package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_wf_action_target")
public class WorkflowActionTargetModel extends EntityBase {
    @Id
    @SequenceGenerator(name = "wfActionTargetSeqGen", sequenceName = "wfActionTargetSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "wfActionTargetSeqGen")
    private Long id;
    @Column(name = "proposed_by")
    private String proposedBy;
    @Column(name = "proposed_date")
    private Date proposedDate = new Date();
    @Column(name = "assigne")
    private String assigne;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAssigne() {
        return assigne;
    }

    public void setAssigne(String assigne) {
        this.assigne = assigne;
    }
}
