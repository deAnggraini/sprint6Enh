package id.co.bca.pakar.be.wf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ArticleTitleDto extends BaseDto {
    @NotEmpty(message = "{judul.article.required}")
    @Size(max = 50, message = "Maximum judul topik adalah 50 karakter")
    @Pattern(regexp="[0-9a-zA-Z\\/\\s\\-\\(\\)]*$", message = "title must alpha or numeric, whitespace, - , /, ()")
    @JsonProperty("title")
    protected String judulArticle;

    public String getJudulArticle() {
        return judulArticle;
    }

    public void setJudulArticle(String judulArticle) {
        this.judulArticle = judulArticle;
    }

    @Override
    public String toString() {
        return "ArticleTitleDto{" +
                "judulArticle='" + judulArticle + '\'' +
                '}';
    }
}
