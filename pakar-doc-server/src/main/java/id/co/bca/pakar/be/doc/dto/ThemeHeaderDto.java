package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThemeHeaderDto {

    @JsonProperty("background")
    private String background;
    @JsonProperty("color")
    private String color;
    @JsonProperty("hover")
    private String hover;

    public ThemeHeaderDto (String background, String color, String hover){
        super();
        this.background = background;
        this.color = color;
        this.hover = hover;
    }

    public ThemeHeaderDto(){
        super();
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHover() {
        return hover;
    }

    public void setHover(String hover) {
        this.hover = hover;
    }
}
