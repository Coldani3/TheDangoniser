package com.coldani3.dangoniser.screens.event

import android.annotation.SuppressLint
import android.content.Context
import android.location.*
import android.media.metrics.Event
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.coldani3.dangoniser.MainActivity
import com.coldani3.dangoniser.R
import com.coldani3.dangoniser.Util
import com.coldani3.dangoniser.data.EventData
import com.coldani3.dangoniser.data.bases.DBCalendarEvent
import com.coldani3.dangoniser.databinding.FragmentEventBinding
import com.coldani3.dangoniser.databinding.FragmentEventListBinding
import com.coldani3.dangoniser.screens.pickers.DatePicker
import com.coldani3.dangoniser.screens.pickers.TimePicker
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [EventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventFragment : Fragment() {
    private lateinit var eventData: EventData;
    private lateinit var locationManager: LocationManager;
    private lateinit var binding: FragmentEventBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentEventBinding>(inflater,
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
        binding.notes.setText(eventData.notes);
        binding.hereButton.setOnClickListener { view -> updateCurrentLocation(view) }

        binding.doneButton.setOnClickListener { view -> updateDB(); };
        binding.notes.onTextChanged { text ->
            eventData.notes = text;
        }

        binding.eventName.afterTextChanged { it ->
            eventData.eventName = it;
        }

        binding.atInput.setTextIsSelectable(false);
        binding.atInput.setOnTouchListener() { view, event -> {
                if (event.action == MotionEvent.ACTION_UP) {
                    Util.dateTimeSelect(binding.atInput, eventData.date, requireActivity().supportFragmentManager);
                }
                view.performClick();
            }()
        }

        binding.untilInput.setTextIsSelectable(false);
        binding.untilInput.setOnTouchListener { view, event -> {
                if (event.action == MotionEvent.ACTION_UP) {
                    Util.dateTimeSelect(binding.untilInput, eventData.until, requireActivity().supportFragmentManager);
                }
                view.performClick();
            }()
        }

        //binding.reminderDropdown.

        binding.whereInput.afterTextChanged { it ->
            eventData.location = it;
        }

        return binding.root;
    }



    @SuppressWarnings("MissingPermission")
    fun getLongitudeLatitude(): MutableList<Double> {
        val provider: String? = locationManager.getBestProvider(Criteria(), false/*true*/);

        if (provider != null) {
            val locations: Location = locationManager.getLastKnownLocation(provider)!!;
            val providerList: List<String> = locationManager.allProviders;

            if (!providerList.isNullOrEmpty()) {
                val longitude: Double = locations.longitude;
                val latitude: Double = locations.latitude;

                return mutableListOf(longitude, latitude);
            }

            Log.d(MainActivity.DEBUG_LOG_NAME, "Provider List null or empty.")
        } else {
            Log.d(MainActivity.DEBUG_LOG_NAME, "Provider null")
        }

        return mutableListOf(0.0, 0.0);
    }

    @SuppressLint("MissingPermission")
    fun getHereLocation(): String {
        val longitudeLatitude: MutableList<Double> = getLongitudeLatitude();

        if (longitudeLatitude[0] != 0.0 && longitudeLatitude[1] != 0.0) {
            val longitude: Double = longitudeLatitude[0];
            val latitude: Double = longitudeLatitude[1];
            val geocoder: Geocoder = Geocoder(context, Locale.getDefault());

            try {
                val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1);

                if (!addresses.isNullOrEmpty()) {
                    return addresses[0].getAddressLine(0);
                }
            } catch (e: IOException) {
                Log.e(MainActivity.DEBUG_LOG_NAME, Log.getStackTraceString(e));
                return requireContext().getString(R.string.cannot_find_location_lon_lat, longitude.toString(), latitude.toString());
            }
        }

        return requireContext().getString(R.string.cannot_find_location);

        //https://stackoverflow.com/a/6922448
//        val locationManager: LocationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager;
        /*val provider: String? = locationManager.getBestProvider(Criteria(), true);

        if (provider != null) {
            val locations: Location = locationManager.getLastKnownLocation(provider)!!;
            val providerList: List<String> = locationManager.allProviders;

            if (!providerList.isNullOrEmpty()) {
                val longitude: Double = locations.longitude;
                val latitude: Double = locations.latitude;
                val geocoder: Geocoder = Geocoder(context, Locale.getDefault());

                try {
                    val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1);

                    if (!addresses.isNullOrEmpty()) {
                        return addresses[0].getAddressLine(0);
                    }
                } catch (e: IOException) {
                    Log.e(MainActivity.DEBUG_LOG_NAME, Log.getStackTraceString(e));
                    return "Could not get location name ($latitude, $longitude)";
                }
            }

        }

        return "Could not get location";*/

    }

    fun updateCurrentLocation(view: View) {
        val here: String = getHereLocation();
        binding.whereInput.text = Editable.Factory.getInstance().newEditable(here);
        eventData.location = here;
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