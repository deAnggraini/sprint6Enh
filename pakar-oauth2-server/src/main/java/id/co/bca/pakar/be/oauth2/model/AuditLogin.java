package id.co.bca.pakar.be.oauth2.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "t_audit_login")
public class AuditLogin extends EntityBase {
    @Id
    @SequenceGenerator(name = "auditLoginSeqGen", sequenceName = "auditLoginSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "auditLoginSeqGen")
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "failed_login", nullable = false)
    private Integer failed_login;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getFailed_login() {
        return failed_login;
    }

    public void setFailed_login(Integer failed_login) {
        this.failed_login = failed_login;
    }
}
