package id.co.bca.pakar.be.doc.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseRelatedArticleDto extends PagingResponseDto {
    private List<RelatedArticleDto> articles = new ArrayList<RelatedArticleDto>();

    public List<RelatedArticleDto> getArticles() {
        return articles;
    }

    public void setArticles(List<RelatedArticleDto> articles) {
        this.articles = articles;
    }
}
