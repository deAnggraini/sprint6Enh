package id.co.bca.pakar.be.doc.dao.text;

import id.co.bca.pakar.be.doc.model.Article;

import java.util.List;

public interface ArticleTextRepository {
    List<Article> search(String terms, int limit, int offset);
}
