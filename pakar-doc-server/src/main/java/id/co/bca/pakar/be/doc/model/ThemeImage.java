package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

@Entity
@Table(name = "r_theme_image")
public class ThemeImage extends EntityBase {
        @Id
        @SequenceGenerator(name = "themeImageSeqGen", sequenceName = "themeImageSeq", initialValue = 1, allocationSize = 1)
        @GeneratedValue(generator = "themeImageSeqGen")
        private Long id;
        private String imageType;
        @ManyToOne
        @JoinColumn(name = "image_id")
        private Images images;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getImageType() {
                return imageType;
        }

        public void setImageType(String imageType) {
                this.imageType = imageType;
        }

        public Images getImages() {
                return images;
        }

        public void setImages(Images images) {
                this.images = images;
        }
}
