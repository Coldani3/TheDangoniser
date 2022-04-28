package com.coldani3.dangoniser.data

import com.coldani3.dangoniser.Util
import com.coldani3.dangoniser.data.bases.DBTodoListItem
import java.io.Serializable
import java.util.*

class TodoData : Serializable{
    public var uid = 0;
    public var checked: Boolean;
    public var title: String;
    public var forDate: Calendar;

    constructor(title: String, checked: Boolean = false, forDate: Calendar = Calendar.getInstance()) {
        this.title = title;
        this.checked = checked;
        this.forDate = forDate;
    }

    constructor(data: DBTodoListItem) {
        this.title = data.name;
        this.checked = data.checked;
        this.forDate = Util.millisToCalendar(data.forDate);
        this.uid = data.uid;
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