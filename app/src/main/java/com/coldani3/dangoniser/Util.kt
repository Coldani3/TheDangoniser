package com.coldani3.dangoniser

import android.text.Editable
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import androidx.room.RoomDatabase
import com.coldani3.dangoniser.screens.pickers.DatePicker
import com.coldani3.dangoniser.screens.pickers.TimePicker
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

        fun startOfDay(day: Calendar): Calendar {
            val startOfDay: Calendar = Calendar.getInstance();
            startOfDay.set(Calendar.HOUR_OF_DAY, 0);
            startOfDay.set(Calendar.MINUTE, 0);
            startOfDay.set(Calendar.SECOND, 0);
            startOfDay.set(Calendar.MILLISECOND, 0);
            startOfDay.set(day.get(Calendar.YEAR), day.get(Calendar.MONTH), day.get(Calendar.DAY_OF_MONTH));
            return startOfDay;
        }

        fun dateTimeSelect(input: EditText, date: Calendar, fragmentManager: FragmentManager) {
            var calendar: Calendar = Calendar.getInstance();

            if (date != null) {
                calendar = date;
            }

            val datePicker: DatePicker = DatePicker(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePicker.setOnDateSet { datePicker, year, month, day ->
                val timePicker: TimePicker = TimePicker(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
                timePicker.setOnTimeSet { timePicker, hourOfDay, minute ->
                    calendar.set(year, month, day);
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);

                    date.set(year, month, day, hourOfDay, minute);

                    input.text = Editable.Factory.getInstance().newEditable(Util.calendarToStringDate(calendar));
                }
                timePicker.show(fragmentManager/*requireActivity().supportFragmentManager*/, "timePicker");
            }

            datePicker.show(fragmentManager/*requireActivity().supportFragmentManager*/, "datePicker");
        }
    }
}