
package com.example.absensiapp.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Absensi {

    @SerializedName("dosen_email")
    private String dosenEmail;
    @SerializedName("dosen_id")
    private String dosenId;
    @Expose
    private String jam;
    @SerializedName("mahasiswa_id")
    private String mahasiswaId;
    @Expose
    private String matapelajaran;
    @SerializedName("matapelajaran_id")
    private String matapelajaranId;
    @Expose
    private String nama;
    @Expose
    private String npm;
    @Expose
    private String tanggal;

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

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getMahasiswaId() {
        return mahasiswaId;
    }

    public void setMahasiswaId(String mahasiswaId) {
        this.mahasiswaId = mahasiswaId;
    }

    public String getMatapelajaran() {
        return matapelajaran;
    }

    public void setMatapelajaran(String matapelajaran) {
        this.matapelajaran = matapelajaran;
    }

    public String getMatapelajaranId() {
        return matapelajaranId;
    }

    public void setMatapelajaranId(String matapelajaranId) {
        this.matapelajaranId = matapelajaranId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

}
