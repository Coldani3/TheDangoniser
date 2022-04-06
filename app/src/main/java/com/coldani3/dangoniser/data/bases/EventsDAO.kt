package com.coldani3.dangoniser.data.bases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query

@Dao
interface EventsDAO {
    @Query("SELECT * FROM dbcalendarevent")
    fun getAllEvents(): List<DBCalendarEvent>;

    @Query("SELECT * FROM dbcalendarevent WHERE date = :dateMillis")
    fun getEventsForDay(dateMillis: Long): List<DBCalendarEvent>;


    @Delete
    fun deleteEvent(event: DBCalendarEvent);
}