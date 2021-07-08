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

    @Column(name = "number")
    public String skNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkNumber() {
        return skNumber;
    }

    public void setSkNumber(String skNumber) {
        this.skNumber = skNumber;
    }
}
