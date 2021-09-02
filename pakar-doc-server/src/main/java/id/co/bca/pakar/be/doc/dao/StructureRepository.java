package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.Structure;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StructureRepository extends CrudRepository<Structure, Long> {
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Structure m WHERE m.parentStructure=:parentId AND m.sort=:sort AND m.deleted IS FALSE")
    Boolean existStructureByParentIdAndSort(@Param("parentId") Long parentId, @Param("sort") Long sort);

    @Query(value = "SELECT m.* FROM ( " +
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

    @Query(value = "SELECT m.* FROM ( " +
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

    @Query(value = "SELECT m FROM Structure m WHERE m.parentStructure=:parentId AND m.deleted IS FALSE ")
    List<Structure> findByParentId(@Param("parentId") Long parentId);

    @Query(value = "SELECT CASE WHEN max(m.sort) IS NULL THEN 0 ELSE max(m.sort) END FROM Structure m WHERE m.parentStructure=:parentId AND m.deleted IS FALSE ")
    Long maxSort(@Param("parentId") Long parentId);

    @Query(value = "SELECT m FROM Structure m WHERE m.id=:id AND m.deleted IS FALSE ")
    Structure findStructure(@Param("id") Long id);

    @Query(value = "SELECT rs3.* FROM (  " +
            " WITH RECURSIVE rec AS ( " +
            "     SELECT rs.* " +
            "                           FROM r_structure rs " +
            "                          WHERE rs.id = (select rs2.parent from r_structure rs2 where rs2.id= :id) " +
            "                        UNION ALL " +
            "                         SELECT rs.* " +
            "                           FROM rec rec1, " +
            "                            r_structure rs " +
            "                          WHERE rs.id = rec1.parent " +
            "                        ) " +
            "                 SELECT rec.* " +
            "                 FROM rec " +
            "                 ORDER BY rec.level " +
            " ) rs3",
            nativeQuery = true)
    List<Structure> findParentListById(@Param("id") Long id);

    @Query(value = "SELECT rs3.* FROM ( " +
            "SELECT rs2.* FROM ( " +
            "WITH RECURSIVE rec as " +
            "( " +
            "  SELECT rs.* FROM r_structure rs WHERE rs.id=:id AND rs.deleted IS FALSE" +
            "  UNION ALL " +
            "  SELECT rs.* FROM rec rec1, r_structure rs WHERE rs.parent = rec1.id AND rs.deleted IS FALSE " +
            "  ) " +
            "SELECT * " +
            "FROM rec " +
            ") rs2 " +
            ") rs3 WHERE rs3.id <> :id ",
            nativeQuery = true)
    List<Structure> findChildsById(@Param("id") Long id);

    @Query(value = "SELECT COUNT(tmp.level) FROM " +
            "( " +
            "SELECT rs2.level FROM ( " +
            "WITH RECURSIVE rec as " +
            "( " +
            "  SELECT rs.* FROM r_structure rs WHERE rs.id=:id AND rs.deleted IS FALSE " +
            "  UNION ALL " +
            "  SELECT rs.* FROM rec rec1, r_structure rs WHERE rs.parent = rec1.id AND rs.deleted IS FALSE " +
            "  ) " +
            "SELECT * " +
            "FROM rec " +
            ") rs2 " +
            "group by rs2.level " +
            "order by rs2.level " +
            "asc " +
            ") tmp ",
    nativeQuery = true)
    Long totalChildsLevel(@Param("id") Long id);

    @Query(value = "SELECT rs3.* FROM (  " +
            " WITH RECURSIVE rec AS ( " +
            "     SELECT rs.* " +
            "                           FROM r_structure rs " +
            "                          WHERE rs.id = :id " +
            "                           AND rs.deleted IS FALSE " +
            "                        UNION ALL " +
            "                         SELECT rs.* " +
            "                           FROM rec rec1, " +
            "                            r_structure rs " +
            "                          WHERE rs.id = rec1.parent " +
            "                           AND rs.deleted IS FALSE " +
            "                        ) " +
            "                 SELECT rec.* " +
            "                 FROM rec " +
            "                 ORDER BY rec.level " +
            "                 ASC " +
            " ) rs3",
            nativeQuery = true)
    List<Structure> findBreadcumbById(@Param("id") Long id);

    @Query(value = "select public.find_bc_structure_by_id(:id)", nativeQuery = true)
    List<Structure> findBreadcumb2ById(@Param("id") Long id);

    @Query(value = "SELECT string_agg(tbl.name, ' > ' ) AS location_text" +
            "           FROM ( WITH RECURSIVE rec AS (" +
            "                         SELECT  rs.id," +
            "                             rs.name," +
            "                             rs.parent," +
            "                             rs.level" +
            "                           FROM r_structure  rs" +
            "                          WHERE  rs.id = :id " +
            "                           AND rs.deleted IS FALSE" +
            "                        UNION ALL" +
            "                         SELECT  rs.id," +
            "                             rs.name," +
            "                             rs.parent," +
            "                             rs.level" +
            "                           FROM rec rec_1," +
            "                            r_structure  rs" +
            "                          WHERE  rs.id = rec_1.parent " +
            "                           AND rs.deleted IS FALSE" +
            "                        )" +
            "                 SELECT rec.id," +
            "                    rec.name," +
            "                    rec.level," +
            "                    1 AS grouper" +
            "                   FROM rec" +
            "                  ORDER BY rec.level" +
            "                  ASC " +
            " ) AS tbl",
            nativeQuery = true)
    String getLocationText(@Param("id") Long id);

    @Query(value = "SELECT string_agg(CAST (TBL.id AS text), ',' ) AS location" +
            "           FROM ( WITH RECURSIVE rec AS (" +
            "                         SELECT  rs.id," +
            "                             rs.name," +
            "                             rs.parent," +
            "                             rs.level" +
            "                           FROM r_structure  rs" +
            "                          WHERE  rs.id = :id " +
            "                           AND rs.deleted IS FALSE " +
            "                        UNION ALL" +
            "                         SELECT rs.id," +
            "                             rs.name," +
            "                             rs.parent," +
            "                             rs.level" +
            "                           FROM rec rec_1," +
            "                            r_structure  rs" +
            "                          WHERE  rs.id = rec_1.parent " +
            "                           AND rs.deleted IS FALSE" +
            "                        )" +
            "                 SELECT rec.id," +
            "                    rec.name," +
            "                    rec.level," +
            "                    1 AS grouper" +
            "                   FROM rec" +
            "                  ORDER BY rec.level" +
            "                  ASC " +
            " ) AS tbl",
            nativeQuery = true)
    String getLocationId(@Param("id") Long id);

    @CacheEvict
    @Query("SELECT m FROM Structure m " +
            "WHERE m.deleted IS FALSE " +
            "AND m.id=:id ")
    @Override
    Optional<Structure> findById(@Param("id") Long id);
}
