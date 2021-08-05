package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleMyPagesRepository extends CrudRepository<Article, Long> {
    @Query("SELECT m FROM Article m"
    )
    List<Article> findMyPagesDraft();

}
