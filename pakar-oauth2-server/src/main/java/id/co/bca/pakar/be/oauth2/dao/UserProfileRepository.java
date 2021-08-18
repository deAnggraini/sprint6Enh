package id.co.bca.pakar.be.oauth2.dao;

import id.co.bca.pakar.be.oauth2.model.AuditLogin;
import id.co.bca.pakar.be.oauth2.model.UserProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long>{
	@Query("SELECT m FROM UserProfile m WHERE m.user.username=:username AND m.deleted IS FALSE ")
	UserProfile findByUsername(@Param("username") String username);

	@Query("SELECT m FROM AuditLogin m WHERE m.username=:username")
	AuditLogin findFailedLogin(@Param("username") String username);

	@Query(value ="select rup.* from r_user_profile rup join r_user ru on rup.username = ru.username " +
			"join r_user_role rur on ru.username = rur.username " +
			"WHERE (lower(rup.firstname) like lower(concat('%',:keyword,'%')) or lower(rup.lastname) like lower(concat('%', :keyword,'%')) " +
			"or lower(rup.email) like lower(concat('%', :keyword,'%')) or lower(rup.fullname) like lower(concat('%', :keyword,'%'))) AND rur.role_id != 'READER' " +
			"and lower(rup.username) not like lower(concat('%', :username,'%')) order by rup.username asc",
			nativeQuery = true)
	List<UserProfile> findUserNotReader(@Param("username") String username, @Param("keyword") String keyword);

	@Query(value ="select rup.* from r_user_profile rup " +
			"join r_user ru on rup.username = ru.username " +
			"join r_user_role rur on ru.username = rur.username " +
			"WHERE (lower(rup.firstname) like lower(concat('%',:keyword,'%')) or lower(rup.lastname) like lower(concat('%', :keyword,'%')) " +
			"or lower(rup.email) like lower(concat('%', :keyword,'%')) or lower(rup.fullname) like lower(concat('%', :keyword,'%'))) " +
			"AND rur.role_id = :role " +
			"and lower(rup.username) not like lower(concat('%', :username,'%')) " +
			"order by rup.username asc",
			nativeQuery = true)
	List<UserProfile> findUsersByRole(@Param("username") String username, @Param("role") String role, @Param("keyword") String keyword);

	@Query(value ="SELECT rup.* FROM r_user_profile rup " +
			"LEFT JOIN r_user ru ON rup.username = ru.username " +
			"WHERE rup.username IN (:userIds) " +
			"AND rup.deleted IS FALSE ",
			nativeQuery = true)
	List<UserProfile> findUsersByListUser(@Param("userIds") List<String> userIds);
}
