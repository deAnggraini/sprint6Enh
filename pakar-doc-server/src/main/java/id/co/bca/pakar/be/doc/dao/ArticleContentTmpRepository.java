package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ArticleContentTmp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleContentTmpRepository extends CrudRepository<ArticleContentTmp, Long> {
    @Query(value = "SELECT nextval('public.article_content_tmp_seq') ",
            nativeQuery = true)
    Long getContentId();

    @Query(value = "SELECT tac.* " +
            "FROM t_article_content_tmp AS tac " +
            "INNER JOIN " +
            "  (SELECT tac.id FROM t_article_content_tmp tac WHERE tac.parent = :parentId) AS tac2 " +
            "  ON tac2.id = tac.parent OR tac.parent = :parentId " +
            "GROUP BY tac.id, tac2.id  " +
            "ORDER BY tac.id, tac.parent, tac.level ASC ",
            nativeQuery = true)
    List<ArticleContentTmp> findArticleContentChildrenByParentId(@Param("parentId") Long parentId);

    @Query(value = "SELECT tac.* FROM ( " +
            "SELECT tac.*  " +
            "            FROM t_article_content_tmp AS tac  " +
            "            INNER JOIN  " +
            "              (SELECT tac.id FROM t_article_content_tmp tac WHERE tac.parent=:parentId) AS tac2  " +
            "              ON tac2.id = tac.parent OR tac.parent = :parentId  " +
            "            GROUP BY tac.id, tac2.id   " +
            "UNION  " +
            "SELECT tac.* FROM t_article_content_tmp AS tac  " +
            "WHERE tac.id = :parentId " +
            ") tac " +
            "ORDER BY tac.id, tac.parent, tac.level ASC",
            nativeQuery = true)
    List<ArticleContentTmp> findContentChildrenAndOwnRowByParentId(@Param("parentId") Long parentId);

    @Query(value = "with recursive tac_parent (id, parent_id) AS (" +
            "  SELECT tac.id, tac.parent, tac.name " +
            "  FROM t_article_content_tmp tac " +
            "  WHERE tac.id = :id " +
            "  UNION ALL " +
            "  SELECT tac2.id, tac2.parent, tac2.name " +
            "  FROM t_article_content_tmp tac2 INNER JOIN t_article_content_tmp tac3 " +
            "  ON tac2.id = tac3.parent " +
            ") " +
            "SELECT tacp.* FROM tac_parent tacp group by tacp.id, tacp.parent_id, tacp.name",
            nativeQuery = true)
    List<ArticleContentTmp> findArticleContentParent(@Param("id") Long id);

    @Query(value = "SELECT m FROM ArticleContentTmp m " +
            "WHERE m.deleted IS FALSE " +
            "AND m.id=:id ")
    Optional<ArticleContentTmp> findById(@Param("id") Long id);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM ArticleContentTmp m " +
            "WHERE m.deleted IS FALSE " +
            "AND m.id=:id ")
    boolean existsById(@Param("id") Long id);

    @Query(value = "SELECT m FROM ArticleContentTmp m " +
            "WHERE m.article.id=:articleId " +
            "AND m.deleted IS FALSE ")
    Iterable<ArticleContentTmp> findByArticleId(@Param("articleId") Long articleId);
}
