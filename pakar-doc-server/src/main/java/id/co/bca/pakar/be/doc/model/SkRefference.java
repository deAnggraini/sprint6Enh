package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "r_sk_reff")
public class SkRefference extends EntityBase {
    @Id
    @SequenceGenerator(name = "skReffSeqGen", sequenceName = "skReffSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "skReffSeqGen")
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;

    @Column(name = "title")
    private String title;

    @Column(name = "sk_number")
    public String skNumber;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSkNumber() {
        return skNumber;
    }

    public void setSkNumber(String skNumber) {
        this.skNumber = skNumber;
    }
}
