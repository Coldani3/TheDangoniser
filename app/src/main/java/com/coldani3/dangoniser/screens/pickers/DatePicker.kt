package com.coldani3.dangoniser.screens.pickers

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

//https://developer.android.com/guide/topics/ui/controls/pickers
class DatePicker : DialogFragment, DatePickerDialog.OnDateSetListener {
    private var dateSet: ((DatePicker?, Int, Int, Int) -> Unit)? = null;

    private var year: Int;
    private var month: Int;
    private var dayOfMonth: Int;

    constructor(initialYear: Int, intialMonth: Int, initialDayOfMonth: Int) : super() {
        this.year = initialYear;
        this.month = intialMonth;
        this.dayOfMonth = initialDayOfMonth;
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        //val c: Calendar = Calendar.getInstance()
//        val year: Int = year;//c.get(Calendar.YEAR)
//        val month: Int = month;//c.get(Calendar.MONTH)
//        val day: Int = dayOfMonth;//c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireContext(), this, year, month, dayOfMonth)
    }

    fun setOnDateSet(onDateSet: ((DatePicker?, Int, Int, Int) -> Unit)) {
        dateSet = onDateSet;
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        if (dateSet != null) {
            dateSet!!(p0, p1, p2, p3);
        }
    }
}