package com.coldani3.dangoniser.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.coldani3.dangoniser.R
import com.coldani3.dangoniser.databinding.ItemListBinding


abstract class AbstractListItemView<T, U> : LinearLayout {
    protected var binding: ItemListBinding;
    protected var items: MutableList<T> = mutableListOf();
    protected var views: MutableList<U> = mutableListOf();

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.item_list, this);
        binding = ItemListBinding.inflate(LayoutInflater.from(context), this);
    }

    public abstract fun addItem(event: T);

    public fun clearItems() {
        binding.eventsList.removeAllViews();
    }

    public fun refreshItems() {
        clearItems();

        for (item in this.items) {
            addItem(item);
        }
    }

    public fun getViewItem(event: T) : U {
        return views[items.indexOf(event)];
    }
}