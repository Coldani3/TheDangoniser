package com.coldani3.dangoniser.components

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
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
        binding.notesText
    }

    fun onTextChanged(onChange: (String) -> Unit) {
        binding.notesText.afterTextChanged(onChange)
    }

    fun getText(): String {
        return binding.notesText.text.toString();
    }

    fun setText(text: String) {
        binding.notesText.text = Editable.Factory.getInstance().newEditable(text);
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }
}