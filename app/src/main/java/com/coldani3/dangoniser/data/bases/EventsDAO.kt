package com.coldani3.dangoniser.data.bases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventsDAO {
    @Query("SELECT * FROM dbcalendarevent")
    suspend fun getAllEvents(): List<DBCalendarEvent>;
    @Query("SELECT * FROM dbcalendarevent WHERE date = :dateMillis")
    suspend fun getEventsForDay(dateMillis: Long): List<DBCalendarEvent>;

    @Insert
    suspend fun insertEvent(event: DBCalendarEvent);
    @Insert
    suspend fun insertEvents(vararg events: DBCalendarEvent);

    @Delete
    suspend fun deleteEvent(event: DBCalendarEvent);
}