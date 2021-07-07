package id.co.bca.pakar.be.oauth2.dao;

import id.co.bca.pakar.be.oauth2.model.MenuIcons;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuIconRepository extends CrudRepository<MenuIcons, String> {
    @Query("SELECT m FROM MenuIcons m WHERE m.menu.id=:menu_id AND m.deleted IS FALSE ")
    MenuIcons findIconbyMenuId(@Param("menu_id") Long menu_id);
}
