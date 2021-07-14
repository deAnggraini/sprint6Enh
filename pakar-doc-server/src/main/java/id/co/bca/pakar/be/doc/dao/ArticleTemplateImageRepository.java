package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ArticleTemplateImage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTemplateImageRepository extends CrudRepository<ArticleTemplateImage, Long> {
    @Query("SELECT m FROM ArticleTemplateImage m " +
            "WHERE m.articleTemplate.id=:templateId " +
            "AND m.deleted IS FALSE " +
            "AND m.articleTemplate.deleted IS FALSE")
    ArticleTemplateImage findArticleTemplatesImage(@Param("templateId") Long templateId);
}
