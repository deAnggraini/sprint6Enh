package id.co.bca.pakar.be.oauth2.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import id.co.bca.pakar.be.oauth2.model.UserProfile;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long>{
	@Query("SELECT m FROM UserProfile m WHERE m.user.username=:username")
	UserProfile findByUsername(@Param("username") String username);
}
