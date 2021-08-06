package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "r_wf_action_type")
public class WorkflowActionTypeModel extends EntityBase {
    @Id
    @Column(name = "id", nullable = false, length = 50)
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
