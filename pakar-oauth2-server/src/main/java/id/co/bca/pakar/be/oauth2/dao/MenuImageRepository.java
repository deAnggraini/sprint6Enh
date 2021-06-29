package id.co.bca.pakar.be.oauth2.dao;

import id.co.bca.pakar.be.oauth2.model.Menu;
import id.co.bca.pakar.be.oauth2.model.MenuIcons;
import id.co.bca.pakar.be.oauth2.model.MenuImages;
import id.co.bca.pakar.be.oauth2.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MenuImageRepository extends CrudRepository<MenuImages, String> {
    @Query("SELECT m FROM MenuImages m WHERE m.menu.id=:menu_id")
    MenuImages findImagebyMenuId(@Param("menu_id") Long menu_id);
}
