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
    @Query("select ta from Article ta where created_by =:username and deleted is false and state = 'DRAFT'"
    )
    List<Article> findMyPagesArticle(@Param("username") String username);

    @Query("select ta from Formulir ta where created_by =:username and deleted is false and state = 'DRAFT'"
    )
    List<Formulir> findMyPagesFormulir(@Param("username") String username);

    @Query("select ta from VirtualPages ta where created_by =:username and deleted is false and state = 'DRAFT'"
    )
    List<VirtualPages> findMyPagesVirtualPages(@Param("username") String username);

    @Query("select ta from Article ta where created_by =:username and deleted is false and state = 'PENDING'"
    )
    List<Article> findMyPagesArticlePending(@Param("username") String username);

    @Query("select ta from Formulir ta where created_by =:username and deleted is false and state = 'PENDING'"
    )
    List<Formulir> findMyPagesFormulirPending(@Param("username") String username);

    @Query("select ta from VirtualPages ta where created_by =:username and deleted is false and state = 'PENDING'"
    )
    List<VirtualPages> findMyPagesVirtualPagesPending(@Param("username") String username);


}
