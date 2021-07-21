package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ArticleTemplateContent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleTemplateContentRepository extends CrudRepository<ArticleTemplateContent, Long> {
    @Query("SELECT m FROM ArticleTemplateContent m " +
            "WHERE m.articleTemplate.id=:templateId " +
            "AND m.deleted IS FALSE " +
            "AND m.articleTemplate.deleted IS FALSE " +
            "ORDER BY m.name DESC ")
    List<ArticleTemplateContent> findByTemplateId(@Param("templateId") Long templateId);

    @Query("SELECT m FROM ArticleTemplateContent m " +
            "WHERE m.id=:id " +
            "AND m.deleted IS FALSE ")
    Optional<ArticleTemplateContent> findById(@Param("id") Long id);
}
