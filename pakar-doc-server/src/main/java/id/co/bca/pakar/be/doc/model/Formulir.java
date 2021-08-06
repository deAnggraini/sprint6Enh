package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_formulir")
public class Formulir extends EntityBase{
    @Id
    @SequenceGenerator(name = "formulirSeqGen", sequenceName = "formulirSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "formulirSeqGen")
    private Long id;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
