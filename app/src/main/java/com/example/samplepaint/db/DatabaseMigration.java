package com.example.samplepaint.db;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseMigration{
    public static final Migration Migration_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL("ALTER TABLE User ADD COLUMN age INTEGER NOT NULL DEFAULT 0");
        }
    };

    public static final Migration MIGRATION_3_4_ChangeColumnName = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE user_temp ("
                    + "user_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "user_name TEXT, "
                    + "password TEXT, "
                    + "age INTEGER NOT NULL)"
            );
            database.execSQL("INSERT INTO user_temp(user_id,user_name,password,age)"+"SELECT user_id,user_name,password,age FROM users");
            database.execSQL("DROP TABLE users");
            database.execSQL("ALTER TABLE user_temp RENAME TO users ");
        }
    };
}
