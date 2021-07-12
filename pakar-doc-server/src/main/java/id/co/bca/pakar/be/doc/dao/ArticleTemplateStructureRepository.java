package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ArticleTemplateStructure;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTemplateStructureRepository extends CrudRepository<ArticleTemplateStructure, Long> {
    @Query("SELECT m FROM ArticleTemplateStructure m " +
            "WHERE m.structure.id=:structureId " +
            "AND m.deleted IS FALSE " +
            "AND m.structure.deleted IS FALSE")
    List<ArticleTemplateStructure> findArticleTemplatesByStructureId(@Param("structureId") Long structureId);
}
