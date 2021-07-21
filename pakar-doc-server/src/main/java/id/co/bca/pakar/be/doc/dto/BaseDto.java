package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BaseDto {
    @JsonIgnore
    protected String token;
    @JsonIgnore
    protected String username;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
