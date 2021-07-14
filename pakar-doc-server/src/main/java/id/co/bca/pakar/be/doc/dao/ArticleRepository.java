package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Article m WHERE LOWER(m.judulArticle)=LOWER(:title) AND m.deleted IS FALSE")
    Boolean existByArticleTitle(@Param("title") String title);
}
