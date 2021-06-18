package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "r_theme")
public class Theme extends EntityBase {
    @Id
    @SequenceGenerator(name = "themeSeqGen", sequenceName = "themeSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "themeSeqGen")
    private Long id;
    private String background;
    private String color;
    private String hover;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHover() {
        return hover;
    }

    public void setHover(String hover) {
        this.hover = hover;
    }
}
