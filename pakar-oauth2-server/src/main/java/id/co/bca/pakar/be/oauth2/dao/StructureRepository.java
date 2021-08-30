package id.co.bca.pakar.be.oauth2.dao;

import id.co.bca.pakar.be.oauth2.model.Structure;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StructureRepository extends CrudRepository<Structure, Long> {
    @Query(value="SELECT m.* FROM ( \n" +
            "            SELECT m.* FROM (SELECT m.* FROM r_structure m \n" +
            "            LEFT JOIN r_structure m2 ON m2.parent = m.id \n" +
            "            WHERE m.deleted is false \n" +
            "            ORDER BY m.id ASC) m \n" +
            "            WHERE m.parent IN ( \n" +
            "            SELECT m.id FROM r_structure m \n" +
            "            LEFT JOIN r_structure m2 ON m2.parent = m.id \n" +
            "            WHERE m.deleted is false \n" +
            "            ORDER BY m.id ASC) \n" +
            "            UNION \n" +
            "            SELECT m.* FROM r_structure m WHERE m.deleted is false and m.parent = 0 \n" +
            "            ) m ORDER BY m.id ASC",
            nativeQuery = true)
    List<Structure> findAll();

    @Query(value="SELECT m.* FROM ( " +
            "SELECT m.* FROM (SELECT m.* FROM r_structure m " +
            "LEFT JOIN r_structure m2 ON m2.parent = m.id " +
            "WHERE m.deleted is false " +
            "ORDER BY m.id ASC) m " +
            "WHERE m.parent IN ( " +
            "SELECT m.id FROM r_structure m " +
            "LEFT JOIN r_structure m2 ON m2.parent = m.id " +
            "WHERE m.deleted is false ORDER BY m.id ASC) " +
            "UNION " +
            "SELECT m.* FROM r_structure m WHERE m.deleted is false and m.parent = 0 " +
            ") m where m.has_article is true ORDER BY m.id ASC ",
            nativeQuery = true)
    List<Structure> findAllForReader();
}
