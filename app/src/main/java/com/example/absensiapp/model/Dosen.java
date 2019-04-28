
package com.example.absensiapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Dosen implements Parcelable {

    @Expose
    private String email;
    @Expose
    private String id;
    @Expose
    private String nama;
    @Expose
    private String nid;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.nid);
    }

    public Dosen() {
    }

    protected Dosen(Parcel in) {
        this.email = in.readString();
        this.id = in.readString();
        this.nama = in.readString();
        this.nid = in.readString();
    }

    public static final Parcelable.Creator<Dosen> CREATOR = new Parcelable.Creator<Dosen>() {
        @Override
        public Dosen createFromParcel(Parcel source) {
            return new Dosen(source);
        }

        @Override
        public Dosen[] newArray(int size) {
            return new Dosen[size];
        }
    };
}
