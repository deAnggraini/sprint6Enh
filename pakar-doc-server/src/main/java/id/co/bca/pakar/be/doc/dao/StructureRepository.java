package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.Structure;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StructureRepository extends CrudRepository<Structure, Long>{
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Structure m WHERE m.parentStructure=:parentId AND m.sort=:sort AND m.deleted IS FALSE")
    Boolean existStructureByParentIdAndSort(@Param("parentId") Long parentId, @Param("sort") Long sort);
    @Query(value="SELECT m.* FROM ( " +
            "SELECT m.* FROM (SELECT m.* FROM r_structure m " +
            "LEFT JOIN r_structure m2 ON m2.parent = m.id " +
            "WHERE m.deleted is false " +
            "ORDER BY m.id ASC) m " +
            "WHERE m.parent IN ( " +
            "SELECT m.id FROM r_structure m " +
            "LEFT JOIN r_structure m2 ON m2.parent = m.id " +
            "WHERE m.deleted is false " +
            "ORDER BY m.id ASC) " +
            "UNION " +
            "SELECT m.* FROM r_structure m WHERE m.deleted is false and m.parent = 0 " +
            ") m ORDER BY m.id ASC ",
            nativeQuery = true)
    List<Structure> findAll();

    @Query(value="SELECT m FROM Structure m WHERE m.parentStructure=:parentId AND m.deleted IS FALSE ")
    List<Structure> findByParentId(@Param("parentId") Long parentId);

    @Query(value = "SELECT max(m.sort) FROM Structure m WHERE m.parentStructure=:parentId AND m.deleted IS FALSE ")
    public Long maxSort(@Param("parentId") Long parentId);

}
