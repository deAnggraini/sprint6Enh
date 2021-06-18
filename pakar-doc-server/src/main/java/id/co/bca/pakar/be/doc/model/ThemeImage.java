package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "r_theme_image")
public class ThemeImage extends EntityBase {
        @Id
        @SequenceGenerator(name = "themeImageSeqGen", sequenceName = "themeImageSeq", initialValue = 1, allocationSize = 1)
        @GeneratedValue(generator = "themeImageSeqGen")
        private Long id;
        private String bg_img_top;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getBg_img_top() {
                return bg_img_top;
        }

        public void setBg_img_top(String bg_img_top) {
                this.bg_img_top = bg_img_top;
        }
}
