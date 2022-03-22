package com.coldani3.dangoniser

import sun.bob.mcalendarview.vo.DateData
import java.util.*

class Util {
    companion object {
        fun calendarToDateData(date: Calendar) : DateData {
            return DateData(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        }
    }
}