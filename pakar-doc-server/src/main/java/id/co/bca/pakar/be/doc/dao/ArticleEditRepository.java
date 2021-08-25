package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ArticleEdit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleEditRepository extends CrudRepository<ArticleEdit, Long> {
    @Query(
        "SELECT m FROM ArticleEdit m " +
                "WHERE m.article.id=:articleId " +
                "AND m.status IS TRUE " +
                "AND m.deleted IS FALSE "
    )
    List<ArticleEdit> findArticleInEditingStatus(@Param("articleId") Long articleId);

    @Query("select m from ArticleEdit m where m.article.id=:articleId ")
    ArticleEdit findCurrentEdit(@Param("articleId") Long articleId);

    @Query("SELECT m FROM ArticleEdit m " +
            "WHERE m.article.id=:articleId " +
            "AND m.deleted IS FALSE " +
            "AND m.status IS TRUE " +
            "GROUP BY m.username " +
            "ORDER BY m.id " +
            "DESC")
    List<ArticleEdit> findTopByOrderByIdDesc(@Param("articleId") Long articleId);

    @Query(
            "SELECT m FROM ArticleEdit m " +
                    "WHERE m.article.id=:articleId " +
                    "AND m.username=:username " +
                    "AND m.deleted IS FALSE "
    )
    ArticleEdit findByUsername(@Param("articleId") Long articleId, @Param("username") String username);

    @Query(
            "SELECT m FROM ArticleEdit m " +
                    "WHERE m.article.id=:articleId " +
                    "AND m.username=:username " +
                    "AND m.status IS TRUE " +
                    "AND m.deleted IS FALSE "
    )
    ArticleEdit findActiveEditingStatusByUsername(@Param("articleId") Long articleId, @Param("username") String username);

    @Query("SELECT m FROM ArticleEdit m " +
            "WHERE m.article.id=:articleId " +
            "AND m.deleted IS FALSE ")
    List<ArticleEdit> findByArticleId(@Param("articleId") Long articleId);
}
