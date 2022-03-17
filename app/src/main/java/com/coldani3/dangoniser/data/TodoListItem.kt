package com.coldani3.dangoniser.data

class TodoListItem {
    public var checked: Boolean;
    public var title: String;

    constructor(title: String, checked: Boolean = false) {
        this.title = title;
        this.checked = checked;
    }

    public fun setCheckedTo(newChecked: Boolean) {
        this.checked = newChecked;
    }

    public fun toggleChecked() {
        setCheckedTo(!this.checked);
    }
}