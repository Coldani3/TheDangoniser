package com.coldani3.dangoniser.data

import java.util.*

class CalendarEvent {
    public var eventName: String;
    public var date: Date;
    public var until: Date;
    public var location: String;
    public var notes: String = "";


    constructor(name: String, date: Date = Calendar.getInstance().time, until: Date = Calendar.getInstance().time, location: String = "") {
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