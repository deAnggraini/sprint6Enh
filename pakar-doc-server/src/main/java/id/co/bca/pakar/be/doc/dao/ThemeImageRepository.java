package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ThemeImage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeImageRepository extends CrudRepository<ThemeImage, String> {
    @Query(
            value =
                    "select id, bg_img_top, deleted, created_by, created_date, modify_by, modify_date from r_theme_image",
            nativeQuery = true)
    ThemeImage findAllThemeImage();
}
