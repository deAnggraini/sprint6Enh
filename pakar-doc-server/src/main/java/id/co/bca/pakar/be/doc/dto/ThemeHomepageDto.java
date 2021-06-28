package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ThemeHomepageDto {

    @JsonProperty("bg_img_top")
    private String  bg_img_top;
    private List<String> component;

    public ThemeHomepageDto(){
        super();
    }
    public ThemeHomepageDto(String bg_img_top){
        super();
        this.bg_img_top = bg_img_top;
    }

    public String getBg_img_top() {
        return bg_img_top;
    }

    public void setBg_img_top(String bg_img_top) {
        this.bg_img_top = bg_img_top;
    }

    public List<String> getComponent() {
        return component;
    }

    public void setComponent(List<String> component) {
        this.component = component;
    }
}
