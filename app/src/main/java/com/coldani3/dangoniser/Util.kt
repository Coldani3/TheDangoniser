package com.coldani3.dangoniser

import androidx.room.RoomDatabase
import sun.bob.mcalendarview.vo.DateData
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object {
        fun calendarToDateData(date: Calendar) : DateData {
            return DateData(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        }

        fun millisToCalendar(millis: Long) : Calendar {
            val calendar: Calendar = Calendar.getInstance();
            calendar.timeInMillis = millis;
            return calendar;
        }

        fun calendarToStringDate(date: Calendar): String {
            val format: String = "yyyy-MM-dd HH:mm";

            val dateFormat: DateFormat = SimpleDateFormat(format);

            return dateFormat.format(date.time);
        }
    }
}