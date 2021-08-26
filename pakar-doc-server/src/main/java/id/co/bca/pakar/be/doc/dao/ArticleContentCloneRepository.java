package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ArticleContentClone;
import id.co.bca.pakar.be.doc.model.Structure;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleContentCloneRepository extends CrudRepository<ArticleContentClone, Long> {
    @Query(value = "SELECT m FROM ArticleContentClone m " +
            "WHERE m.deleted IS FALSE " +
            "AND m.id=:id ")
    Optional<ArticleContentClone> findById(@Param("id") Long id);

    @Query(value = "SELECT m FROM ArticleContentClone m " +
            "WHERE m.deleted IS FALSE " +
            "AND m.id=:id " +
            "AND m.createdBy = :username")
    Optional<ArticleContentClone> findById(@Param("id") Long id, @Param("username") String username);

    @Query(value = "SELECT m FROM ArticleContentClone m " +
            "WHERE m.deleted IS FALSE " +
            "AND m.article.id = :articleId " +
            "AND m.createdBy = :username")
    Iterable<ArticleContentClone> findsByArticleId(@Param("articleId") Long articleId, @Param("username") String username);

    @Modifying
    @Query("DELETE FROM ArticleContentClone m " +
            "WHERE m.id IN (:ids) " +
            "AND m.createdBy = :username")
    int deleteByIds(@Param("username") String username, @Param("ids") List<Long> ids);

    @Modifying
    @Query("DELETE FROM ArticleContentClone m " +
            "WHERE m.id NOT IN (:ids) " +
            "AND m.createdBy = :username")
    int deleteByNotInIds(@Param("username") String username, @Param("ids") List<Long> ids);

    @Query(value = "SELECT tac_.* FROM ( " +
            "WITH RECURSIVE rec as " +
            "( " +
            "  SELECT tac.* FROM t_article_content_clone tac WHERE tac.id= :parentId " +
            "  UNION ALL " +
            "  SELECT tac.* FROM rec, t_article_content_clone tac WHERE tac.parent = rec.id " +
            ") " +
            "SELECT * " +
            "FROM rec " +
            ") tac_ " +
            "WHERE tac_.created_by = :username " +
            "ORDER BY tac_.id " +
            "ASC ",
            nativeQuery = true)
    List<ArticleContentClone> findChildsIncludeParent(@Param("parentId") Long parentId, @Param("username") String username);

    @Query(value = "SELECT tac_.* FROM ( " +
            "WITH RECURSIVE rec as " +
            "( " +
            "  SELECT tac.* FROM t_article_content tac WHERE tac.id= :parentId " +
            "  UNION ALL " +
            "  SELECT tac.* FROM rec, t_article_content tac WHERE tac.parent = rec.id " +
            ") " +
            "SELECT * " +
            "FROM rec " +
            ") tac_ " +
            "ORDER BY tac_.id " +
            "ASC ",
            nativeQuery = true)
    List<ArticleContentClone> findChildsIncludeParent(@Param("parentId") Long parentId);

    @Query(value = "SELECT rs3.* FROM (  " +
            " WITH RECURSIVE rec AS ( " +
            "     SELECT rs.* " +
            "                           FROM t_article_content_clone rs " +
            "                          WHERE rs.id = (select rs2.parent from t_article_content_clone rs2 where rs2.id= :id) " +
            "                        UNION ALL " +
            "                         SELECT rs.* " +
            "                           FROM rec rec1, " +
            "                            t_article_content_clone rs " +
            "                          WHERE rs.id = rec1.parent " +
            "                        ) " +
            "                 SELECT rec.* " +
            "                 FROM rec " +
            "                 ORDER BY rec.level " +
            " ) rs3",
            nativeQuery = true)
    List<ArticleContentClone>  findParentListById(@Param("id") Long id);

    @Query(value = "SELECT rs3.* FROM (  " +
            " WITH RECURSIVE rec AS ( " +
            "     SELECT tacc.* " +
            "                           FROM t_article_content_clone tacc " +
            "                          WHERE tacc.id = :id " +
            "                        UNION ALL " +
            "                         SELECT tacc.* " +
            "                           FROM rec rec1, " +
            "                            t_article_content_clone tacc " +
            "                          WHERE tacc.id = rec1.parent " +
            "                        ) " +
            "                 SELECT rec.* " +
            "                 FROM rec " +
            "                 ORDER BY rec.level " +
            " ) rs3",
            nativeQuery = true)
    List<ArticleContentClone>  findBreadcumbById(@Param("id") Long id);
}
