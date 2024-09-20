package com.example.samplepaint.db;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    public int uId;
    @ColumnInfo(name = "user_name")
    public String uName;
    @ColumnInfo(name = "password")
    public String password;
    @ColumnInfo(name = "age")
    public int age;

}

