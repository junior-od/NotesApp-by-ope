package com.example.notesapp.data.note

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * db operations on note table
 * */

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: List<Note>)

    /**
     * get note by id
     *
     * @param id of the note
     * */
    @Query(
        "SELECT * FROM ${NoteTable.TABLE_NAME} WHERE ${NoteTable.ID} = :id"
    )
    suspend fun getNoteById(id: String?): Note?

    /**
     * get notes with todos
     *
     * @param userId user id that created the notes
     * */
    @Transaction // Ensures the query is executed as a single transaction
    @Query(
        "SELECT * FROM ${NoteTable.TABLE_NAME} WHERE " +
        "${NoteTable.DELETE_FLAG} = 0 AND " +
        "${NoteTable.CREATED_BY} = :userId"
    )
    fun getNotesWithTodosByUserId(
        userId: String?
    ): Flow<List<NoteWithTodosModel>>
}