package id.co.bca.pakar.be.doc.service;

import id.co.bca.pakar.be.doc.model.Article;
import id.co.bca.pakar.be.doc.model.ArticleContent;
import id.co.bca.pakar.be.doc.model.ArticleVersion;

import java.util.List;

public interface ArticleVersionService {
//    Boolean existArticle(String title);
//
//    ArticleDto generateArticle(GenerateArticleDto articleDto) throws Exception;
//
//    ArticleDto getArticleById(Long id) throws Exception;
//
//    ArticleDto getArticleById(Long id, boolean isEdit, String username, String token) throws Exception;

    ArticleVersion saveArticle(Article article) throws Exception;

//    Boolean cancelArticle(Long id, String username, String token) throws Exception;
//
//    Boolean cancelSendArticle(Long id, String username, String token) throws Exception;
//
//    Long getContentId(String username, String token) throws Exception;
//
//    ArticleContentDto getContentById(Long id) throws Exception;
//
//    ArticleContentDto saveContent(ArticleContentDto articleContentDto) throws Exception;
//
//    List<ArticleContentDto> saveBatchContents(List<ArticleContentDto> articleContentDtos, String username, String token) throws Exception;
//
//    Boolean deleteContent(DeleteContentDto deleteContentDto) throws Exception;
//
//    Page<RelatedArticleDto> search(SearchDto searchDto) throws Exception;
//
//    List<FaqDto> findFaq(Long requestFAQDto) throws Exception;
//
//    Page<SuggestionArticleDto> searchSuggestion(SearchSuggestionDto searchDto) throws Exception;
//
//    Page<MyPageDto> searchMyPages(SearchMyPageDto searchDto) throws Exception;
//
//    Page<MyPageDto> searchMyPages2(SearchMyPageDto searchDto) throws Exception;
//
//    List<UserArticleEditingDto> findUserArticleEditings(String username, String token, Long articleId) throws Exception;
}