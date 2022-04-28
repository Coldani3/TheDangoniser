package com.coldani3.dangoniser.screens.pickers

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import android.text.format.DateFormat
import java.util.*

//https://developer.android.com/guide/topics/ui/controls/pickers
class TimePicker : DialogFragment, TimePickerDialog.OnTimeSetListener {
    private var timeSet: ((TimePicker?, Int, Int) -> Unit)? = null;
    private var hour: Int;
    private var minute: Int;

    constructor(initialHour: Int, initialMinute: Int) : super() {
        hour = initialHour;
        minute = initialMinute;
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

//        val c = Calendar.getInstance()
//        val hour = c.get(Calendar.HOUR_OF_DAY)
//        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    fun setOnTimeSet(onTimeSet: ((TimePicker?, Int, Int) -> Unit)?) {
        timeSet = onTimeSet;
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        if (timeSet != null) {
            timeSet!!(p0, p1, p2);
        }
    }
}