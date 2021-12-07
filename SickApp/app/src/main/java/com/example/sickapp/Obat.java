package com.example.sickapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "table_obat")
public class Obat implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    int id;

    @ColumnInfo(name="nama")
    String nama;

    @ColumnInfo(name="rasa")
    String rasa;

    @ColumnInfo(name="untuk_umur")
    String untuk_umur;

    @ColumnInfo(name="takaran")
    String takaran;

    @ColumnInfo(name="harga")
    String harga;

    @ColumnInfo(name="gejala")
    String gejala;

    public Obat(String nama, String untuk_umur, String takaran, String gejala) {
        this.nama = nama;
        this.untuk_umur = untuk_umur;
        this.takaran = takaran;
        this.gejala = gejala;
    }

    protected Obat(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        rasa = in.readString();
        untuk_umur = in.readString();
        takaran = in.readString();
        harga = in.readString();
        gejala = in.readString();
    }

    public static final Creator<Obat> CREATOR = new Creator<Obat>() {
        @Override
        public Obat createFromParcel(Parcel in) {
            return new Obat(in);
        }

        @Override
        public Obat[] newArray(int size) {
            return new Obat[size];
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
        parcel.writeString(rasa);
        parcel.writeString(untuk_umur);
        parcel.writeString(takaran);
        parcel.writeString(harga);
        parcel.writeString(gejala);
    }
}
