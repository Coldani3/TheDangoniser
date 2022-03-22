package com.coldani3.dangoniser.data

import java.util.*
import kotlin.collections.HashMap

class DateIndexedMap<T> {
    private var map: HashMap<Calendar, MutableList<T>> = hashMapOf();

    public fun getByDate(date: Calendar): List<T> {
        return if (map.containsKey(date)) {
            map[date]!!;
        } else {
            emptyList();
        }
    }

    public fun add(date: Calendar, newObj: T) {
        if (map.containsKey(date)) {
            map[date]!!.add(newObj);
        } else {
            map[date] = mutableListOf(newObj);
        }
    }

    public fun removeAllOnDay(date: Calendar) {
        map.remove(date);
    }

    public fun getAllDatesInMap() : MutableSet<Calendar> {
        return map.keys;
    }
}