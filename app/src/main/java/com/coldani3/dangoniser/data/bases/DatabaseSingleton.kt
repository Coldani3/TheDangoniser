package com.coldani3.dangoniser.data.bases

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

class DatabaseSingleton<T> where T : RoomDatabase {
    private lateinit var database: T;
    private var dbInitialsed: Boolean = false;

    fun init(context: Context, clazz: Class<T>, name: String) {
        database = Room.databaseBuilder(context, clazz, name).build();
        dbInitialsed = true;
    }

    fun get(): T {
        return database;
    }

    fun initialised(): Boolean {
        return dbInitialsed;
    }
}