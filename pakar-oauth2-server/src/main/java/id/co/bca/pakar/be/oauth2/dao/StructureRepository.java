package id.co.bca.pakar.be.oauth2.dao;

import id.co.bca.pakar.be.oauth2.model.Structure;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StructureRepository extends CrudRepository<Structure, Long> {
    @Query(value="SELECT m.id, m.created_by, m.created_date, m.deleted, m.modify_by, m.modify_date, m.edit, m.level, m.parent, m.sort," +
            "           m.description, m.name, m.uri, m.has_article, m.optlock, m.breadcumb, m.location, (SELECT string_agg(tbl.name::text, ' > '::text) AS location_text" +
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
}
