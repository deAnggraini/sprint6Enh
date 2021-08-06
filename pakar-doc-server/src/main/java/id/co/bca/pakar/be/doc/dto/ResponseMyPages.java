package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ResponseMyPages extends BaseDto{
    @JsonProperty("type")
    private String tipe;
    @JsonProperty("judul")
    private String judul;
    @JsonProperty("lokasi")
    private String lokasi;
    @JsonProperty("isNew")
    private Boolean isNew;
    @JsonProperty("modifikasi_date")
    private Date modifikasi_date;
    @JsonProperty("modifikasi_by")
    private String modifikasi_by;
    @JsonProperty("reviewed_by")
    private String reviewed_by;

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Date getModifikasi_date() {
        return modifikasi_date;
    }

    public void setModifikasi_date(Date modifikasi_date) {
        this.modifikasi_date = modifikasi_date;
    }

    public String getModifikasi_by() {
        return modifikasi_by;
    }

    public void setModifikasi_by(String modifikasi_by) {
        this.modifikasi_by = modifikasi_by;
    }

    public String getReviewed_by() {
        return reviewed_by;
    }

    public void setReviewed_by(String reviewed_by) {
        this.reviewed_by = reviewed_by;
    }
}
