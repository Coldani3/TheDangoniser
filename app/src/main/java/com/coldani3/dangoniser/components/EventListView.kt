package com.coldani3.dangoniser.components

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.coldani3.dangoniser.data.CalendarEvent

class EventListView : AbstractListItemView<CalendarEvent> {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    public override fun addItem(event: CalendarEvent) {
        val newItem: EventListItemView = EventListItemView(context);
        newItem.layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        newItem.setEventName(event.eventName);
        binding.eventsList.addView(newItem);
        newItem.invalidate();
    }
}