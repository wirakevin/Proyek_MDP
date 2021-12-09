package com.example.sickapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_gejala")
public class Gejala implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    int id;

    @ColumnInfo(name="nama")
    String nama;

    public Gejala(String nama) {
        this.nama = nama;
    }

    protected Gejala(Parcel in) {
        id = in.readInt();
        nama = in.readString();
    }

    public static final Creator<Gejala> CREATOR = new Creator<Gejala>() {
        @Override
        public Gejala createFromParcel(Parcel in) {
            return new Gejala(in);
        }

        @Override
        public Gejala[] newArray(int size) {
            return new Gejala[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nama);
    }

    @Override
    public String toString() {
        return nama;
    }
}
