package com.coldani3.dangoniser.screens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.databinding.DataBindingUtil
import com.coldani3.dangoniser.R
import com.coldani3.dangoniser.databinding.FragmentHomeBinding;
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home,container,false);
        binding.homeCalendarView.setDate(Calendar.getInstance().timeInMillis);
        binding.homeCalendarView.setOnDateChangeListener { calendarView, year, month, day -> onDateChange(calendarView, year, month, day)};
        return binding.root;
    }

    fun onDateChange(calendarView: CalendarView, year: Int, month: Int, day: Int) {
        val calendar: Calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        val date: Date = calendar.time;
    }
}