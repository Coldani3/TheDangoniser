package com.coldani3.dangoniser.data

import java.util.*

class CalendarEvent {
    public var eventName: String;
    public var date: Calendar;
    public var until: Calendar;
    public var location: String;
    public var notes: String = "";


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
}