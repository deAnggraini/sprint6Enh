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
    @Query(value="select n.* FROM (\n" +
            "          SELECT m.id, m.created_by, m.created_date, m.deleted, m.modify_by, m.modify_date, m.edit, m.level, m.parent, m.sort,\n" +
            "           m.description, m.name, m.uri, m.has_article, m.optlock, m.breadcumb, m.location, (SELECT string_agg(tbl.name, ' > ') AS location_text\n" +
            "           \t\t\t\t\tFROM ( WITH RECURSIVE rec as (\n" +
            "\t\t                         SELECT tree.id,\n" +
            "\t\t                            tree.name,\n" +
            "\t\t                            tree.parent,\n" +
            "\t\t                            tree.level\n" +
            "\t\t                           FROM r_structure tree\n" +
            "\t\t                          WHERE tree.id = m.id\n" +
            "\t\t                        UNION ALL\n" +
            "\t\t                         SELECT tree.id,\n" +
            "\t\t                            tree.name,\n" +
            "\t\t                            tree.parent,\n" +
            "\t\t                            tree.level\n" +
            "\t\t                           FROM rec rec_1,\n" +
            "\t\t                            r_structure tree\n" +
            "\t\t                          WHERE tree.id = rec_1.parent\n" +
            "\t\t                        )\n" +
            "\t\t                 SELECT rec.id,\n" +
            "\t\t                    rec.name,\n" +
            "\t\t                    rec.level,\n" +
            "\t\t                    1 AS grouper\n" +
            "\t\t                   FROM rec\n" +
            "\t\t                  ORDER BY rec.level) tbl\n" +
            "\t\t          GROUP BY tbl.grouper) AS location_text\n" +
            "          FROM (SELECT m.* FROM (SELECT m.* FROM r_structure m \n" +
            "\t\t            LEFT JOIN r_structure m2 ON m2.parent = m.id \n" +
            "\t\t            WHERE m.deleted is false \n" +
            "\t\t            ORDER BY m.id ASC) m \n" +
            "\t\t            WHERE m.parent IN ( \n" +
            "\t\t            SELECT m.id FROM r_structure m \n" +
            "\t\t            LEFT JOIN r_structure m2 ON m2.parent = m.id \n" +
            "\t\t            WHERE m.deleted is false \n" +
            "\t\t            ORDER BY m.id ASC) \n" +
            "\t\t            UNION \n" +
            "\t\t            SELECT m.* FROM r_structure m WHERE m.deleted is false and m.parent = 0\n" +
            "            ) m\n" +
            "          ORDER BY m.id \n" +
            "          asc\n" +
            "          ) n",
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
