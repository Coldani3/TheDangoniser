package com.coldani3.dangoniser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.coldani3.dangoniser.data.EventData
import com.coldani3.dangoniser.data.DateIndexedMap
import com.coldani3.dangoniser.data.TodoData
import com.coldani3.dangoniser.data.bases.DangoniserDatabase
import com.coldani3.dangoniser.data.bases.DatabaseSingleton
import com.coldani3.dangoniser.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        val testCal: Calendar = Calendar.getInstance();

        testCal.set(testCal.get(Calendar.YEAR), testCal.get(Calendar.MONTH), testCal.get(Calendar.DAY_OF_MONTH) - 3);

        eventsManager.add(testCal, EventData("Test", testCal));

        if (!database.initialised()) {
            database.init(applicationContext, DangoniserDatabase::class.java, "dangoniser-database");
        }
    }

    companion object {
        val eventsManager: DateIndexedMap<EventData> = DateIndexedMap();
        val todoListManager: DateIndexedMap<TodoData> = DateIndexedMap();
        val DATE_PASS_ID: String = "date";
        var database: DatabaseSingleton<DangoniserDatabase> = DatabaseSingleton<DangoniserDatabase>();
        val DEBUG_LOG_NAME: String = "Dangoniser";
    }
}