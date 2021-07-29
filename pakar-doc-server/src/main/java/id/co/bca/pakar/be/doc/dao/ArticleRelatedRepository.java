package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.Article;
import id.co.bca.pakar.be.doc.model.RelatedArticle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRelatedRepository extends CrudRepository<RelatedArticle, Long> {
    @Query("SELECT m.relatedArticle FROM RelatedArticle m " +
            "WHERE m.sourceArticle.id=:articleId " +
            "AND m.deleted IS FALSE " +
            "AND m.relatedArticle.deleted IS FALSE " +
            "AND m.sourceArticle.deleted IS FALSE " +
            "ORDER BY m.id ASC ")
    Iterable<Article> findByArticleId(@Param("articleId") Long articleId);

    @Query("SELECT m FROM RelatedArticle m " +
            "WHERE m.sourceArticle.id=:articleId " +
            "AND m.deleted IS FALSE " +
            "AND m.relatedArticle.deleted IS FALSE " +
            "AND m.sourceArticle.deleted IS FALSE " +
            "ORDER BY m.relatedArticle.id ASC ")
    Iterable<RelatedArticle> findRelatedArticleByArticleId(@Param("articleId") Long articleId);
}
