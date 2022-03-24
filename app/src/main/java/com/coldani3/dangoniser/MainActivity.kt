package com.coldani3.dangoniser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.coldani3.dangoniser.data.CalendarEvent
import com.coldani3.dangoniser.data.DateIndexedMap
import com.coldani3.dangoniser.data.TodoListItem
import com.coldani3.dangoniser.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        val testCal: Calendar = Calendar.getInstance();

        testCal.set(testCal.get(Calendar.YEAR), testCal.get(Calendar.MONTH), testCal.get(Calendar.DAY_OF_MONTH) - 3);

        eventsManager.add(testCal, CalendarEvent("Test", testCal));
    }

    companion object {
        val eventsManager: DateIndexedMap<CalendarEvent> = DateIndexedMap();
        val todoListManager: DateIndexedMap<TodoListItem> = DateIndexedMap();
        val DATE_PASS_ID: String = "date";
    }
}