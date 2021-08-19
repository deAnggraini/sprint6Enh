package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ArticleVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleVersionRepository extends CrudRepository<ArticleVersion, Long> {
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM ArticleVersion m WHERE LOWER(TRIM(m.judulArticle))=LOWER(TRIM(:title)) AND m.deleted IS FALSE")
    Boolean existByArticleTitle(@Param("title") String title);

    @Query("SELECT m FROM ArticleVersion m " +
            "WHERE m.id=:id " +
            "AND m.deleted IS FALSE ")
    Optional<ArticleVersion> findById(@Param("id") Long id);

    @Query(value = "SELECT m.* FROM t_article_version m " +
            "    WHERE m.deleted IS FALSE " +
            "    AND m.state = 'PUBLISHED' " +
            "    AND m.id <> :id " +
            "    AND (lower(m.title) LIKE lower(concat('%', :keyword,'%')))",
            nativeQuery = true
    )
    Page<ArticleVersion> findRelatedArticles(@Param("id") Long id, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM ArticleVersion m " +
            "WHERE m.articleState = 'NEW'" +
            "AND m.deleted IS FALSE " +
            "AND m.id=:id")
    Boolean isPreDraftArticle(@Param("id") Long id);

    // find article for contents page role ADMIN
    @Query("SELECT m FROM ArticleVersion m " +
            "WHERE m.deleted IS FALSE " +
            "AND (LOWER(m.judulArticle) LIKE lower(concat('%', :keyword,'%')) " +
            "OR LOWER(m.fullNameModifier) LIKE lower(concat('%', :keyword,'%')) )")
    Page<ArticleVersion> findContentArticleForAdmin(@Param("keyword") String keyword, Pageable pageable);

    // find article for contents page role except ADMIN
    @Query("SELECT m FROM ArticleVersion m " +
            "WHERE  m.articleState = 'PUBLISHED' AND m.deleted IS FALSE " +
            "AND (LOWER(m.judulArticle) LIKE lower(concat('%', :keyword,'%')) " +
            "OR LOWER(m.fullNameModifier) LIKE lower(concat('%', :keyword,'%')) ) ")
    Page<ArticleVersion> findContentArticle(@Param("keyword") String keyword, Pageable pageable);

    // find selain article for contents page
    @Query("select m from ArticleVersion m where id not in (select id from Article m ) ")
    Page<ArticleVersion> findContentExceptArticle(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT m FROM ArticleVersion m " +
            "WHERE  m.articleState = 'PUBLISHED' " +
            "AND m.structure = :structureId " +
            "AND m.deleted IS FALSE " +
            "AND (LOWER(m.judulArticle) LIKE lower(concat('%', :keyword,'%')) " +
            "OR LOWER(m.fullNameModifier) LIKE lower(concat('%', :keyword,'%')) ) " +
            "ORDER BY m.modifyDate ")
    Page<ArticleVersion> findPublishedArticles(@Param("structureId") Long structureId, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT m FROM ArticleVersion m " +
            "WHERE  m.articleState = 'PUBLISHED' " +
            "AND m.deleted IS FALSE " +
            "ORDER BY m.modifyDate ")
    Page<ArticleVersion> findPublishedArticles(Pageable pageable);
}
