package com.example.sickapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GejalaDAO {
    @Query("SELECT * FROM table_gejala")
    List<Gejala> getAllGejala();

    @Query("SELECT * FROM table_gejala WHERE nama LIKE '%' || :nama || '%' ")
    List<Gejala> getGejalaByName(String nama);

    @Insert
    void insert(Gejala gejala);

    @Delete
    void delete(Gejala gejala);

    @Update
    void update(Gejala gejala);
}
