
package com.example.absensiapp.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GroupTanggalModel {


    @SerializedName("absensi_id")
    private String absensiId;
    @SerializedName("dosen_email")
    private String dosenEmail;
    @SerializedName("dosen_id")
    private String dosenId;
    @Expose
    private String tanggal;

    public String getAbsensiId() {
        return absensiId;
    }

    public void setAbsensiId(String absensiId) {
        this.absensiId = absensiId;
    }

    public String getDosenEmail() {
        return dosenEmail;
    }

    public void setDosenEmail(String dosenEmail) {
        this.dosenEmail = dosenEmail;
    }

    public String getDosenId() {
        return dosenId;
    }

    public void setDosenId(String dosenId) {
        this.dosenId = dosenId;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

}
