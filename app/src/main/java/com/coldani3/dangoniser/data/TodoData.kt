package com.coldani3.dangoniser.data

import com.coldani3.dangoniser.Util
import com.coldani3.dangoniser.data.bases.DBTodoListItem
import java.util.*

class TodoData {
    public var checked: Boolean;
    public var title: String;
    public var forDate: Calendar;

    constructor(title: String, checked: Boolean = false, forDate: Calendar = Calendar.getInstance()) {
        this.title = title;
        this.checked = checked;
        this.forDate = forDate;
    }

    public fun setCheckedTo(newChecked: Boolean) {
        this.checked = newChecked;
    }

    public fun toggleChecked() {
        setCheckedTo(!this.checked);
    }

    companion object {
        fun fromDBObject(item: DBTodoListItem): TodoData {
            return TodoData(item.name, item.checked, Util.millisToCalendar(item.forDate));
        }
    }
}