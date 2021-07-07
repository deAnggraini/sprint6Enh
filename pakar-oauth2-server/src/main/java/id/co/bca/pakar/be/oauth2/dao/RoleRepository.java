package id.co.bca.pakar.be.oauth2.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import id.co.bca.pakar.be.oauth2.model.Role;
import id.co.bca.pakar.be.oauth2.model.UserRole;

@Repository
public interface RoleRepository extends CrudRepository<Role, String>{
	@Query("SELECT m FROM UserRole m WHERE m.user.username=:username AND m.deleted IS FALSE ")
	List<UserRole> findUserRolesByUsername(@Param("username") String username);
}
