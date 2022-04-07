package com.coldani3.dangoniser.data.bases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventsDAO {
    @Query("SELECT * FROM dbcalendarevent")
    fun getAllEvents(): List<DBCalendarEvent>;
    @Query("SELECT * FROM dbcalendarevent WHERE date = :dateMillis")
    fun getEventsForDay(dateMillis: Long): List<DBCalendarEvent>;

    @Insert
    fun insertEvent(event: DBCalendarEvent);
    @Insert
    fun insertEvents(vararg events: DBCalendarEvent);

    @Delete
    fun deleteEvent(event: DBCalendarEvent);
}