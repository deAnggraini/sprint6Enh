package id.co.bca.pakar.be.oauth2.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import id.co.bca.pakar.be.oauth2.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, String>{
	@Query("SELECT m FROM UserRole m WHERE m.user.username=:username")
	List<UserRole> findUserRolesByUsername(@Param("username") String username);
}
