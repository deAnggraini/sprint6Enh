package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Article m " +
            "WHERE LOWER(TRIM(m.judulArticle))=LOWER(TRIM(:title)) " +
            "AND m.deleted IS FALSE " +
            "AND m.id <> :exludeArticleId ")
    Boolean existByArticleTitle(@Param("title") String title, @Param("exludeArticleId") Long exludeArticleId);

    @Query("SELECT m FROM Article m " +
            "WHERE m.id=:id " +
            "AND m.deleted IS FALSE ")
    Optional<Article> findById(@Param("id") Long id);

    @Query("SELECT m FROM Article m " +
            "WHERE m.id=:id " +
            "AND m.modifyBy = :username " +
            "AND m.deleted IS FALSE ")
    Optional<Article> findById(@Param("id") Long id, @Param("username") String username);

    @Query(value = "SELECT m.* FROM t_article m " +
            "    WHERE m.deleted IS FALSE " +
            "    AND m.state = 'PUBLISHED' " +
            "    AND m.id <> :id " +
            "    AND (lower(m.title) LIKE lower(concat('%', :keyword,'%')))",
            nativeQuery = true
    )
    Page<Article> findRelatedArticles(@Param("id") Long id, @Param("keyword") String keyword, Pageable pageable);

//    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Article m " +
//            "WHERE m.articleState = 'NEW'" +
//            "AND m.deleted IS FALSE " +
//            "AND m.id=:id")
//    Boolean isPreDraftArticle(@Param("id") Long id);
//
//
//    // find selain article for contents page
//    @Query("select m from Article m where id not in (select id from Article m ) ")
//    Page<Article> findContentExceptArticle(@Param("keyword") String keyword, Pageable pageable);
}
