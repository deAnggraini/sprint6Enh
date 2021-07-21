package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.ArticleContent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleContentRepository extends CrudRepository<ArticleContent, Long> {
    @Query(value = "SELECT nextval('public.article_content_seq') ",
            nativeQuery = true)
    Long getContentId();
}
