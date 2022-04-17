package com.coldani3.dangoniser.screens.event

import android.media.metrics.Event
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.coldani3.dangoniser.MainActivity
import com.coldani3.dangoniser.R
import com.coldani3.dangoniser.Util
import com.coldani3.dangoniser.data.EventData
import com.coldani3.dangoniser.databinding.FragmentEventBinding
import com.coldani3.dangoniser.databinding.FragmentEventListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [EventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventFragment : Fragment() {
    private lateinit var eventData: EventData;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentEventBinding>(inflater,
            R.layout.fragment_event,container,false);

        eventData = arguments?.get(MainActivity.EVENT_DATA_PASS_ID) as EventData;

        binding.eventName.text = Editable.Factory.getInstance().newEditable(eventData.eventName)
        binding.atInput.text = Editable.Factory.getInstance().newEditable(Util.calendarToStringDate(eventData.date));
        binding.untilInput.text = Editable.Factory.getInstance().newEditable(Util.calendarToStringDate(eventData.until));
        binding.whereInput.text = Editable.Factory.getInstance().newEditable(eventData.location);

        return binding.root;
    }
}