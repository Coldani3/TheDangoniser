package com.coldani3.dangoniser.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.coldani3.dangoniser.R
import com.coldani3.dangoniser.data.CalendarEvent
import com.coldani3.dangoniser.databinding.ItemListBinding


abstract class ListItemView : LinearLayout {
    protected var binding: ItemListBinding;

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.item_list, this);
        binding = ItemListBinding.inflate(LayoutInflater.from(context), this);
    }

    public abstract fun addItem(event: CalendarEvent);
}