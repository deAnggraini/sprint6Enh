package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ArticleSkReff;
import id.co.bca.pakar.be.doc.model.SkRefference;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SkReffRepository extends CrudRepository<ArticleSkReff, Long> {
    @Query("SELECT m.skRefference FROM ArticleSkReff m " +
            "WHERE m.article.id=:articleId " +
            "AND m.deleted IS FALSE " +
            "AND m.skRefference.deleted IS FALSE " +
            "AND m.article.deleted IS FALSE " +
            "ORDER BY m.skRefference.id ASC ")
    Iterable<SkRefference> findByArticleId(@Param("articleId") Long articleId);
}
