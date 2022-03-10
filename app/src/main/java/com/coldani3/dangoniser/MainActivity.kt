package com.coldani3.dangoniser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.coldani3.dangoniser.data.CalendarEventsManager
import com.coldani3.dangoniser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    companion object {
        val eventsManager: CalendarEventsManager = CalendarEventsManager();
    }
}