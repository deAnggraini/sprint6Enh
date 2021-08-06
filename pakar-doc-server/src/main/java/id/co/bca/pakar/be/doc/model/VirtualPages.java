package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "t_virtual_pages")
public class VirtualPages extends EntityBase {
    @Id
    @SequenceGenerator(name = "virtualPagesSeqGen", sequenceName = "virtualPagesSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "virtualPagesSeqGen")
    private Long id;

    @Column(name = "title", unique = true, nullable = false )
    private String title;

    @Column(name = "tipe")
    private String tipe;

    @Column(name = "used_by")
    private String used_by;

    @ManyToOne
    @JoinColumn(name = "folder")
    private Folder folder;

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

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getUsed_by() {
        return used_by;
    }

    public void setUsed_by(String used_by) {
        this.used_by = used_by;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }
}
