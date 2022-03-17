package com.coldani3.dangoniser.data

import java.util.*
import kotlin.collections.HashMap

class DateIndexedMap<T> {
    private var map: HashMap<Date, MutableList<T>> = hashMapOf();

    constructor() {
        loadEvents();
    }

    private fun loadEvents() {

    }

    public fun getByDate(date: Date): List<T> {
        if (map.containsKey(date)) {
            return map[date]!!;
        } else {
            return emptyList();
        }
    }

    public fun add(date: Date, newObj: T) {
        if (map.containsKey(date)) {
            map[date]!!.add(newObj);
        } else {
            map[date] = mutableListOf(newObj);
        }
    }

    public fun removeAllOnDay(date: Date) {
        map.remove(date);
    }
}