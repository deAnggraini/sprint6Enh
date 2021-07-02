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
                    "SELECT rti.* FROM r_theme_image rti left join r_images ri on rti.image_id = ri.id where ri.deleted is false",
            nativeQuery = true)
    List<ThemeImage> findAllThemeImage();
}
