package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.Article;
import id.co.bca.pakar.be.doc.model.ArticleState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleStateRepository extends CrudRepository<ArticleState, Long> {
//    @Query("SELECT m.article FROM ArticleState m WHERE m.article.id IN (:ids) " +
//            "AND m.deleted IS FALSE " +
//            "AND m.state = :state " +
//            "AND (LOWER(m.article.judulArticle) LIKE CONCAT('%',LOWER(:keyword),'%') " +
//            "       OR LOWER(m.article.fullNameModifier) LIKE CONCAT('%', LOWER(:keyword), '%') " +
//            "       OR LOWER(m.article.structure.location_text) LIKE CONCAT('%',LOWER(:keyword), '%') ) ")
//    Page<Article> findMyPagesArticle(@Param("ids") List<Long> ids, @Param("keyword") String keyword, @Param("state") String state, Pageable pageable);

    @Query("SELECT m.article FROM ArticleState m WHERE m.article.id IN (:ids) " +
            "AND m.deleted IS FALSE " +
            "AND m.receiverState = :state " +
            "AND m.receiver=:username " +
            "AND (LOWER(m.article.judulArticle) LIKE CONCAT('%',LOWER(:keyword),'%') " +
            "       OR LOWER(m.article.fullNameModifier) LIKE CONCAT('%', LOWER(:keyword), '%') " +
            "       OR LOWER(m.article.structure.location_text) LIKE CONCAT('%',LOWER(:keyword), '%') ) ")
    Page<Article> findMyPagesDratfArticle(@Param("ids") List<Long> ids, @Param("keyword") String keyword, @Param("username") String username, @Param("state") String state, Pageable pageable);

    @Query("SELECT m.article FROM ArticleState m " +
            "WHERE m.article.id IN (:ids) " +
            "AND m.deleted IS FALSE " +
            "AND m.senderState = :state " +
            "AND m.sender=:username " +
            "AND (LOWER(m.article.judulArticle) LIKE CONCAT('%',LOWER(:keyword),'%') " +
            "       OR LOWER(m.article.fullNameModifier) LIKE CONCAT('%', LOWER(:keyword), '%') " +
            "       OR LOWER(m.article.structure.location_text) LIKE CONCAT('%',LOWER(:keyword), '%') ) ")
    Page<Article> findMyPagesPendingArticle(@Param("ids") List<Long> ids, @Param("keyword") String keyword, @Param("username") String username, @Param("state") String state, Pageable pageable);

    @Query("SELECT m.article FROM ArticleState m " +
            "WHERE m.article.id IN (:ids) " +
            "AND m.deleted IS FALSE " +
            "AND m.senderState = :state " +
            "AND m.sender=:username " +
            "AND (LOWER(m.article.judulArticle) LIKE CONCAT('%',LOWER(:keyword),'%') " +
            "       OR LOWER(m.article.fullNameModifier) LIKE CONCAT('%', LOWER(:keyword), '%') " +
            "       OR LOWER(m.article.structure.location_text) LIKE CONCAT('%',LOWER(:keyword), '%') ) ")
    Page<Article> findMyPagesPublishedArticle(@Param("ids") List<Long> ids, @Param("keyword") String keyword, @Param("username") String username, @Param("state") String state, Pageable pageable);

    @Query("select rs.location_text from Structure rs where rs.id =:structureId and rs.deleted is false ")
    String findLocation(@Param("structureId") Long structureId);

    @Query(
            "SELECT m FROM ArticleState m " +
                    "WHERE m.article.id=:articleId " +
                    "AND m.deleted IS FALSE " +
                    "AND m.article.deleted IS FALSE "
    )
    ArticleState findByArticleId(@Param("articleId") Long articleId);

    @Query(
            "SELECT m FROM ArticleState m " +
                    "WHERE m.article.id=:articleId " +
                    "AND m.deleted IS FALSE " +
                    "AND m.receiver=:receiver " +
                    "AND m.article.deleted IS FALSE "
    )
    ArticleState findByArticleIdAndReceiver(@Param("articleId") Long articleId, @Param("receiver") String receiver);
}
