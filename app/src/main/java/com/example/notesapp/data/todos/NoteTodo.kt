package com.example.notesapp.data.todos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Note To-do Entity or table
 *
 * @param id Unique identifier of note to-do record
 * @param noteId id of note record the to-do is under
 * @param to_do the to-do info
 * @param todoCompleted determine if to-do is completed or not
 * @param deleteFlag determine if record is deleted or not
 * @param createdBy  user id of the note to-do creator
 * @param createdAt time note to-do record was created
 * @param updatedAt time note to-do record was updated
 * */

@Entity(tableName = NoteTodoTable.TABLE_NAME)
data class NoteTodo(
    @ColumnInfo(name = NoteTodoTable.ID)
    @PrimaryKey
    var id: String,

    @ColumnInfo(name = NoteTodoTable.NOTE_ID)
    var noteId: String? = null,

    @ColumnInfo(name = NoteTodoTable.TODO)
    var todo: String? = null,

    @ColumnInfo(name = NoteTodoTable.TODO_COMPLETED)
    var todoCompleted: Int = 0,

    @ColumnInfo(name = NoteTodoTable.DELETE_FLAG)
    var deleteFlag: Int = 0,

    @ColumnInfo(name = NoteTodoTable.CREATED_BY)
    var createdBy: String? = null,

    @ColumnInfo(name = NoteTodoTable.CREATED_AT)
    var createdAt: String? = null,

    @ColumnInfo(name = NoteTodoTable.UPDATED_AT)
    var updatedAt: String? = null,

    @ColumnInfo(name = NoteTodoTable.SYNC_FLAG)
    var syncFlag: Int = 0,
)


object NoteTodoTable {
    const val TABLE_NAME = "notes_todo"

    // columns
    const val ID = "id"

    const val NOTE_ID = "note_id"
    const val TODO = "todo"
    const val TODO_COMPLETED = "todo_completed"
    const val DELETE_FLAG = "delete_flag"
    const val CREATED_BY = "created_by"
    const val CREATED_AT = "created_at"
    const val UPDATED_AT = "updated_at"
    const val SYNC_FLAG = "sync_flag"
}
