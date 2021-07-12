package id.co.bca.pakar.be.doc.dao.text.impl;

import id.co.bca.pakar.be.doc.dao.text.ArticleTextRepository;
import id.co.bca.pakar.be.doc.model.Article;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ArticleTextRepositoryImp implements ArticleTextRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Article> search(String terms, int limit, int offset) {
        return null;
    }
}
