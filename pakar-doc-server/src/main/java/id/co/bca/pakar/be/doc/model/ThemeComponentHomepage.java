package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "r_theme_component_homepage")
public class ThemeComponentHomepage extends EntityBase {
    @Id
    @SequenceGenerator(name = "themeComponentHomepageSeqGen", sequenceName = "themeComponentHomepageSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "themeComponentHomepageSeq")
    private Long id;
    private String component_name;
    private Long orderFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComponent_name() {
        return component_name;
    }

    public void setComponent_name(String component_name) {
        this.component_name = component_name;
    }

    public Long getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(Long orderFlag) {
        this.orderFlag = orderFlag;
    }
}
