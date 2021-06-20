package id.co.bca.pakar.be.doc.dto;

import java.util.ArrayList;
import java.util.List;

public class ThemeDto {

    private ThemeHeaderDto header;
    private ThemeHomepageDto homepage;

    public ThemeHeaderDto getHeader() {
        return header;
    }

    public void setHeader(ThemeHeaderDto header) {
        this.header = header;
    }

    public ThemeHomepageDto getHomepage() {
        return homepage;
    }

    public void setHomepage(ThemeHomepageDto homepage) {
        this.homepage = homepage;
    }
}
