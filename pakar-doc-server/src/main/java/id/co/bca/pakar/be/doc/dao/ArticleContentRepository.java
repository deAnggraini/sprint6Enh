package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ArticleContent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleContentRepository extends CrudRepository<ArticleContent, Long> {
    @Query(value = "SELECT nextval('public.article_content_seq') ",
            nativeQuery = true)
    Long getContentId();

    @Query(value = "SELECT tac.* " +
            "FROM t_article_content AS tac " +
            "INNER JOIN " +
            "  (SELECT tac.id FROM t_article_content tac WHERE tac.parent = :parentId) AS tac2 " +
            "  ON tac2.id = tac.parent OR tac.parent = :parentId " +
            "GROUP BY tac.id, tac2.id  " +
            "ORDER BY tac.id, tac.parent, tac.level ASC ",
            nativeQuery = true)
    List<ArticleContent> findArticleContent(@Param("parentId") Long parentId);
}