package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.Article;
import id.co.bca.pakar.be.doc.model.ArticleContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "SELECT m.* FROM t_article m " +
            "    WHERE m.deleted IS FALSE " +
            "    AND m.state = 'PUBLISHED' " +
            "    AND m.id <> :id " +
            "    AND (lower(m.title) LIKE lower(concat('%', :keyword,'%')))",
            nativeQuery = true
    )
    Page<Article> findRelatedArticles(@Param("id") Long id, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Article m " +
            "WHERE m.articleState = 'PREDRAFT'" +
            "AND m.deleted IS FALSE " +
            "AND m.id=:id")
    Boolean isPreDraftArticle(@Param("id") Long id);

    // find article for contents page role ADMIN
    @Query(value = "SELECT m FROM Article m WHERE  m.articleState != 'PENDING' AND m.deleted IS FALSE " +
            "AND (LOWER(m.judulArticle) LIKE CONCAT('%',LOWER(:keyword),'%') " +
            "OR LOWER(m.fullNameModifier) LIKE CONCAT('%', LOWER(:keyword), '%') " +
            "OR LOWER(m.structure.location_text) LIKE CONCAT('%',LOWER(:keyword), '%') )")
    Page<Article> findContentArticleForAdmin(@Param("keyword") String keyword, Pageable pageable);

    // find article for contents page role except ADMIN
    @Query(value = "SELECT m FROM Article m WHERE  m.articleState = 'PUBLISHED' AND m.deleted IS FALSE " +
            "AND (LOWER(m.judulArticle) LIKE CONCAT('%',LOWER(:keyword),'%') " +
            "OR LOWER(m.fullNameModifier) LIKE CONCAT('%', LOWER(:keyword), '%') " +
            "OR LOWER(m.structure.location_text) LIKE CONCAT('%',LOWER(:keyword), '%') ) ")
    Page<Article> findContentArticle(@Param("keyword") String keyword, Pageable pageable);

}
