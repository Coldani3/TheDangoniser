package com.coldani3.dangoniser.data.bases

import androidx.room.*

@Dao
interface EventsDAO {
    @Query("SELECT * FROM dbcalendarevent")
    suspend fun getAllEvents(): List<DBCalendarEvent>;
    @Query("SELECT * FROM dbcalendarevent WHERE date BETWEEN :now AND :now + (:x * 86400000)")
    suspend fun getEventsWithinXDaysOfNow(now: Long, x: Int): List<DBCalendarEvent>;
    @Query("SELECT * FROM dbcalendarevent WHERE date BETWEEN :dateMillis AND :dateMillis + 86400000")
    suspend fun getEventsForDay(dateMillis: Long): List<DBCalendarEvent>;
    @Query("SELECT * FROM dbcalendarevent WHERE uid = :uid")
    suspend fun getEventByUID(uid: Int): DBCalendarEvent

    @Insert
    suspend fun insertEvent(event: DBCalendarEvent);
    @Insert
    suspend fun insertEvents(vararg events: DBCalendarEvent);

    @Update
    suspend fun updateEvent(event: DBCalendarEvent);
    @Update
    suspend fun updateEvents(vararg events: DBCalendarEvent);

    @Delete
    suspend fun deleteEvent(event: DBCalendarEvent);
}