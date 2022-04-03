package com.coldani3.dangoniser.data.bases

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity
data class DBCalendarEvent(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "eventName") val eventName: String,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "until") val until: Long,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "notes") val notes: String,
)
