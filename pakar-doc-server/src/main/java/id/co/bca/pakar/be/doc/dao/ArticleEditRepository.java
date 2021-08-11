package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ArticleEdit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleEditRepository extends CrudRepository<ArticleEdit, Long> {
    @Query(
        "SELECT m FROM ArticleEdit m " +
                "WHERE m.article.id=:articleId " +
                "AND m.status IS TRUE " +
                "AND m.deleted IS FALSE "
    )
    Iterable<ArticleEdit> findArticleInEditingStatus(@Param("articleId") Long articleId);


    @Query("select m from ArticleEdit m where m.article.id=:articleId ")
    ArticleEdit findCurrentEdit(@Param("articleId") Long articleId);
}
