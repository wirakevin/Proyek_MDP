package com.example.sickapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DiseaseDAO {
    @Query("SELECT * FROM table_disease")
    List<Disease> getAllDisease();

    @Query("SELECT * FROM table_disease WHERE nama LIKE '%' || :nama || '%' ")
    List<Disease> getDiseaseByName(String nama);

    @Insert
    void insert(Disease disease);

    @Delete
    void delete(Disease disease);

    @Update
    void update(Disease disease);
}
