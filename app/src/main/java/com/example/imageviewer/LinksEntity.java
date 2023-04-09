package com.example.imageviewer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "links")
public class LinksEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    public String getName() {
        return name;
    }



    public LinksEntity(String name) {
        this.name = name;

    }

}