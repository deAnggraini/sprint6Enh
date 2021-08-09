package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.Article;
import id.co.bca.pakar.be.doc.model.Formulir;
import id.co.bca.pakar.be.doc.model.VirtualPages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleMyPagesRepository extends PagingAndSortingRepository<Article, Long> {
    @Query("select ta from Article ta where created_by =:username and deleted is false and state = 'DRAFT'"
    )
    List<Article> findMyPagesArticle(@Param("username") String username);

    @Query("select ta from Formulir ta where created_by =:username and deleted is false "
    )
    List<Formulir> findMyPagesFormulir(@Param("username") String username);

    @Query("select ta from VirtualPages ta where created_by =:username and deleted is false "
    )
    List<VirtualPages> findMyPagesVirtualPages(@Param("username") String username);

    @Query("select ta from Article ta where created_by =:username and deleted is false and state = 'PENDING'"
    )
    List<Article> findMyPagesArticlePending(@Param("username") String username);

    @Query("select ta from Formulir ta where created_by =:username and deleted is false "
    )
    List<Formulir> findMyPagesFormulirPending(@Param("username") String username);

    @Query("select ta from VirtualPages ta where created_by =:username and deleted is false"
    )
    List<VirtualPages> findMyPagesVirtualPagesPending(@Param("username") String username);

    @Query("SELECT m FROM Article m WHERE id IN (:ids) " +
            "AND m.deleted IS FALSE " +
            "AND m.state = :state "
    )
    Page<Article> findMyPagesArticle(@Param("ids") List<Long> ids, @Param("state") String state, Pageable pageable);

    @Query("SELECT m FROM Article m WHERE id IN (:ids) " +
            "AND m.deleted IS FALSE " +
            "AND m.state = :state "
    )
    Page<Article> findMyPagesArticle(@Param("ids") List<Long> ids, @Param("keyword") String keyword, @Param("type") String type, @Param("state") String state, Pageable pageable);
}
