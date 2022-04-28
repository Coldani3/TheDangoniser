package com.coldani3.dangoniser.data.bases

import androidx.room.*

@Dao
interface TodoListDAO {
    //todolist
    @Query("SELECT * FROM dbtodolistitem;")
    fun getAllTodos(): List<DBTodoListItem>;
    @Query("SELECT * FROM dbtodolistitem WHERE forDate BETWEEN :dateMillis AND :dateMillis + 86400000")
    fun getTodosForDay(dateMillis: Long): List<DBTodoListItem>;
    @Query("SELECT * FROM dbtodolistitem WHERE uid = :uid")
    fun getTodoByUID(uid: Int): DBTodoListItem;

    @Update
    fun updateTodo(todo: DBTodoListItem);
    @Update
    fun updateTodos(vararg todos: DBTodoListItem);

    @Delete
    fun deleteTodo(todo: DBTodoListItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodos(vararg todos: DBTodoListItem);
    @Insert
    fun insertTodo(todo: DBTodoListItem);
}