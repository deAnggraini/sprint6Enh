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
    @Query(value="SELECT m.id, m.created_by, m.created_date, m.deleted, m.modify_by, m.modify_date, m.edit, m.level, m.parent, m.sort, "+
            "           ,m.description, m.name, m.uri, m.has_article, m.optlock, m.location, (SELECT string_agg(tbl.name, ' > ') AS location_text " +
            "           FROM ( WITH RECURSIVE rec AS (" +
            "                         SELECT tree.id," +
            "                            tree.name," +
            "                            tree.parent," +
            "                            tree.level" +
            "                           FROM r_structure tree" +
            "                          WHERE tree.id = m.id" +
            "                        UNION ALL" +
            "                         SELECT tree.id," +
            "                            tree.name," +
            "                            tree.parent," +
            "                            tree.level" +
            "                           FROM rec rec_1," +
            "                            r_structure tree" +
            "                          WHERE tree.id = rec_1.parent" +
            "                        )" +
            "                 SELECT rec.id," +
            "                    rec.name," +
            "                    rec.level," +
            "                    1 AS grouper" +
            "                   FROM rec" +
            "                  ORDER BY rec.level) tbl" +
            "          GROUP BY tbl.grouper) AS location_text" +
            "          FROM (SELECT m.* FROM (SELECT m.* FROM r_structure m " +
            "            LEFT JOIN r_structure m2 ON m2.parent = m.id " +
            "            WHERE m.deleted is false " +
            "            ORDER BY m.id ASC) m " +
            "            WHERE m.parent IN ( " +
            "            SELECT m.id FROM r_structure m " +
            "            LEFT JOIN r_structure m2 ON m2.parent = m.id " +
            "            WHERE m.deleted is false " +
            "            ORDER BY m.id ASC) " +
            "            UNION " +
            "            SELECT m.* FROM r_structure m WHERE m.deleted is false and m.parent = 0" +
            "            ) m" +
            "          ORDER BY m.id " +
            "          ASC",
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

    @Query(value="SELECT m FROM Structure m WHERE m.parentStructure=:parentId AND m.deleted IS FALSE ")
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
    List<Structure>  findParentListById(@Param("id") Long id);

    @Query(value = "SELECT rs3.* FROM (  " +
            " WITH RECURSIVE rec AS ( " +
            "     SELECT rs.* " +
            "                           FROM r_structure rs " +
            "                          WHERE rs.id = :id " +
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
    List<Structure>  findBreadcumbById(@Param("id") Long id);
}
