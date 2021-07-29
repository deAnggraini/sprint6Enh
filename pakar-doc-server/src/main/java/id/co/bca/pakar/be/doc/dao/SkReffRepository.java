package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.SkRefference;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkReffRepository extends CrudRepository<SkRefference, Long> {
    @Query("SELECT m.skRefference FROM ArticleSkReff m " +
            "WHERE m.article.id=:articleId " +
            "AND m.deleted IS FALSE " +
            "AND m.skRefference.deleted IS FALSE " +
            "AND m.article.deleted IS FALSE " +
            "ORDER BY m.skRefference.id ASC ")
    Iterable<SkRefference> findByArticleId(@Param("articleId") Long articleId);

    @Query("SELECT m FROM SkRefference m " +
            "WHERE (lower(m.title) LIKE lower(concat('%', :keyword,'%')) OR lower(m.skNumber) LIKE lower(concat('%', :keyword,'%')))" +
            "AND m.deleted IS FALSE "+
            "ORDER BY m.skNumber DESC ")
    Iterable<SkRefference> searchSkReffLike(@Param("keyword") String keyword);

    @Query("SELECT m FROM SkRefference m " +
            "WHERE m.id=:id " +
            "AND m.deleted IS FALSE ")
    Optional<SkRefference> findById(@Param("id") Long id);
}
