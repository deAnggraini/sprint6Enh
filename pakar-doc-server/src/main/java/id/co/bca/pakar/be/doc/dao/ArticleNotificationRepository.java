package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ArticleNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleNotificationRepository extends CrudRepository<ArticleNotification, Long> {
    @Query("SELECT m FROM ArticleNotification m " +
            "WHERE m.receiver = :username " +
            "AND m.deleted IS FALSE " +
            "AND (LOWER(m.article.judulArticle) LIKE CONCAT('%',LOWER(:keyword),'%') " +
            "       OR LOWER(m.documentType) LIKE CONCAT('%',LOWER(:keyword),'%') " +
            "       OR LOWER(m.sender) LIKE CONCAT('%',LOWER(:keyword),'%')" +
            "    ) ")
    Page<ArticleNotification> findNotification(@Param("username") String username, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT m FROM ArticleNotification m " +
            "WHERE m.receiver = :username " +
            "AND m.deleted IS FALSE ")
    Page<ArticleNotification> findNotification(@Param("username") String username, Pageable pageable);

    @Query("SELECT m FROM ArticleNotification m " +
            "WHERE m.receiver=:username ")
    List<ArticleNotification> findAll(@Param("username") String username, Pageable pageable);

    @Query("SELECT COUNT(m) FROM ArticleNotification m " +
            "WHERE m.receiver=:username " +
            "AND m.isRead=:read " +
            "AND m.deleted IS FALSE")
    long countByReceiverAndReadStatus(@Param("username") String username, @Param("read") boolean read);

    @Modifying
    @Query("UPDATE ArticleNotification m " +
            "SET m.isRead = TRUE " +
            "WHERE m.receiver = :username")
    int updateAllReadStatus(@Param("username") String username);

    @Modifying
    @Query("UPDATE ArticleNotification m " +
            "SET m.isRead = TRUE " +
            "WHERE m.receiver = :username " +
            "AND m.id IN (:ids) ")
    int updateReadStatus(@Param("username") String username, @Param("ids") List<Long> ids);
}
