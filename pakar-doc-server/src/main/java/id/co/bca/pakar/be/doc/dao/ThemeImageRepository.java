package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ThemeImage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeImageRepository extends CrudRepository<ThemeImage, String> {
    @Query(
            value =
                    "select bg_image_top where r_theme_image",
            nativeQuery = true)
    ThemeImage findAllThemeImage();
}
