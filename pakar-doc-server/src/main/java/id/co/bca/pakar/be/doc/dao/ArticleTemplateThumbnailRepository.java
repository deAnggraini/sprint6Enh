package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ArticleTemplateThumbnail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTemplateThumbnailRepository extends CrudRepository<ArticleTemplateThumbnail, Long> {
    @Query("SELECT m FROM ArticleTemplateThumbnail m " +
            "WHERE m.articleTemplate.id=:templateId " +
            "AND m.deleted IS FALSE " +
            "AND m.articleTemplate.deleted IS FALSE")
    ArticleTemplateThumbnail findArticleTemplatesThumbnail(@Param("templateId") Long templateId);
}
