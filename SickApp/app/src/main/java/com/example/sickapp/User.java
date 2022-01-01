package com.example.sickapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_user")
public class User implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    int id;

    @ColumnInfo(name="username")
    String username;

    @ColumnInfo(name="password")
    String password;

    @ColumnInfo(name="nama")
    String nama;

    @ColumnInfo(name="dob")
    String dob;

    @ColumnInfo(name="alamat")
    String alamat;

    @ColumnInfo(name="no_tlp")
    String no_tlp;

    @ColumnInfo(name="gender")
    String gender;

    @ColumnInfo(name="history_penyakit")
    String history_penyakit;

    @ColumnInfo(name="myimage")
    String myimage;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected User(Parcel in) {
        id = in.readInt();
        username = in.readString();
        password = in.readString();
        nama = in.readString();
        dob = in.readString();
        alamat = in.readString();
        no_tlp = in.readString();
        gender = in.readString();
        history_penyakit = in.readString();
        myimage = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeString(nama);
        parcel.writeString(dob);
        parcel.writeString(alamat);
        parcel.writeString(no_tlp);
        parcel.writeString(gender);
        parcel.writeString(history_penyakit);
        parcel.writeString(myimage);
    }
}
