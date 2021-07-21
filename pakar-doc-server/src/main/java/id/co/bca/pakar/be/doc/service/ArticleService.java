package id.co.bca.pakar.be.doc.service;

import id.co.bca.pakar.be.doc.dto.ArticleContentDto;
import id.co.bca.pakar.be.doc.dto.ArticleDto;
import id.co.bca.pakar.be.doc.dto.GenerateArticleDto;
import id.co.bca.pakar.be.doc.dto.MultipartArticleDto;

public interface ArticleService {
    Boolean existArticle(String title);

    ArticleDto generateArticle(GenerateArticleDto articleDto) throws Exception;

    ArticleDto getArticleById(Long id) throws Exception;

    ArticleDto saveArticle(MultipartArticleDto articleDto) throws Exception;

    Long getContentId() throws Exception;

    ArticleContentDto saveContent(ArticleContentDto articleContentDto) throws Exception;
}
