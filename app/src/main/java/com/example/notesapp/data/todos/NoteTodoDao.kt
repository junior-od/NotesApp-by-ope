package com.example.notesapp.data.todos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notesapp.data.notecategory.NoteCategory
import com.example.notesapp.data.notecategory.NoteCategoryTable


/**
 * db operations on note todo table
 * */

@Dao
interface NoteTodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteTodo(noteTodo: NoteTodo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteTodos(noteTodo: List<NoteTodo>)

    /***
     * get todos by note id
     *
     * @param noteId expects note id
     */
    @Query(
        "SELECT * FROM ${NoteTodoTable.TABLE_NAME} " +
        "WHERE ${NoteTodoTable.NOTE_ID} = :noteId"
    )
    suspend fun getTodosByNoteId(noteId: String?): List<NoteTodo>

    /**
     * get all note todos to be uploaded
     * */
    @Query(
        "SELECT * FROM ${NoteTodoTable.TABLE_NAME} WHERE ${NoteTodoTable.SYNC_FLAG} = 0"
    )
    suspend fun getAllNoteTodoToUpload(): List<NoteTodo>
}