package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ArticleTemplate;
import id.co.bca.pakar.be.doc.model.ArticleTemplateStructure;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTemplateRepository extends CrudRepository<ArticleTemplate, Long> {
    @Query(value = "SELECT tat.* FROM ( " +
            "SELECT tat.* FROM t_article_template tat " +
            "LEFT JOIN t_article_template_role tatr ON tatr.template_id = tat.id " +
            "WHERE tatr.role_id = :role) tat " +
            "LEFT JOIN t_article_template_structure tats ON tats.article_template_id = tat.id " +
            "WHERE tats.structure_id = :structureId " +
            "and tat.deleted is FALSE",
            nativeQuery = true)
    List<ArticleTemplate> findArticleTemplates(@Param("structureId") Long structureId, @Param("role") String role);
}
