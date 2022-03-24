package com.coldani3.dangoniser.screens.eventlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.coldani3.dangoniser.MainActivity
import com.coldani3.dangoniser.R
import com.coldani3.dangoniser.databinding.FragmentEventListBinding
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentEventListBinding>(inflater,
            R.layout.fragment_event_list,container,false)
        selectedDate = arguments?.get(MainActivity.DATE_PASS_ID) as Calendar;

        if (selectedDate == null) {
            selectedDate = Calendar.getInstance();
        }

        var year: Int = selectedDate.get(Calendar.YEAR);
        var month: Int = selectedDate.get(Calendar.MONTH);
        var day: Int = selectedDate.get(Calendar.DAY_OF_MONTH);

        binding.eventsForDay.text = "Events for day $year/$month/$day";

        return binding.root;
    }
}