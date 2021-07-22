package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class RelatedArticleDto extends PagingResponseDto {
    @JsonProperty("articles")
    private List<RelatedArticleDto> relatedArticleDtos = new ArrayList<RelatedArticleDto>();

    public List<RelatedArticleDto> getRelatedArticleDtos() {
        return relatedArticleDtos;
    }

    public void setRelatedArticleDtos(List<RelatedArticleDto> relatedArticleDtos) {
        this.relatedArticleDtos = relatedArticleDtos;
    }
}
