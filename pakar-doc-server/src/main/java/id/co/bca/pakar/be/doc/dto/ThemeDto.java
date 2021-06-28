package id.co.bca.pakar.be.doc.dto;

public class ThemeDto {

    private ThemeHeaderDto header;
    private ThemeHomepageDto homepage;
    private ThemeLoginDto login;

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

    public ThemeLoginDto getLogin() {
        return login;
    }

    public void setLogin(ThemeLoginDto login) {
        this.login = login;
    }
}
