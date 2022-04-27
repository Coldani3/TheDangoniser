package com.coldani3.dangoniser.components

import android.app.AlertDialog
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.coldani3.dangoniser.MainActivity
import com.coldani3.dangoniser.R
import com.coldani3.dangoniser.databinding.TodoListItemBinding


class TodoListItemView : RelativeLayout {
    private var onChecked: ((View, Boolean) -> Unit)? = null;
    private var onEdit: ((View) -> Unit)? = null;
    private var onDelete: ((View) -> Unit)? = null;
    private lateinit var binding: TodoListItemBinding;

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.todo_list_item, this);
        binding = TodoListItemBinding.inflate(LayoutInflater.from(context), this);//.parent as ViewGroup);
        binding.checkbox.setOnClickListener { view -> callChecked(view) };
        binding.deleteButton.setOnClickListener { view -> callDelete(view) };
        binding.editButton.setOnClickListener { view -> callEdit(view) };

        val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.TodoListItemView, 0, 0);
        binding.checkbox.text = attributes.getString(R.styleable.TodoListItemView_checkboxText);
        binding.checkbox.isChecked = attributes.getBoolean(R.styleable.TodoListItemView_checked, false);
        attributes.recycle();

    }

    public fun onChecked(unit: ((View, Boolean) -> Unit)?) {
        if (unit != null) {
            onChecked = unit;
        }
    }

    public fun onEditPress(unit: ((View) -> Unit)?) {
        if (unit != null) {
            onEdit = unit;
        }
    }

    public fun onDeletePress(unit: ((View) -> Unit)?) {
        if (unit != null) {
            onDelete = unit;
        }
    }

    public fun setChecked(checked: Boolean) {
        binding.checkbox.isChecked = checked;
    }

    public fun setName(name: String) {
        binding.checkboxText.text = name;
    }

    private fun deleteSelf(view: View) {

    }

    private fun callChecked(view: View) {
        if (onChecked != null) {
            onChecked!!(view, binding.checkbox.isChecked);
        }
    }

    private fun callEdit(view: View) {
        if (onEdit != null) {
            Log.d(MainActivity.DEBUG_LOG_NAME, "edit");
            onEdit!!(view);
        } else {
            Log.d(MainActivity.DEBUG_LOG_NAME, "edit null");
        }
    }

    private fun callDelete(view: View) {
        if (onDelete != null) {
            onDelete!!(view);
        }

        AlertDialog.Builder(context)
            .setTitle("Delete To-do")
            .setMessage("Are you sure you want to delete this to-do?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.ok) {
                    dialogInterface, whichButton -> deleteSelf(view);
            }.setNegativeButton(android.R.string.cancel, null)
            .show();
    }
}