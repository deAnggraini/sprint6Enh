package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.Article;
import id.co.bca.pakar.be.doc.model.FAQ;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleFaqRepository extends CrudRepository<FAQ, Long> {
    @Query(value = "SELECT m.* FROM t_article_faq m join t_article ta on m.article_id = ta.id " +
            "    WHERE m.deleted IS FALSE "+
            "    AND m.article_id =:id ",
            nativeQuery = true
    )
    List<FAQ> findFAQ(@Param("id") Long id);
}
