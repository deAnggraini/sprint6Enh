package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class RelatedArticleDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonIgnore
    private String createdBy;
    @JsonIgnore
    private Date createdDate = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

//    @JsonProperty("articles")
//    private List<RelatedArticleDto> relatedArticleDtos = new ArrayList<RelatedArticleDto>();
//
//    public List<RelatedArticleDto> getRelatedArticleDtos() {
//        return relatedArticleDtos;
//    }

//    public void setRelatedArticleDtos(List<RelatedArticleDto> relatedArticleDtos) {
//        this.relatedArticleDtos = relatedArticleDtos;
//    }
}
