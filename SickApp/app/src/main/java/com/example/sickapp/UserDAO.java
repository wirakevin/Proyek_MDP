package com.example.sickapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM table_user")
    List<User> getAllUser();

    @Query("SELECT * FROM table_user WHERE nama LIKE '%' || :nama || '%' ")
    User getUserByName(String nama);

    @Query("SELECT * FROM table_user WHERE username LIKE '%' || :username || '%' ")
    User getUserByUsername(String username);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);
}
