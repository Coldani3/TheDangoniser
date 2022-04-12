package com.coldani3.dangoniser.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.navigation.findNavController
import com.coldani3.dangoniser.R
import com.coldani3.dangoniser.databinding.EventListItemBinding

class EventListItemView : RelativeLayout {
    private lateinit var binding: EventListItemBinding;
    private var eventName: String = "";
    private var navPath: Int = -1;

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.event_list_item, this);
        binding = EventListItemBinding.inflate(LayoutInflater.from(context), this);

        this.setOnClickListener { view -> onClicked(view) }

        val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.EventListItemView, 0, 0);
        binding.eventName.text = attributes.getString(R.styleable.EventListItemView_text);
        attributes.recycle();
    }

    fun setEventName(name: String) {
        eventName = name;
        binding.eventName.text = eventName;
    }

    //R.id.action_<etc.>
    fun setEventNavpath(path: Int) {
        navPath = path;
    }

    protected fun onClicked(view: View) {
        if (navPath != -1) {
            view.findNavController().navigate(navPath);
        }
    }
}