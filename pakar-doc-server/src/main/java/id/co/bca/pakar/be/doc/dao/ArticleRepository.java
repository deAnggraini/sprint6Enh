package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Article m WHERE LOWER(TRIM(m.judulArticle))=LOWER(TRIM(:title)) AND m.deleted IS FALSE")
    Boolean existByArticleTitle(@Param("title") String title);

    @Query("SELECT m FROM Article m " +
            "WHERE m.id=:id " +
            "AND m.deleted IS FALSE ")
    Optional<Article> findById(@Param("id") Long id);
}