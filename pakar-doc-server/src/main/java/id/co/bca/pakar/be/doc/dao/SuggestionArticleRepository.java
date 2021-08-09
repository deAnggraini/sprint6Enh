package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.Article;
import id.co.bca.pakar.be.doc.model.EntityBase;
import id.co.bca.pakar.be.doc.model.SuggestionArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionArticleRepository extends CrudRepository<Article, Long> {

    @Query("select tsa.article from SuggestionArticle tsa " +
            "where tsa.article.id <> :id " +
            "and lower(tsa.article.judulArticle) like lower(concat('%', :keyword,'%')) " +
            "and tsa.article.articleState = 'PUBLISHED' order by  tsa.article.judulArticle, tsa.hit_count"
    )
    Page<Article> findSuggestionArticle(@Param("id") Long id, @Param("keyword") String keyword, Pageable pageable);

    @Query("select tsa.article from SuggestionArticle tsa " +
            "where tsa.article.id NOT IN (:ids) " +
            "and lower(tsa.article.judulArticle) like lower(concat('%', :keyword,'%')) " +
            "and tsa.article.articleState = 'PUBLISHED' and tsa.article.structure.id =:structureId " +
            "order by tsa.hit_count desc, tsa.article.judulArticle asc"
    )
    Page<Article> findSuggestionArticles(@Param("ids") List<Long> ids, @Param("keyword") String keyword, @Param("structureId") Long structureId, Pageable pageable);

    @Query("select tsa.article from SuggestionArticle tsa " +
            "where tsa.article.id NOT IN (:ids) " +
            "and tsa.article.articleState = 'PUBLISHED' and tsa.article.structure.id =:structureId order by tsa.hit_count desc, tsa.article.judulArticle asc"
    )
    Page<Article> findSuggestionArticleWithoutKey(@Param("ids") List<Long> ids, @Param("structureId") Long structureId, Pageable pageable);

}
