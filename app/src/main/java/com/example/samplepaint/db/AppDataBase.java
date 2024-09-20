package com.example.samplepaint.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class}, version = 4)
public abstract class AppDataBase extends RoomDatabase {
    public abstract UserDao userDao();

    private static AppDataBase Instance;

    public static AppDataBase getInstance(Context context) {
        if(Instance == null){
            Instance = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class, "database-name")
                    .addMigrations(DatabaseMigration.MIGRATION_3_4_ChangeColumnName)
                    .allowMainThreadQueries()
                    .build();
        }
        return Instance;
    }
}