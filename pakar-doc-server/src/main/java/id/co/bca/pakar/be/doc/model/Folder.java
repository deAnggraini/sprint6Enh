package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_folder")
public class Folder extends EntityBase{
    @Id
    @SequenceGenerator(name = "virtualPagesSeqGen", sequenceName = "virtualPagesSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "virtualPagesSeqGen")
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "parent")
    private Long parent;
}
