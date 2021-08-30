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
public interface ArticleVersionRepository extends CrudRepository<ArticleVersion, String> {
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

    // find article for contents page role ADMIN
    @Query("SELECT m FROM ArticleVersion m " +
            "WHERE m.deleted IS FALSE " +
            "AND (LOWER(m.judulArticle) LIKE lower(concat('%', :keyword,'%')) " +
            "OR LOWER(m.fullNameModifier) LIKE lower(concat('%', :keyword,'%')) )")
    Page<ArticleVersion> findContentArticleForAdmin(@Param("keyword") String keyword, Pageable pageable);

    // find article for contents page role except ADMIN
    @Query("SELECT m FROM ArticleVersion m " +
            "WHERE m.isPublished IS TRUE " +
            "AND m.deleted IS FALSE " +
            "AND (LOWER(m.judulArticle) LIKE lower(concat('%', :keyword,'%')) " +
            "OR LOWER(m.fullNameModifier) LIKE lower(concat('%', :keyword,'%')) ) ")
    Page<ArticleVersion> findContentArticle(@Param("keyword") String keyword, Pageable pageable);

    // find selain article for contents page
    @Query("select m from ArticleVersion m where id not in (select id from Article m ) ")
    Page<ArticleVersion> findContentExceptArticle(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT m FROM ArticleVersion m " +
            "WHERE m.isPublished IS TRUE " +
            "AND m.structure = :structureId " +
            "AND m.deleted IS FALSE " +
            "AND (LOWER(m.judulArticle) LIKE lower(concat('%', :keyword,'%')) " +
            "OR LOWER(m.fullNameModifier) LIKE lower(concat('%', :keyword,'%')) ) " +
            "ORDER BY m.modifyDate ")
    Page<ArticleVersion> findPublishedArticles(@Param("structureId") Long structureId, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT m FROM ArticleVersion m " +
            "WHERE m.isPublished IS TRUE " +
            "AND m.deleted IS FALSE " +
            "ORDER BY m.modifyDate ")
    Page<ArticleVersion> findPublishedArticles(Pageable pageable);

    @Query(
            "SELECT m FROM ArticleVersion m " +
                    " WHERE m.judulArticle = :judulArticle " +
                    " AND m.id = (SELECT MAX(m.id) FROM ArticleVersion m " +
                    "               WHERE m.judulArticle = :judulArticle " +
                    "               AND m.publishedVersion IS NOT NULL " +
                    "               AND m.deleted IS FALSE " +
                    ")"
    )
    ArticleVersion findLastPublished(@Param("judulArticle") String judulArticle);

    @Query("SELECT m FROM ArticleVersion m " +
            "WHERE m.timeStampVersion IS NOT NULL " +
            "AND m.judulArticle = :judulArticle " +
            "AND m.createdBy=:username " +
            "AND m.id = (SELECT MAX(m.id) FROM ArticleVersion m " +
            "           WHERE m.judulArticle = :judulArticle " +
            "           AND m.timeStampVersion IS NOT NULL " +
            "           AND m.deleted IS FALSE " +
            ")"
    )
    ArticleVersion findLastTimeStampVersion(@Param("judulArticle") String judulArticle, @Param("username") String username);

    @Query("SELECT m FROM ArticleVersion m " +
            "WHERE m.timeStampVersion IS NOT NULL " +
            "AND m.articleId=:articleId " +
            "AND m.fullNameModifier=:username " +
            "AND m.deleted IS FALSE " +
            "ORDER BY m.timeStampVersion " +
            "DESC ")
    ArticleVersion findLastTimeStampByFnModifier(@Param("articleId") Long articleId, @Param("username") String username);
}
