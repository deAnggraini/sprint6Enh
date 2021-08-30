package id.co.bca.pakar.be.doc.service;

import id.co.bca.pakar.be.doc.dto.ArticleDto;
import id.co.bca.pakar.be.doc.dto.SearchPublishedArticleDto;
import id.co.bca.pakar.be.doc.model.Article;
import id.co.bca.pakar.be.doc.model.ArticleVersion;
import org.springframework.data.domain.Page;

public interface ArticleVersionService {
    ArticleVersion saveArticle(Article article, Boolean isReleased) throws Exception;

    Page<ArticleDto> searchPublishedArticle(SearchPublishedArticleDto searchDto) throws Exception;

    Article reloadPublishedArticleVersion(String judulArticle, String copier, Article source) throws Exception;
}