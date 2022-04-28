package com.coldani3.dangoniser

import androidx.room.RoomDatabase
import sun.bob.mcalendarview.vo.DateData
import java.lang.NullPointerException
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm");

        fun calendarToDateData(date: Calendar) : DateData {
            //not adding 1 makes the month in datedata wrong for some reason.
            return DateData(date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1, date.get(Calendar.DAY_OF_MONTH));
        }

        fun millisToCalendar(millis: Long) : Calendar {
            val calendar: Calendar = Calendar.getInstance();
            calendar.timeInMillis = millis;
            return calendar;
        }

        fun calendarToStringDate(date: Calendar): String {
            return dateFormat.format(date.time);
        }

        fun stringDateToCalendar(date: String): Calendar {
            val calendar: Calendar = Calendar.getInstance();
            calendar.time = dateFormat.parse(date);
            return calendar;
        }

        fun stringIsDateTime(date: String): Boolean {
            //TODO: ew
            try {
                stringDateToCalendar(date);
                return true;
            } catch (e: ParseException) {
                return false;
            }
        }
    }
}