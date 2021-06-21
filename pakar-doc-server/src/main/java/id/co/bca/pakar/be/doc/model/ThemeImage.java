package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "r_theme_image")
public class ThemeImage extends EntityBase {
        @Id
        @SequenceGenerator(name = "themeImageSeqGen", sequenceName = "themeImageSeq", initialValue = 1, allocationSize = 1)
        @GeneratedValue(generator = "themeImageSeqGen")
        private Long id;
        private String image_name;
        private String imageType;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getImage_name() {
                return image_name;
        }

        public void setImage_name(String image_name) {
                this.image_name = image_name;
        }

        public String getImageType() {
                return imageType;
        }

        public void setImageType(String imageType) {
                this.imageType = imageType;
        }
}
