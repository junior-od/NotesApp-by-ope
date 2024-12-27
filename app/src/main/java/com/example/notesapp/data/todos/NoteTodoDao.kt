package com.example.notesapp.data.todos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy


/**
 * db operations on note todo table
 * */

@Dao
interface NoteTodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteTodo(noteTodo: NoteTodo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteTodos(noteTodo: List<NoteTodo>)
}