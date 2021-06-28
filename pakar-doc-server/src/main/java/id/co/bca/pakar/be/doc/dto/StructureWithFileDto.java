package id.co.bca.pakar.be.doc.dto;

import org.springframework.web.multipart.MultipartFile;

public class StructureWithFileDto extends StructureDto {
    private MultipartFile icon;

    private MultipartFile image;

    public MultipartFile getIcon() {
        return icon;
    }

    public void setIcon(MultipartFile icon) {
        this.icon = icon;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
