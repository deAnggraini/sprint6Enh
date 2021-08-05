package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ArticleMyPagesRepository extends CrudRepository<Article, Long> {
    @Query("select tsa.article from SuggestionArticle tsa " +
            "where tsa.article.id <> :id " +
            "and lower(tsa.article.judulArticle) like lower(concat('%', :keyword,'%')) " +
            "and tsa.article.articleState = 'PUBLISHED' order by  tsa.article.judulArticle, tsa.hit_count"
    )
    Page<Article> findMyPagesDraft(@Param("id") Long id, @Param("keyword") String keyword, Pageable pageable);

}
