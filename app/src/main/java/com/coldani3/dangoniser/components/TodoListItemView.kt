package com.coldani3.dangoniser.components

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.coldani3.dangoniser.R
import com.coldani3.dangoniser.databinding.TodoListItemBinding


class TodoListItemView : RelativeLayout {
    private var checkedUnits: MutableList<(View, Boolean) -> Unit> = mutableListOf();
    private var onEditUnits: MutableList<(View) -> Unit> = mutableListOf();
    private var onDeleteUnits: MutableList<(View) -> Unit> = mutableListOf();
    private lateinit var binding: TodoListItemBinding;

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.todo_list_item, this);
        binding = TodoListItemBinding.inflate(LayoutInflater.from(context), this.parent as ViewGroup);
        binding.checkbox.setOnClickListener { View.OnClickListener { view -> callChecked(view) } };
        binding.deleteButton.setOnClickListener { View.OnClickListener { view -> callDelete(view) }};
        binding.editButton.setOnClickListener { View.OnClickListener { view -> callEdit(view) }};
    }

    public fun onChecked(unit: (View, Boolean) -> Unit) {
        checkedUnits.add(unit);
    }

    public fun onEditPress(unit: (View) -> Unit) {
        onEditUnits.add(unit);
    }

    public fun onDeletePress(unit: (View) -> Unit) {
        onDeleteUnits.add(unit);
    }

    private fun callChecked(view: View) {
        for (unit: (View, Boolean) -> Unit in checkedUnits) {
            unit.invoke(view, binding.checkbox.isChecked);
        }
    }

    private fun callEdit(view: View) {
        for (unit: (View) -> Unit in onEditUnits) {
            unit.invoke(view);
        }
    }

    private fun callDelete(view: View) {
        for (unit: (View) -> Unit in onDeleteUnits) {
            unit.invoke(view);
        }
    }
}