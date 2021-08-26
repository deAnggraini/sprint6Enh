package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.MyPages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyPagesRepository extends CrudRepository<MyPages, Long> {
    @Query("SELECT m FROM MyPages m WHERE m.id IN (:ids) " +
            "AND m.deleted IS FALSE " +
            "AND (m.receiverState = :state or m.receiverState = 'DRAFTDELETED') " +
            "AND m.receiver=:username " +
            "AND (LOWER(m.judulArticle) LIKE CONCAT('%',LOWER(:keyword),'%') " +
            "       OR LOWER(m.fullNameModifier) LIKE CONCAT('%', LOWER(:keyword), '%') " +
            "       OR LOWER(m.location) LIKE CONCAT('%',LOWER(:keyword), '%') ) ")
    Page<MyPages> findMyPagesDratfArticle(@Param("ids") List<Long> ids, @Param("keyword") String keyword, @Param("username") String username, @Param("state") String state, Pageable pageable);

    @Query("SELECT m FROM MyPages m " +
            "WHERE m.id IN (:ids) " +
            "AND m.deleted IS FALSE " +
            "AND (m.senderState=:state OR m.senderState='PENDINGDELETED') " +
            "AND m.sender=:username " +
            "AND (LOWER(m.judulArticle) LIKE CONCAT('%',LOWER(:keyword),'%') " +
            "       OR LOWER(m.fullNameModifier) LIKE CONCAT('%', LOWER(:keyword), '%') " +
            "       OR LOWER(m.location) LIKE CONCAT('%',LOWER(:keyword), '%') ) ")
    Page<MyPages> findMyPagesPendingArticle(@Param("ids") List<Long> ids, @Param("keyword") String keyword, @Param("username") String username, @Param("state") String state, Pageable pageable);

    @Query("SELECT m FROM MyPages m " +
            "WHERE m.id IN (:ids) " +
            "AND m.deleted IS FALSE " +
            "AND (m.receiverState=:state)  " +
            "AND (m.receiver=:username) " +
            "AND (LOWER(m.judulArticle) LIKE CONCAT('%',LOWER(:keyword),'%') " +
            "       OR LOWER(m.fullNameModifier) LIKE CONCAT('%', LOWER(:keyword), '%') " +
            "       OR LOWER(m.location) LIKE CONCAT('%',LOWER(:keyword), '%') ) ")
    Page<MyPages> findMyPagesPendingArticleAsPublisher(@Param("ids") List<Long> ids, @Param("keyword") String keyword, @Param("username") String username, @Param("state") String state, Pageable pageable);

    @Query("SELECT m FROM MyPages m " +
            "WHERE m.id IN (:ids) " +
            "AND m.deleted IS FALSE " +
            "AND m.senderState = :state " +
            "AND m.sender=:username " +
            "AND (LOWER(m.judulArticle) LIKE CONCAT('%',LOWER(:keyword),'%') " +
            "       OR LOWER(m.fullNameModifier) LIKE CONCAT('%', LOWER(:keyword), '%') " +
            "       OR LOWER(m.location) LIKE CONCAT('%',LOWER(:keyword), '%') ) ")
    Page<MyPages> findMyPagesPublishedArticle(@Param("ids") List<Long> ids, @Param("keyword") String keyword, @Param("username") String username, @Param("state") String state, Pageable pageable);

    @Query("select rs.location from Structure rs where rs.id =:structureId and rs.deleted is false ")
    String findLocation(@Param("structureId") Long structureId);

    @Query(
            "SELECT m FROM MyPages m " +
                    "WHERE m.id=:articleId " +
                    "AND m.deleted IS FALSE " +
                    "AND m.deleted IS FALSE "
    )
    MyPages findByArticleId(@Param("articleId") Long articleId);

    // find article for contents page role ADMIN
    @Query("SELECT m FROM MyPages m " +
            "WHERE m.deleted IS FALSE " +
            "AND (LOWER(m.judulArticle) LIKE CONCAT('%',LOWER(:keyword),'%') " +
            "       OR LOWER(m.fullNameModifier) LIKE CONCAT('%', LOWER(:keyword), '%') " +
            "       OR LOWER(m.location) LIKE CONCAT('%',LOWER(:keyword), '%') ) ")
    Page<MyPages> findContentArticleForAdmin(@Param("keyword") String keyword, Pageable pageable);

    // find article for contents page role except ADMIN
    @Query("SELECT m FROM MyPages m " +
            "WHERE  m.articleState = 'PUBLISHED' AND m.deleted IS FALSE " +
            "AND (LOWER(m.judulArticle) LIKE CONCAT('%',LOWER(:keyword),'%') " +
            "       OR LOWER(m.fullNameModifier) LIKE CONCAT('%', LOWER(:keyword), '%') " +
            "       OR LOWER(m.location) LIKE CONCAT('%',LOWER(:keyword), '%') ) ")
    Page<MyPages> findContentArticle(@Param("keyword") String keyword, Pageable pageable);

}
