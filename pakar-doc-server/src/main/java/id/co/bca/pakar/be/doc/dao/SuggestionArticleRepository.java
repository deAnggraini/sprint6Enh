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

@Repository
public interface SuggestionArticleRepository extends CrudRepository<Article, Long> {
    @Query(value = "select tsa.*, ta.title from t_article ta " +
            "join t_suggestion_article tsa on tsa.article_id = ta.id join r_structure rs " +
            "on tsa.structure_id = rs.id " +
            "where ta.id <> :id " +
            "and lower(ta.title) like lower(concat('%', :keyword,'%')) " +
            "and tsa.structure.id =:structure_id " +
            "and ta.state = 'PUBLISHED' order by tsa.hit_count, ta.title asc",
            nativeQuery = true
    )
    Page<Article> findSuggestionArticle(@Param("id") Long id, @Param("keyword") String keyword, @Param("structure_id") Long structure_id, Pageable pageable);

//    @Query(value = "select tsa.*, ta.title from t_article ta " +
//            "join t_suggestion_article tsa on tsa.article_id = ta.id join r_structure rs " +
//            "on tsa.structure_id = rs.id " +
//            "where ta.id <> :id " +
//            "and lower(ta.title) like lower(concat('%', :keyword,'%')) " +
//            "and tsa.structure.id =:structure_id " +
//            "and ta.state = 'PUBLISHED' order by tsa.hit_count, ta.title asc",
//            nativeQuery = true
//    )
//    Page<Article> findSuggestionArticle(@Param("id") Long id, @Param("keyword") String keyword, Pageable pageable);



}
