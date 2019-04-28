
package com.example.absensiapp.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Matapelajaran {

    @SerializedName("hari")
    private String mHari;
    @SerializedName("id")
    private String mId;
    @SerializedName("jam")
    private String mJam;
    @SerializedName("nama")
    private String mNama;
    @SerializedName("sks")
    private String mSks;

    public String getHari() {
        return mHari;
    }

    public void setHari(String hari) {
        mHari = hari;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getJam() {
        return mJam;
    }

    public void setJam(String jam) {
        mJam = jam;
    }

    public String getNama() {
        return mNama;
    }

    public void setNama(String nama) {
        mNama = nama;
    }

    public String getSks() {
        return mSks;
    }

    public void setSks(String sks) {
        mSks = sks;
    }

}
