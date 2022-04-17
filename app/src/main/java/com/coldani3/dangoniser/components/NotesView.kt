package com.coldani3.dangoniser.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.coldani3.dangoniser.R
import com.coldani3.dangoniser.databinding.NotesViewBinding

class NotesView : LinearLayout {
    private lateinit var binding: NotesViewBinding;

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LinearLayout.inflate(context, R.layout.notes_view, this);
        binding = NotesViewBinding.inflate(LayoutInflater.from(context), this);
    }

    fun getText(): String {
        return binding.notesText.text.toString();
    }
}