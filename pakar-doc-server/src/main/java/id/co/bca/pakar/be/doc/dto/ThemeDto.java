package id.co.bca.pakar.be.doc.dto;

import java.util.ArrayList;
import java.util.List;

public class ThemeDto {

    private List<ThemeHeaderDto> header = new ArrayList<>();
    private List<ThemeHomepageDto> homepage = new ArrayList<>();

    public List<ThemeHeaderDto> getHeader() {
        return header;
    }

    public void setHeader(List<ThemeHeaderDto> header) {
        this.header = header;
    }

    public List<ThemeHomepageDto> getHomepage() {
        return homepage;
    }

    public void setHomepage(List<ThemeHomepageDto> homepage) {
        this.homepage = homepage;
    }
}
