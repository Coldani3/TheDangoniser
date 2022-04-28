package com.coldani3.dangoniser.screens.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.coldani3.dangoniser.MainActivity
import com.coldani3.dangoniser.R
import com.coldani3.dangoniser.Util
import com.coldani3.dangoniser.components.EventListItemView
import com.coldani3.dangoniser.data.EventData
import com.coldani3.dangoniser.data.TodoData
import com.coldani3.dangoniser.data.bases.DBCalendarEvent
import com.coldani3.dangoniser.data.bases.DBTodoListItem
import com.coldani3.dangoniser.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    private var calendarView: MCalendarView? = null;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home,container,false);

        binding.upcomingEvents.setAddNavpath(R.id.action_homeFragment_to_eventFragment);
        binding.todoList.setAddNavpath(R.id.action_homeFragment_to_todoFragment);

        /*if (calendarView != null) {
            Log.d(MainActivity.DEBUG_LOG_NAME, "Refresh calendar!");
            binding.calendarContainer.removeView(calendarView);
            calendarView = null;
        }*/

        calendarView = binding.homeCalendarView;//MCalendarView(this.context);

        /*binding.calendarContainer.addView(calendarView)
        binding.calendarContainer.invalidate();
        calendarView!!.invalidate();*/

        lifecycleScope.launch(Dispatchers.IO) {
            val data: List<DBCalendarEvent> = MainActivity.database.get().eventsDao().getAllEvents();
            Log.d(MainActivity.DEBUG_LOG_NAME, "Found " + data.size + " events!");
            val todos: List<DBTodoListItem> =
                MainActivity.database.get().todoListDao().getAllTodos();
            Log.d(MainActivity.DEBUG_LOG_NAME, "Found " + todos.size + " events!");

            withContext(Dispatchers.Main) {
                if (data.isNotEmpty()) {
                    //DEBUG
//                    val item: EventListItemView = EventListItemView(requireContext());
//
//                    item.layoutParams = RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    item.setEventData(EventData(data.first()));
//                    item.setEventNavpath(R.id.action_homeFragment_to_eventFragment);
//
//                    item.id = View.generateViewId();
//
//                    binding.debug.addView(item);

                    //DBEUG


                    for (event in data) {
                        val eventData: EventData = EventData(event);
                        binding.upcomingEvents.addItem(
                            eventData,
                            R.id.action_homeFragment_to_eventFragment
                        );


                        MainActivity.eventsManager.add(eventData.date, eventData);
                        Log.d(
                            MainActivity.DEBUG_LOG_NAME,
                            "Loaded event with name: " + event.eventName + " (uid :" + event.uid + ")"
                        );
                    }
                } else {
                    binding.upcomingEvents.addItem(
                        EventData("Sample event"),
                        R.id.action_homeFragment_to_eventFragment
                    );
                }

                highlightDatesWithEvents(/*binding.homeCalendarView*/calendarView!!);

                if (todos.isNotEmpty()) {
                    for (todo in todos) {
                        val todoData: TodoData = TodoData(todo);
                        binding.todoList.addItem(todoData, R.id.action_homeFragment_to_todoFragment);
                        MainActivity.todoListManager.add(todoData.forDate, todoData);
                    }
                } else {
                    binding.todoList.addItem(TodoData("Sample todo"));
                }

                binding.upcomingEvents.invalidate();
            }
        }


        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        /*binding.homeCalendarView.*/calendarView!!.setMarkedStyle(MarkStyle.BACKGROUND);
        /*binding.homeCalendarView.*/calendarView!!.travelTo(Util.calendarToDateData(Calendar.getInstance()));
        /*binding.homeCalendarView.*/calendarView!!.setOnDateClickListener( object : OnDateClickListener() {
            override fun onDateClick(view: View, dateData: DateData) {
                dateChanged(view, dateData);
            }
        } );


        //make sure the calendar actually displays
        //binding.homeCalendarView.invalidate();
    }

    fun eventClicked(view: View) {

    }

    fun dateChanged(view: View, dateData: DateData) {
        val calendar: Calendar = Calendar.getInstance();
        calendar.set(dateData.year, dateData.month - 1, dateData.day);

        val data: Bundle = bundleOf(MainActivity.DATE_PASS_ID to calendar);
        view.findNavController().navigate(R.id.action_homeFragment_to_eventListFragment, data);
    }

    fun monthChanged(year: Int, month: Int) {
        highlightDatesWithEvents(calendarView!!/*binding.homeCalendarView*/);
    }

    private fun highlightDatesWithEvents(calendar: MCalendarView) {
        for (date: Calendar in MainActivity.eventsManager.getAllDatesInMap()) {
            calendar.markDate(Util.calendarToDateData(date));
        }
    }
}