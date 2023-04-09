package com.example.imageviewer;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface imageDao {
    @Query("SELECT * FROM links")
    List<LinksEntity> getAll();

    @Insert
    long insertEmployee(LinksEntity employeeEntity);



    @Delete
    void deleteEmployee(LinksEntity employeeEntity);
}