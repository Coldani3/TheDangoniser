package com.coldani3.dangoniser.screens.eventlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.coldani3.dangoniser.MainActivity
import com.coldani3.dangoniser.R
import com.coldani3.dangoniser.components.TodoListItemView
import com.coldani3.dangoniser.data.EventData
import com.coldani3.dangoniser.data.TodoData
import com.coldani3.dangoniser.data.bases.DBCalendarEvent
import com.coldani3.dangoniser.databinding.FragmentEventListBinding
import kotlinx.coroutines.launch
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EventListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventListFragment : Fragment() {
    private lateinit var selectedDate: Calendar;
    private lateinit var binding: FragmentEventListBinding;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentEventListBinding>(inflater,
            R.layout.fragment_event_list,container,false)

        if (savedInstanceState != null) {
            val calendar: Calendar? = savedInstanceState.getSerializable(DATE_KEY) as Calendar;

            if (calendar != null) {
                selectedDate = calendar!!;
            }
        } else if (arguments != null) {
            selectedDate = arguments?.get(MainActivity.DATE_PASS_ID) as Calendar;
        }

        if (selectedDate == null) {
            selectedDate = Calendar.getInstance();
        }

        var year: Int = selectedDate.get(Calendar.YEAR);
        var month: Int = selectedDate.get(Calendar.MONTH);
        var day: Int = selectedDate.get(Calendar.DAY_OF_MONTH);

        binding.eventsForDay.text = "Events for day $year/$month/$day";

        lifecycleScope.launch {
            val events: List<DBCalendarEvent> =
                MainActivity.database.get().eventsDao().getEventsForDay(selectedDate.timeInMillis);
            Log.d(MainActivity.DEBUG_LOG_NAME, "Loaded " + events.size + " events for time " + selectedDate.timeInMillis);

            if (events.size > 0) {
                for (event in events) {
                    binding.events.addItem(EventData(event));
                }
            } else {
                binding.events.addItem(EventData("Sample Event"), R.id.action_eventListFragment_to_eventFragment);
            }
        }

        addTodo(TodoData("Sample todo"));

        return binding.root;
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(DATE_KEY, selectedDate);
    }

    fun addTodo(todo: TodoData) {
        binding.todoList.addItem(todo, ::onChecked, ::editPressed, ::deletePressed);
    }

    fun onChecked(view: View, checked: Boolean) {

    }

    fun editPressed(view: View) {
        view.findNavController().navigate(R.id.action_eventListFragment_to_eventFragment);
        Log.d(MainActivity.DEBUG_LOG_NAME, "Edit pressed.");
    }

    fun deletePressed(view: View) {

    }

    companion object {
        const val DATE_KEY = "eventListDate";
    }
}