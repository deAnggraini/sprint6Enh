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
            value = "select menu_id from r_menu_role rmr join r_user_role rur on rmr.role_id=rur.role_id join r_user_profile rup on rur.username=:username AND m.deleted IS FALSE ",
            nativeQuery = true)
    List<Long> findMenuId(@Param("username") String username);

    @Query(
            value = "select m.* from r_menu m left join r_menu_role rmr on m.id = rmr.menu_id left join r_user_role rur on rmr.role_id=rur.role_id left join r_user ru on rur.username=ru.username where ru.username=:username AND m.nav = 'top' AND m.deleted IS FALSE",
            nativeQuery = true)
    List<Menu> getAllTopMenuById(@Param("username") String username);

    @Query(
            value = "select m.* from r_menu m left join r_menu_role rmr on m.id = rmr.menu_id left join r_user_role rur on rmr.role_id=rur.role_id left join r_user ru on rur.username=ru.username where ru.username=:username AND m.nav = 'bottom' AND m.deleted IS FALSE",
            nativeQuery = true)
    List<Menu> getAllBottomMenuById(@Param("username") String username);


    @Query(
            value = "SELECT * from r_structure where has_article is true and deleted is false ",
            nativeQuery = true)
    List<Structure> getAllStructure();

}
