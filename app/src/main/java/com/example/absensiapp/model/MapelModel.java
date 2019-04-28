
package com.example.absensiapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class MapelModel implements Parcelable {

    @Expose
    private String id;
    @Expose
    private Dosen dosen;
    @Expose
    private String hari;
    @Expose
    private String jam;
    @Expose
    private String kelas;
    @Expose
    private String nama;
    @Expose
    private String sks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Dosen getDosen() {
        return dosen;
    }

    public void setDosen(Dosen dosen) {
        this.dosen = dosen;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSks() {
        return sks;
    }

    public void setSks(String sks) {
        this.sks = sks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeParcelable(this.dosen, flags);
        dest.writeString(this.hari);
        dest.writeString(this.jam);
        dest.writeString(this.kelas);
        dest.writeString(this.nama);
        dest.writeString(this.sks);
    }

    public MapelModel() {
    }

    protected MapelModel(Parcel in) {
        this.id = in.readString();
        this.dosen = in.readParcelable(Dosen.class.getClassLoader());
        this.hari = in.readString();
        this.jam = in.readString();
        this.kelas = in.readString();
        this.nama = in.readString();
        this.sks = in.readString();
    }

    public static final Parcelable.Creator<MapelModel> CREATOR = new Parcelable.Creator<MapelModel>() {
        @Override
        public MapelModel createFromParcel(Parcel source) {
            return new MapelModel(source);
        }

        @Override
        public MapelModel[] newArray(int size) {
            return new MapelModel[size];
        }
    };


}
