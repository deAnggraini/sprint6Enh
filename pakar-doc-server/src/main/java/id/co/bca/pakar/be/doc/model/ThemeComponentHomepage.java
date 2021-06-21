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
}
