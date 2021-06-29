package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StructureResponseDto extends StructureDto {
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("image")
    private String image;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "StructureResponseDto{" +
                "icon='" + icon + '\'' +
                ", image='" + image + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", sort=" + sort +
                ", level=" + level +
                ", parent=" + parent +
                ", uri='" + uri + '\'' +
                ", edit=" + edit +
                ", location='" + location + '\'' +
                ", location_text='" + location_text + '\'' +
                '}';
    }
}