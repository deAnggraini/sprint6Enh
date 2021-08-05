package id.co.bca.pakar.be.oauth2.dao;

import id.co.bca.pakar.be.oauth2.dto.SearchDto;
import id.co.bca.pakar.be.oauth2.model.AuditLogin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import id.co.bca.pakar.be.oauth2.model.UserProfile;

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
			"and lower(rup.username) not like lower(concat('%', :username,'%'))",
			nativeQuery = true)
	List<UserProfile> findUserNotReader(@Param("username") String username, @Param("keyword") String keyword);
}
