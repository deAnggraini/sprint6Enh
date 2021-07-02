package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.Theme;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ThemeRepository extends CrudRepository<Theme, String> {
    @Query(
            value =
                    "select * from r_theme",
            nativeQuery = true)
    Theme findAllTheme();
}
