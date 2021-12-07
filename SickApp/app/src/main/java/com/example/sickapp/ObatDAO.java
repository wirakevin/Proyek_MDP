package com.example.sickapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ObatDAO {
    @Query("SELECT * FROM table_obat")
    List<Obat> getAllObat();

    @Query("SELECT * FROM table_obat WHERE nama LIKE '%' || :nama || '%' ")
    List<Obat> getObatByName(String nama);

    @Insert
    void insert(Obat obat);

    @Delete
    void delete(Obat obat);

    @Update
    void update(Obat obat);
}
