package com.example.sickapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_disease")
public class Disease implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    int id;

    @ColumnInfo(name="nama")
    String nama;

    @ColumnInfo(name="gejala")
    String gejala;

    public Disease(String nama, String gejala) {
        this.nama = nama;
        this.gejala = gejala;
    }

    protected Disease(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        gejala = in.readString();
    }

    public static final Creator<Disease> CREATOR = new Creator<Disease>() {
        @Override
        public Disease createFromParcel(Parcel in) {
            return new Disease(in);
        }

        @Override
        public Disease[] newArray(int size) {
            return new Disease[size];
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
        parcel.writeString(gejala);
    }

    @Override
    public String toString() {
        return nama;
    }
}
