package com.coldani3.dangoniser.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.coldani3.dangoniser.R
import com.coldani3.dangoniser.databinding.EventListItemBinding
import com.coldani3.dangoniser.databinding.TodoListItemBinding

class EventListItemView : RelativeLayout {
    private lateinit var binding: EventListItemBinding;

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.event_list_item, this);
        binding = EventListItemBinding.inflate(LayoutInflater.from(context), this);
    }
}