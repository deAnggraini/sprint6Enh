package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

/**
 * 1 = Approve, request move to next state
 * 2 = Deny, request bact to previous state
 * 3 = Cancel, request move to cancel state
 * 4 = Restart, back to start state
 * 5 = Resolve, request move to Complete state
 */
@Entity
@Table(name = "r_wf_user_task_type")
public class WorkflowUserTaskTypeModel extends EntityBase {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;
    @Column(name = "name", nullable = true, length = 255)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
