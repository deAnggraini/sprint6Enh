package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class ArticleTemplateDto {
    @JsonProperty("id")
    private Long id;
    @NotEmpty(message = "name is required")
    @Size(max = 150, message = "maximum length 150 characters")
    @JsonProperty("name")
    private String name;
    @NotEmpty(message = "location is required")
    @JsonProperty("categories")
    private List<StructureDto> categori = new ArrayList<>();
    @NotEmpty(message = "desc is required")
    @JsonProperty("desc")
    private String desc;
    @NotEmpty(message = "image is required")
    @JsonProperty("image")
    private String image;
    @JsonProperty("thumb")
    private String thumb;
    @NotEmpty(message = "content is required")
    @JsonProperty("contents")
    private List<ContentTemplateDto> content = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StructureDto> getCategori() {
        return categori;
    }

    public void setCategori(List<StructureDto> categori) {
        this.categori = categori;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public List<ContentTemplateDto> getContent() {
        return content;
    }

    public void setContent(List<ContentTemplateDto> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ArticleTemplateDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categori=" + categori +
                ", desc='" + desc + '\'' +
                ", image='" + image + '\'' +
                ", thumb='" + thumb + '\'' +
                ", content=" + content +
                '}';
    }
}
