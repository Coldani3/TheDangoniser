package com.coldani3.dangoniser

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
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

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //mMap.
        } else {
            ActivityCompat.requestPermissions(this, Array<String>(1) {android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        if (!database.initialised()) {
            database.init(applicationContext, DangoniserDatabase::class.java, "dangoniser-database");
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

//        val testCal: Calendar = Calendar.getInstance();
//
//        testCal.set(testCal.get(Calendar.YEAR), testCal.get(Calendar.MONTH), testCal.get(Calendar.DAY_OF_MONTH) - 3);

        //eventsManager.add(testCal, EventData("Test", testCal));
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState);
    }

    companion object {
        val eventsManager: DateIndexedMap<EventData> = DateIndexedMap();
        val todoListManager: DateIndexedMap<TodoData> = DateIndexedMap();
        const val DATE_PASS_ID: String = "date";
        const val EVENT_DATA_PASS_ID: String = "eventData";
        const val TODO_DATA_PASS_ID: String = "todoData";
        const val SHARED_PREFERENCES: String = "dangoniser_preferences";
        var database: DatabaseSingleton<DangoniserDatabase> = DatabaseSingleton<DangoniserDatabase>();
        const val DEBUG_LOG_NAME: String = "Dangoniser";
    }
}