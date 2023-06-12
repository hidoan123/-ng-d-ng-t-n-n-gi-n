package com.example.appdatdoan.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
@Database(entities = {DetailCart.class},version = 1)
public abstract class DetailCartDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "detailcart1.db";
    private static DetailCartDatabase instance;

    // ham tao cho instance
    public static synchronized DetailCartDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(), DetailCartDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract DetailCartDAO detailCartDAO();
}
