package com.example.notesapp.data.note

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.notesapp.data.todos.NoteTodo
import com.example.notesapp.data.todos.NoteTodoTable

/**
 * Note Entity or table
 *
 * @param id Unique identifier of note record
 * @param noteCategoryId id to group the note record
 * @param noteTitle title of note
 * @param noteInfo body of the note
 * @param deleteFlag determine if record is deleted or not
 * @param createdBy  user id of the note creator
 * @param createdAt time note record was created
 * @param updatedAt time note record was updated
 * */

@Entity(tableName = NoteTable.TABLE_NAME)
data class Note(
    @ColumnInfo(name = NoteTable.ID)
    @PrimaryKey
    var id: String,

    @ColumnInfo(name = NoteTable.NOTE_CATEGORY_ID)
    var noteCategoryId: String? = null,

    @ColumnInfo(name = NoteTable.NOTE_TITLE)
    var noteTitle: String? = null,

    @ColumnInfo(name = NoteTable.NOTE_INFO)
    var noteInfo: String? = null,

    @ColumnInfo(name = NoteTable.DELETE_FLAG)
    var deleteFlag: Int = 0,

    @ColumnInfo(name = NoteTable.CREATED_BY)
    var createdBy: String? = null,

    @ColumnInfo(name = NoteTable.CREATED_AT)
    var createdAt: String? = null,

    @ColumnInfo(name = NoteTable.UPDATED_AT)
    var updatedAt: String? = null,

    @ColumnInfo(name = NoteTable.SYNC_FLAG)
    var syncFlag: Int = 0,
)


object NoteTable {
    const val TABLE_NAME = "notes"

    // columns
    const val ID = "id"

    const val NOTE_CATEGORY_ID = "note_category_id"
    const val NOTE_TITLE = "note_title"
    const val NOTE_INFO = "note_info"
    const val DELETE_FLAG = "delete_flag"
    const val CREATED_BY = "created_by"
    const val CREATED_AT = "created_at"
    const val UPDATED_AT = "updated_at"
    const val SYNC_FLAG = "sync_flag"
}

data class NoteWithTodosModel(
    @Embedded val note: Note,
    @Relation(
        parentColumn = NoteTable.ID,
        entityColumn = NoteTodoTable.NOTE_ID,
        entity = NoteTodo::class
    )
    val todos: List<NoteTodo>
)
