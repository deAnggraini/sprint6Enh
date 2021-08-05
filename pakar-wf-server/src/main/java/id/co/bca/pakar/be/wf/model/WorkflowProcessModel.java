package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

@Entity
@Table(name = "r_wf_process")
public class WorkflowProcessModel extends EntityBase {
    @Id
    @SequenceGenerator(name = "wfProcessSeqGen", sequenceName = "wfProcessSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "wfProcessSeqGen")
    private String id;
    @Column(name = "name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
