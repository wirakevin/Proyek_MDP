package com.example.sickapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_obat")
public class Obat implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    int id;

    @ColumnInfo(name="nama")
    String nama;

    @ColumnInfo(name="untuk_umur")
    String untuk_umur;

    @ColumnInfo(name="rasa")
    String rasa;

    @ColumnInfo(name="takaran")
    String takaran;

    @ColumnInfo(name="untuk_penyakit")
    String untuk_penyakit;

    public Obat(String nama, String untuk_umur, String rasa, String takaran, String untuk_penyakit) {
        this.nama = nama;
        this.untuk_umur = untuk_umur;
        this.rasa = rasa;
        this.takaran = takaran;
        this.untuk_penyakit = untuk_penyakit;
    }

    protected Obat(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        untuk_umur = in.readString();
        rasa = in.readString();
        takaran = in.readString();
        untuk_penyakit = in.readString();
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
        parcel.writeString(untuk_umur);
        parcel.writeString(rasa);
        parcel.writeString(takaran);
        parcel.writeString(untuk_penyakit);
    }

    @Override
    public String toString() {
        return nama;
    }
}
