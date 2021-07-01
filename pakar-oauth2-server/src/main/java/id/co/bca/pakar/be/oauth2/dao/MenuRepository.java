package id.co.bca.pakar.be.oauth2.dao;

import id.co.bca.pakar.be.oauth2.model.Menu;
import id.co.bca.pakar.be.oauth2.model.MenuImages;
import id.co.bca.pakar.be.oauth2.model.Structure;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends CrudRepository<Menu, String> {

    @Query(
            value = "select menu_id from r_menu_role rmr join r_user_role rur on rmr.role_id=rur.role_id join r_user_profile rup on rur.username=:username",
            nativeQuery = true)
    List<Long> findMenuId(@Param("username") String username);

    @Query(
            value = "SELECT * FROM r_menu m where m.id=:id AND m.nav = 'top' AND m.deleted IS FALSE",
            nativeQuery = true)
    List<Menu> getAllTopMenuById(@Param("id") Long id);

    @Query(
            value = "SELECT * FROM r_menu m where m.id=:id AND m.nav = 'bottom' AND m.deleted IS FALSE",
            nativeQuery = true)
    List<Menu> getAllBottomMenuById(@Param("id") Long id);


    @Query(
            value = "SELECT * FROM r_structure m where m.id=:id ",
            nativeQuery = true)
    List<Structure> getAllStructureById(@Param("id") Long id);

    @Query(
            value ="SELECT * FROM r_menu m WHERE m.nav = 'top' AND m.deleted IS FALSE",
            nativeQuery = true)
    List<Menu> getAllTopMenu();

    @Query(
            value ="SELECT * FROM r_menu m WHERE m.nav = 'bottom' AND m.deleted IS FALSE",
            nativeQuery = true)
    List<Menu> getAllBottomMenu();
}