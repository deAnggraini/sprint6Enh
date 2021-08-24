package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

@Entity
@Table(name = "r_wf_group")
public class WokrflowGroupModel extends EntityBase {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "process_id")
    private WorkflowProcessModel process;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WorkflowProcessModel getProcess() {
        return process;
    }

    public void setProcess(WorkflowProcessModel process) {
        this.process = process;
    }
}
