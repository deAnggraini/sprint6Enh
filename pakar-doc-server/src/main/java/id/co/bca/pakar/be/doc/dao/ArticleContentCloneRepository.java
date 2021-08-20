package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ArticleContentClone;
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
            "AND m.article.id = :id " +
            "AND m.createdBy = :username")
    Iterable<ArticleContentClone> findsByArticleId(@Param("articleId") Long articleId, @Param("username") String username);

    @Modifying
    @Query("DELETE FROM ArticleContentClone m " +
            "WHERE m.id IN (:ids) " +
            "AND m.createdBy = :username")
    int deleteByIds(@Param("username") String username, @Param("ids") List<Long> ids);

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
            "WHERE tac.created_by = :username " +
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
}
