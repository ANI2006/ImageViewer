package com.example.imageviewer;



import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {LinksEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract imageDao imageDao();
}