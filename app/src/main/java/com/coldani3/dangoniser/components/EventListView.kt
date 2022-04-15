package com.coldani3.dangoniser.components

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.coldani3.dangoniser.data.EventData

class EventListView : AbstractListItemView<EventData, EventListItemView> {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun addItem(event: EventData) {
        val item: EventListItemView = EventListItemView(context);

        item.layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        item.setEventName(event.eventName);

        binding.eventsList.addView(item);
        item.invalidate();

        items.add(event);
    }
}