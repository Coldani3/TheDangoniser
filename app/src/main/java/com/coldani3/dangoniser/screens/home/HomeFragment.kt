package com.coldani3.dangoniser.screens.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.coldani3.dangoniser.MainActivity
import com.coldani3.dangoniser.R
import com.coldani3.dangoniser.Util
import com.coldani3.dangoniser.data.EventData
import com.coldani3.dangoniser.data.TodoData
import com.coldani3.dangoniser.data.bases.DBCalendarEvent
import com.coldani3.dangoniser.databinding.FragmentHomeBinding;
import kotlinx.coroutines.launch
import sun.bob.mcalendarview.MCalendarView
import sun.bob.mcalendarview.MarkStyle
import sun.bob.mcalendarview.listeners.OnDateClickListener
import sun.bob.mcalendarview.listeners.OnMonthChangeListener
import sun.bob.mcalendarview.vo.DateData
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 * binding.upcomingEvents
 * binding.todoList
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home,container,false);

        binding.todoList.addItem(TodoData("Sample todo"));

        binding.upcomingEvents.setAddNavpath(R.id.action_homeFragment_to_eventFragment);

        lifecycleScope.launch {
            val data: List<DBCalendarEvent> = MainActivity.database.get().eventsDao().getAllEvents();
            Log.d(MainActivity.DEBUG_LOG_NAME, "Found " + data.size + " events!");

            if (data.size > 0) {
                for (event in data) {
                    binding.upcomingEvents.addItem(EventData(event), R.id.action_homeFragment_to_eventFragment);
                    Log.d(MainActivity.DEBUG_LOG_NAME, "Loaded event with name: " + event.eventName + " (uid :" + event.uid + ")");
                }
            } else {
                binding.upcomingEvents.addItem(EventData("Test"), R.id.action_homeFragment_to_eventFragment);
            }

            binding.upcomingEvents.invalidate();
        }

        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        binding.homeCalendarView.setMarkedStyle(MarkStyle.BACKGROUND);
        binding.homeCalendarView.travelTo(Util.calendarToDateData(Calendar.getInstance()));
        binding.homeCalendarView.setOnDateClickListener( object : OnDateClickListener() {
            override fun onDateClick(view: View, dateData: DateData) {
                dateChanged(view, dateData);
            }
        } );
//        binding.homeCalendarView.setOnMonthChangeListener(object : OnMonthChangeListener() {
//            override fun onMonthChange(year: Int, month: Int) {
//                monthChanged(year, month);
//            }
//        });

        highlightDatesWithEvents(binding.homeCalendarView);
        //make sure the calendar actually displays
        //binding.homeCalendarView.invalidate();
    }

    fun eventClicked(view: View) {

    }

    fun dateChanged(view: View, dateData: DateData) {
        val calendar: Calendar = Calendar.getInstance();
        calendar.set(dateData.year, dateData.month, dateData.day);

        val data: Bundle = bundleOf(MainActivity.DATE_PASS_ID to calendar);
        view.findNavController().navigate(R.id.action_homeFragment_to_eventListFragment, data);
    }

    fun monthChanged(year: Int, month: Int) {
        highlightDatesWithEvents(binding.homeCalendarView);
    }

    private fun highlightDatesWithEvents(calendar: MCalendarView) {
        for (date: Calendar in MainActivity.eventsManager.getAllDatesInMap()) {
            calendar.markDate(Util.calendarToDateData(date));
        }
    }
}