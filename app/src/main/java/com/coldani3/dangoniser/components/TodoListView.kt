package com.coldani3.dangoniser.components

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import com.coldani3.dangoniser.MainActivity
import com.coldani3.dangoniser.data.TodoData

class TodoListView : AbstractListView<TodoData, TodoListItemView> {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    public override fun addItem(event: TodoData) {
        addItem(event, null, null, null);
    }

    public fun addItem(todoData: TodoData, checkPressed: ((View, Boolean) -> Unit)?, editPressPressed: ((View) -> Unit)?, deleteButtonPressed: ((View) -> Unit)?) {
        val item: TodoListItemView = TodoListItemView(context);
        Log.d(MainActivity.DEBUG_LOG_NAME, "Todo addItem");

        item.layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        item.setTodo(todoData);
        item.setChecked(todoData.checked);
        item.setName(todoData.title);
        item.onChecked(checkPressed);
        item.onEditPress(editPressPressed);
        item.onDeletePress(deleteButtonPressed);

        binding.eventsList.addView(item);
        item.invalidate();
        items.add(todoData);
    }
}