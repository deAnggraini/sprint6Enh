package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ArticleImage;
import id.co.bca.pakar.be.doc.model.Images;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleImageRepository extends CrudRepository<ArticleImage, Long> {
    @Query("SELECT m.image FROM ArticleImage m " +
            "WHERE m.article.id=:articleId " +
            "AND m.image.deleted IS FALSE " +
            "AND m.article.deleted IS FALSE " +
            "AND m.deleted IS FALSE ")
    Optional<Images> findByArticleId(@Param("articleId") Long articleId);

    @Query("SELECT m FROM ArticleImage m " +
            "WHERE m.article.id=:articleId " +
            "AND m.image.deleted IS FALSE " +
            "AND m.article.deleted IS FALSE " +
            "AND m.deleted IS FALSE ")
    Iterable<ArticleImage> findArticleImagesByArticleId(@Param("articleId") Long articleId);
}
