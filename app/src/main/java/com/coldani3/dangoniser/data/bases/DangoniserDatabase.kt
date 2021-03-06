package com.coldani3.dangoniser.data.bases

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DBCalendarEvent::class, DBTodoListItem::class], version = 2, exportSchema = false)
abstract class DangoniserDatabase : RoomDatabase() {
    abstract fun todoListDao(): TodoListDAO;
    abstract fun eventsDao(): EventsDAO;
}