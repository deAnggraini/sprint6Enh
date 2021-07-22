package id.co.bca.pakar.be.doc.service;

import id.co.bca.pakar.be.doc.dto.*;

import java.util.List;

public interface ArticleService {
    Boolean existArticle(String title);

    ArticleDto generateArticle(GenerateArticleDto articleDto) throws Exception;

    ArticleDto getArticleById(Long id) throws Exception;

    ArticleDto saveArticle(MultipartArticleDto articleDto) throws Exception;

    Long getContentId() throws Exception;

    ArticleContentDto saveContent(ArticleContentDto articleContentDto) throws Exception;

    Boolean deleteContent(DeleteContentDto deleteContentDto) throws Exception;

    List<ArticleDto> search(SearchDto searchDto) throws Exception;
}
