package id.co.bca.pakar.be.doc.service;

import id.co.bca.pakar.be.doc.dto.ArticleDto;
import id.co.bca.pakar.be.doc.dto.BaseArticleDto;

public interface ArticleService {
    Boolean existArticle(String title);
    Long generateArticle(BaseArticleDto articleDto);
    ArticleDto saveArticle(ArticleDto articleDto);
}
