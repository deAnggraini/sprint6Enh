package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

/**
 * definition of state
 *  likes 1 = Start, 2 = Normal, 3 = Complete, 4 = Denied, 5 = cancel
 *  every states must have exactly  one of these state type
 */
@Entity
@Table(name = "r_wf_state_type")
public class WorkflowStateTypeModel extends EntityBase {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;
    @Column(name = "name", nullable = false, length = 255)
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
