package id.co.bca.pakar.be.doc.service;

import id.co.bca.pakar.be.doc.model.Article;
import id.co.bca.pakar.be.doc.model.ArticleContentClone;

public interface ArticleCloneService {
//    Boolean clone(Article article, String username) throws Exception;
    Boolean cloneArticleContent(Article article, String username) throws Exception;
    ArticleContentClone findById(Long id, String username) throws Exception;
    ArticleContentClone saveContent(ArticleContentClone content) throws Exception;
}