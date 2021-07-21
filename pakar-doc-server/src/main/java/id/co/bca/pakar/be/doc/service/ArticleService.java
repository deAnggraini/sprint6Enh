package id.co.bca.pakar.be.doc.service;

import id.co.bca.pakar.be.doc.dto.ArticleDto;
import id.co.bca.pakar.be.doc.dto.BaseArticleDto;
import id.co.bca.pakar.be.doc.dto.GenerateArticleDto;

public interface ArticleService {
    Boolean existArticle(String title);
    ArticleDto generateArticle(GenerateArticleDto articleDto) throws Exception;
    ArticleDto getArticleById(Long id) throws Exception;
    ArticleDto saveArticle(ArticleDto articleDto);
}
