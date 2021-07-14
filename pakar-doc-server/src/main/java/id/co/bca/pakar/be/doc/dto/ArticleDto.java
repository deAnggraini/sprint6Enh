package id.co.bca.pakar.be.doc.dto;

import org.springframework.web.multipart.MultipartFile;

public class ArticleDto extends BaseArticleDto {
    private MultipartFile image;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
