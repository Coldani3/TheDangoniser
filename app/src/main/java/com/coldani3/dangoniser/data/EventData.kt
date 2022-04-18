package com.coldani3.dangoniser.data

import com.coldani3.dangoniser.Util
import com.coldani3.dangoniser.data.bases.DBCalendarEvent
import java.io.Serializable
import java.util.*

class EventData : Serializable {
    public var eventName: String;
    public var date: Calendar;
    public var until: Calendar;
    public var location: String;
    public var notes: String = "";

    constructor() {
        this.eventName = "";
        this.date = Calendar.getInstance();
        this.until = Calendar.getInstance();
        this.location = "";
    }

    constructor(name: String, date: Calendar = Calendar.getInstance(), until: Calendar = date, location: String = "") {
        this.eventName = name;
        this.date = date;
        this.until = until;
        this.location = location;
    }

    public fun updateNotes(newNotes: String) {
        this.notes = newNotes;
    }

    public fun updateLocation(newLocation: String) {
        this.location = newLocation;
    }

    companion object {
        fun fromDBObject(event: DBCalendarEvent): EventData {
            val eventOut: EventData = EventData(event.eventName,
                Util.millisToCalendar(event.date),
                Util.millisToCalendar(event.until),
                event.location);
            eventOut.updateNotes(event.notes);
            return eventOut;
        }
    }
}