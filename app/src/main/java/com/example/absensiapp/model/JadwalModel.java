
package com.example.absensiapp.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class JadwalModel {

    @SerializedName("mahasiswa")
    private Mahasiswa mMahasiswa;
    @SerializedName("matapelajaran")
    private Matapelajaran mMatapelajaran;

    public Mahasiswa getMahasiswa() {
        return mMahasiswa;
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        mMahasiswa = mahasiswa;
    }

    public Matapelajaran getMatapelajaran() {
        return mMatapelajaran;
    }

    public void setMatapelajaran(Matapelajaran matapelajaran) {
        mMatapelajaran = matapelajaran;
    }

}
