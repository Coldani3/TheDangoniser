package com.coldani3.dangoniser.data

import com.coldani3.dangoniser.Util
import com.coldani3.dangoniser.data.bases.DBCalendarEvent
import java.io.Serializable
import java.util.*

class EventData : Serializable {
    private var uid: Int = 0;
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

    constructor(event: DBCalendarEvent) {
        this.eventName = event.eventName;
        this.date = Util.millisToCalendar(event.date);
        this.until = Util.millisToCalendar(event.until);
        this.location = event.location;
        this.notes = event.notes;
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
        fun toDBObject(event: EventData): DBCalendarEvent {
            var eventOut: DBCalendarEvent;

            //if (event.uid != 0) {
            eventOut = DBCalendarEvent(event.eventName, event.date.timeInMillis, event.until.timeInMillis, event.location, event.notes);
            //} else {
                //eventOut = DBCalendarEvent(event.eventName, event.date.timeInMillis, event.until.timeInMillis, event.location, event.notes);
            //}

            if (event.uid != 0) {
                eventOut.uid = event.uid;
            }



            return eventOut;
        }
    }
}