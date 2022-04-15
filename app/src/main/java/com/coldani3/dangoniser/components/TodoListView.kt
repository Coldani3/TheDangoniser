package com.coldani3.dangoniser.components

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.coldani3.dangoniser.data.TodoData

class TodoListView : AbstractListItemView<TodoData, TodoListItemView> {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    public override fun addItem(event: TodoData) {
        val item: TodoListItemView = TodoListItemView(context);

        item.layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        item.setChecked(event.checked);
        item.setName(event.title);

        binding.eventsList.addView(item);
        item.invalidate();
        items.add(event);
    }
}