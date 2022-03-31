package com.coldani3.dangoniser.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.ScrollView
import com.coldani3.dangoniser.R
import com.coldani3.dangoniser.data.CalendarEvent
import com.coldani3.dangoniser.databinding.EventListBinding


class EventListView : LinearLayout {
    private var binding: EventListBinding;

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.event_list, this);
        binding = EventListBinding.inflate(LayoutInflater.from(context), this);
    }

    public fun addEvent(event: CalendarEvent) {
        val newItem: EventListItemView = EventListItemView(context);
        newItem.setEventName(event.eventName);
        binding.eventsList.addView(newItem);
    }
}