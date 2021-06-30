package id.co.bca.pakar.be.oauth2.dao;

import id.co.bca.pakar.be.oauth2.model.Menu;
import id.co.bca.pakar.be.oauth2.model.MenuImages;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends CrudRepository<Menu, String> {
//    @Query("SELECT m FROM Menu m WHERE m.parent_menu=:parent_menu")
//    List<Menu> findMenuChilds(@Param("parent_menu") Long parent_menu);

//    @Query("SELECT m FROM Menu")
//    List<Menu> getMenu();

    @Query(
            value = "SELECT * FROM r_menu m ",
            nativeQuery = true)
    List<Menu> getAllMenu();

    @Query(
            value ="SELECT * FROM r_menu m WHERE m.nav = 'top' AND m.deleted IS FALSE",
            nativeQuery = true)
    List<Menu> getAllTopMenu();

    @Query(
            value ="SELECT * FROM r_menu m WHERE m.nav = 'bottom' AND m.deleted IS FALSE",
            nativeQuery = true)
    List<Menu> getAllBottomMenu();
}
