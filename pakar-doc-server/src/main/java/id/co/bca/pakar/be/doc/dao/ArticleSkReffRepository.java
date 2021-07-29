package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ArticleSkReff;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleSkReffRepository extends CrudRepository<ArticleSkReff, Long> {
    @Query("SELECT m FROM ArticleSkReff m " +
            "WHERE m.article.id=:articleId " +
            "AND m.deleted IS FALSE " +
            "AND m.skRefference.deleted IS FALSE " +
            "AND m.article.deleted IS FALSE " +
            "ORDER BY m.id ASC ")
    Iterable<ArticleSkReff> findByArticleId(@Param("articleId") Long articleId);
}
