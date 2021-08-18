package id.co.bca.pakar.be.oauth2.dao;

import id.co.bca.pakar.be.oauth2.model.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    @Query("SELECT m FROM UserRole m " +
			"WHERE m.role.id=:role " +
			"AND m.deleted IS FALSE " +
			"AND m.user.username LIKE %:keyword% ")
    List<UserRole> findUserRolesByRole(@Param("role") String role, @Param("keyword") String keyword);
}
