package com.coldani3.dangoniser.data

import com.coldani3.dangoniser.data.CalendarEvent
import java.util.*
import kotlin.collections.HashMap

class CalendarEventsManager {
    private var events: HashMap<Date, MutableList<CalendarEvent>> = hashMapOf();

    constructor() {
        loadEvents();
    }

    private fun loadEvents() {

    }

    public fun getEventsByDate(date: Date): List<CalendarEvent> {
        if (events.containsKey(date)) {
            return events[date]!!;
        } else {
            return emptyList();
        }
    }

    public fun addEvent(date: Date, calendarEvent: CalendarEvent) {
        if (events.containsKey(date)) {
            events[date]!!.add(calendarEvent);
        } else {
            events[date] = mutableListOf(calendarEvent);
        }
    }

    public fun removeAllEventsOnDay(date: Date) {
        events.remove(date);
    }
}