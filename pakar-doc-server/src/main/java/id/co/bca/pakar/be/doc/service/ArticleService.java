package id.co.bca.pakar.be.doc.service;

import id.co.bca.pakar.be.doc.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ArticleService {
    Boolean existArticle(String title, Long articleId);

    ArticleDto generateArticle(GenerateArticleDto articleDto) throws Exception;

    ArticleDto getArticleById(Long id, boolean isEdit, String username, String token) throws Exception;

    ArticleResponseDto saveArticle(MultipartArticleDto articleDto) throws Exception;

    Boolean cancelArticle(Long id, String username, String token) throws Exception;

    Boolean cancelSendArticle(Long id,  String receiver, String username, String token) throws Exception;

    Long getContentId(String username, String token) throws Exception;

    ArticleContentDto getContentById(Long id) throws Exception;

    ArticleContentDto getContentById(Long id, String username) throws Exception;

    ArticleContentDto saveContent(ArticleContentDto articleContentDto) throws Exception;

    List<ArticleContentDto> saveBatchContents(List<ArticleContentDto> articleContentDtos, String username, String token) throws Exception;

    Boolean deleteContent(DeleteContentDto deleteContentDto) throws Exception;

    Page<RelatedArticleDto> search(SearchDto searchDto) throws Exception;

    List<FaqDto> findFaq(Long requestFAQDto) throws Exception;

    Page<SuggestionArticleDto> searchSuggestion(SearchSuggestionDto searchDto) throws Exception;

//    Page<MyPageDto> searchMyPages(SearchMyPageDto searchDto) throws Exception;
//
//    Page<MyPageDto> searchMyPages2(SearchMyPageDto searchDto) throws Exception;

    List<UserArticleEditingDto> findUserArticleEditings(String username, String token, Long articleId) throws Exception;

    Boolean deleteArticle(RequestDeleteDto requestDeleteDto, String username, String token) throws Exception;

    Boolean cancelEditArticle(String token, String username,RequestCancelEditDto reqCancelEdit) throws Exception;
}