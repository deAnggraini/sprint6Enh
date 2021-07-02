package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ThemeComponentHomepage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ThemeComponentHomepageRepository extends CrudRepository<ThemeComponentHomepage, String> {
    @Query(
            value =
                    "SELECT * from r_theme_component_homepage order by order_flag asc",
            nativeQuery = true)
    List<ThemeComponentHomepage> findAllThemeComponentHomepage();
}
