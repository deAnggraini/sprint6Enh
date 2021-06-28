package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ThemeImage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemeImageRepository extends CrudRepository<ThemeImage, String> {
    @Query(
            value =
                    "select id, image_name, image_type, deleted, created_by, created_date, modify_by, modify_date, file_location from r_theme_image",
            nativeQuery = true)
    List<ThemeImage> findAllThemeImage();
}
