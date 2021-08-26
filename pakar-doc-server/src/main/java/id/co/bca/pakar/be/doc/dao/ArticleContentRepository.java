package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ArticleContent;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleContentRepository extends CrudRepository<ArticleContent, Long> {
    @Query(value = "SELECT nextval('public.article_content_seq') ",
            nativeQuery = true)
    Long getContentId();

    @Query(value = "SELECT tac.* " +
            "FROM t_article_content AS tac " +
            "INNER JOIN " +
            "  (SELECT tac.id FROM t_article_content tac WHERE tac.parent = :parentId) AS tac2 " +
            "  ON tac2.id = tac.parent OR tac.parent = :parentId " +
            "GROUP BY tac.id, tac2.id  " +
            "ORDER BY tac.id, tac.parent, tac.level ASC ",
            nativeQuery = true)
    List<ArticleContent> findArticleContentChildrenByParentId(@Param("parentId") Long parentId);

    @Query(value = "SELECT tac.* FROM ( " +
            "SELECT tac.*  " +
            "            FROM t_article_content AS tac  " +
            "            INNER JOIN  " +
            "              (SELECT tac.id FROM t_article_content tac WHERE tac.parent=:parentId) AS tac2  " +
            "              ON tac2.id = tac.parent OR tac.parent = :parentId  " +
            "            GROUP BY tac.id, tac2.id   " +
            "UNION  " +
            "SELECT tac.* FROM t_article_content AS tac  " +
            "WHERE tac.id = :parentId " +
            ") tac " +
            "ORDER BY tac.id, tac.parent, tac.level ASC",
            nativeQuery = true)
    List<ArticleContent> findContentChildrenAndOwnRowByParentId(@Param("parentId") Long parentId);

    @Query(value = "with recursive tac_parent (id, parent_id) AS (" +
            "  SELECT tac.id, tac.parent, tac.name " +
            "  FROM t_article_content tac " +
            "  WHERE tac.id = :id " +
            "  UNION ALL " +
            "  SELECT tac2.id, tac2.parent, tac2.name " +
            "  FROM t_article_content tac2 INNER JOIN t_article_content tac3 " +
            "  ON tac2.id = tac3.parent " +
            ") " +
            "SELECT tacp.* FROM tac_parent tacp group by tacp.id, tacp.parent_id, tacp.name",
            nativeQuery = true)
    List<ArticleContent> findArticleContentParent(@Param("id") Long id);

    @Query(value = "SELECT m FROM ArticleContent m " +
            "WHERE m.deleted IS FALSE " +
            "AND m.id=:id ")
    Optional<ArticleContent> findById(@Param("id") Long id);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM ArticleContent m " +
            "WHERE m.deleted IS FALSE " +
            "AND m.id=:id ")
    boolean existsById(@Param("id") Long id);

    @Query(value = "SELECT m FROM ArticleContent m " +
            "WHERE m.article.id=:articleId " +
            "AND m.deleted IS FALSE ")
    List<ArticleContent> findByArticleId(@Param("articleId") Long articleId);

    @Query(value = "SELECT rs3.* FROM (  " +
            " WITH RECURSIVE rec AS ( " +
            "     SELECT rs.* " +
            "                           FROM t_article_content rs " +
            "                          WHERE rs.id = (select rs2.parent from t_article_content rs2 where rs2.id= :id) " +
            "                        UNION ALL " +
            "                         SELECT rs.* " +
            "                           FROM rec rec1, " +
            "                            t_article_content rs " +
            "                          WHERE rs.id = rec1.parent " +
            "                        ) " +
            "                 SELECT rec.* " +
            "                 FROM rec " +
            "                 ORDER BY rec.level " +
            " ) rs3",
            nativeQuery = true)
    List<ArticleContent> findParentListById(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM ArticleContent m " +
            "WHERE m.id NOT IN (:ids) " +
            "AND m.article.id=:articleId ")
    int deleteByNotInIds(@Param("ids") List<Long> ids, @Param("articleId") Long articleId);
}
