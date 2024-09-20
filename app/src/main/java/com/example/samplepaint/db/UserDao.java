package com.example.samplepaint.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Upsert;

import java.util.List;

@Dao
public interface UserDao{
    @Query("select * from users")
    List<User> getAll();

    @Insert
    void insertAll(User... users);

    @Update
    void updateAll(User... users);

    @Delete
    void deleteUser(User... users);
}

