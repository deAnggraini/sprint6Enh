package id.co.bca.pakar.be.oauth2.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import id.co.bca.pakar.be.oauth2.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
	@Query("SELECT m FROM User m WHERE m.username=:username AND m.deleted IS FALSE")
	User findUserByUsername(@Param("username") String username);
}
