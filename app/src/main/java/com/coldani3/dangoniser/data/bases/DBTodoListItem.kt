package com.coldani3.dangoniser.data.bases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DBTodoListItem(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "checked") val checked: Boolean,
    @ColumnInfo(name = "forDate") val forDate: Long
)