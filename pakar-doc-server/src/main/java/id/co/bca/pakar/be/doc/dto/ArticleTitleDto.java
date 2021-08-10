package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ArticleTitleDto extends BaseDto {
    @NotEmpty(message = "Judul artikel harus diisi}")
    @Size(max = 50, message = "Maximum judul topik adalah 50 karakter")
//    @Pattern(regexp="[0-9a-zA-Z\\/\\s\\-\\(\\)]*$", message = "title must alpha or numeric, whitespace, - , /, ()")
    @JsonProperty("title")
    protected String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
