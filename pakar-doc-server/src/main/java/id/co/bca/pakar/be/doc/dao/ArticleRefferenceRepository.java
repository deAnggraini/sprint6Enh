package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.Article;
import id.co.bca.pakar.be.doc.model.RefferenceArticle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRefferenceRepository extends CrudRepository<RefferenceArticle, Long> {
    @Query("SELECT m.reffArticle FROM RefferenceArticle m " +
            "WHERE m.sourceArticle.id=:articleId " +
            "AND m.deleted IS FALSE " +
            "AND m.reffArticle.deleted IS FALSE " +
            "AND m.sourceArticle.deleted IS FALSE " +
            "ORDER BY m.reffArticle.id ASC ")
    Iterable<Article> findByArticleId(@Param("articleId") Long articleId);
}
