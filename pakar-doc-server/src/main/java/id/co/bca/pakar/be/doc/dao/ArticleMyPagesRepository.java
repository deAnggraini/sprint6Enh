package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.Article;
import id.co.bca.pakar.be.doc.model.Formulir;
import id.co.bca.pakar.be.doc.model.VirtualPages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleMyPagesRepository extends CrudRepository<Article, Long> {

    @Query("select ta from Formulir ta where created_by =:username and deleted is false "
    )
    List<Formulir> findMyPagesFormulir(@Param("username") String username);

    @Query("select ta from VirtualPages ta where created_by =:username and deleted is false "
    )
    List<VirtualPages> findMyPagesVirtualPages(@Param("username") String username);

    @Query("SELECT m FROM Article m WHERE m.id IN (:ids) " +
            "AND m.deleted IS FALSE " +
            "AND m.articleState = :state " +
            "AND (LOWER(m.judulArticle) LIKE CONCAT('%',LOWER(:keyword),'%') " +
            "       OR LOWER(m.fullNameModifier) LIKE CONCAT('%', LOWER(:keyword), '%') " +
            "       OR LOWER(m.structure.location_text) LIKE CONCAT('%',LOWER(:keyword), '%') ) ")
    Page<Article> findMyPagesArticle(@Param("ids") List<Long> ids, @Param("keyword") String keyword, @Param("state") String state, Pageable pageable);

    @Query("select rs.location_text from Structure rs where rs.id =:structureId and rs.deleted is false ")
    String findLocation(@Param("structureId") Long structureId);

    // Awalnya untuk set location_text
//    @Query(value = "WITH RECURSIVE structure_child AS (select id, parent, name from r_structure " +
//            "where id =:structureId union select e.id, e.parent, e.name from r_structure e " +
//            "INNER JOIN structure_child s ON s.parent = e.id) " +
//            "select * from structure_child",
//            nativeQuery = true)
//    Structure findLocationParent(@Param("structureId") Long structureId);

}
