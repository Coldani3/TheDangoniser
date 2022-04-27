package com.coldani3.dangoniser.components

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.TypedArray
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.coldani3.dangoniser.MainActivity
import com.coldani3.dangoniser.R
import com.coldani3.dangoniser.data.EventData
import com.coldani3.dangoniser.databinding.EventListItemBinding
import kotlinx.coroutines.launch

class EventListItemView : RelativeLayout {
    private lateinit var binding: EventListItemBinding;
    private var eventName: String = "";
    private var eventData: EventData? = null;
    private var navPath: Int = -1;

    private var onDelete: ((View, EventData?) -> Unit)? = null;

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.event_list_item, this);
        binding = EventListItemBinding.inflate(LayoutInflater.from(context), this);

        binding.deleteButton/*findViewById<Button>(R.id.delete_button)*/.setOnClickListener { view -> onDeleteButtonPressed(view); Log.d(MainActivity.DEBUG_LOG_NAME, "delete") };
        binding.itemBody.setOnClickListener { view -> onClicked(view) };

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

    fun setEventData(data: EventData) {
        eventData = data;
    }

    fun onDeletePressed(onPressed: (View, EventData?) -> Unit) {
        onDelete = onPressed;
    }

    protected fun onClicked(view: View) {
        if (navPath != -1) {
            if (eventData != null) {
                val data: Bundle = bundleOf(MainActivity.EVENT_DATA_PASS_ID to eventData!!);
                view.findNavController().navigate(navPath, data);
            } else {
                view.findNavController().navigate(navPath);
            }
        }
    }

    protected fun deleteSelf(view: View) {
        if (onDelete != null) {
            onDelete!!(view, eventData);
        }

        if (eventData != null) {
            Log.d(MainActivity.DEBUG_LOG_NAME, "Delete button with uid: " + eventData!!.uid);
            val data: EventData = eventData!!;

            findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                data.deleteFromDB();
            }

            (parent as ViewGroup).removeView(this);
        }

        Toast.makeText(context, R.string.deletedEvent, Toast.LENGTH_SHORT).show();
    }

    protected fun onDeleteButtonPressed(view: View) {
        Log.d(MainActivity.DEBUG_LOG_NAME, "Delete button pressed.");

        AlertDialog.Builder(context)
            .setTitle("Delete Event")
            .setMessage("Are you sure you want to delete this event?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.ok) {
                    dialogInterface, whichButton -> deleteSelf(view);
            }.setNegativeButton(android.R.string.cancel, null)
            .show();
    }
}