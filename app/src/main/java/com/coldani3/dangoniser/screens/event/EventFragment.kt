package com.coldani3.dangoniser.screens.event

import android.media.metrics.Event
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.coldani3.dangoniser.MainActivity
import com.coldani3.dangoniser.R
import com.coldani3.dangoniser.Util
import com.coldani3.dangoniser.data.EventData
import com.coldani3.dangoniser.data.bases.DBCalendarEvent
import com.coldani3.dangoniser.databinding.FragmentEventBinding
import com.coldani3.dangoniser.databinding.FragmentEventListBinding
import kotlinx.coroutines.launch

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

        if (savedInstanceState != null) {
            eventData = savedInstanceState.getSerializable(EVENT_DATA_ID) as EventData;
        } else if (arguments != null) {
            eventData = arguments?.get(MainActivity.EVENT_DATA_PASS_ID) as EventData;
        } else {
            eventData = EventData("Write title here");
        }

        binding.eventName.text = Editable.Factory.getInstance().newEditable(eventData.eventName)
        binding.atInput.text = Editable.Factory.getInstance().newEditable(Util.calendarToStringDate(eventData.date));
        binding.untilInput.text = Editable.Factory.getInstance().newEditable(Util.calendarToStringDate(eventData.until));
        binding.whereInput.text = Editable.Factory.getInstance().newEditable(eventData.location);

        binding.doneButton.setOnClickListener { view -> updateDB(); };

        binding.eventName.afterTextChanged { it ->
            eventData.eventName = it;
        }

        binding.atInput.afterTextChanged { it ->
            eventData.date = Util.stringDateToCalendar(it);
        }

        binding.untilInput.afterTextChanged { it ->
            eventData.until = Util.stringDateToCalendar(it);
        }

        binding.whereInput.afterTextChanged { it ->
            eventData.location = it;
        }

        return binding.root;
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(EVENT_DATA_ID, eventData);
    }

    fun updateDB() {
        lifecycleScope.launch {
            val prevEvent: DBCalendarEvent = MainActivity.database.get().eventsDao().getEventByUID(eventData.uid);

            if (prevEvent != null) {
                eventData.updateDB();
            } else {
                Log.d(MainActivity.DEBUG_LOG_NAME, "Could not find matching event in database for ID: " + eventData.uid);
                MainActivity.database.get().eventsDao().insertEvent(EventData.toDBObject(eventData));
            }

            findNavController().navigate(R.id.action_eventFragment_to_homeFragment);
        }
    }

    //https://stackoverflow.com/a/40569759
    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    companion object {
        const val EVENT_DATA_ID = "eventData";
    }
}