package com.example.sickapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Obat.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract ObatDAO obatDAO();

    public static AppDatabase database = null;
    public static void initDatabase(Context context, String databaseName){
        if(database == null){
            database = Room.databaseBuilder(context, AppDatabase.class, databaseName).build();
        }
    }
}
