package com.example.notesapp.data.note

import kotlinx.coroutines.flow.Flow

interface NoteRepo {
    suspend fun insertNote(note: Note)

    suspend fun getNoteById(id: String?): Note?

    fun getNotesWithTodosByUserId(
        userId: String?,
        search: String
    ): Flow<List<NoteWithTodosModel>>

    fun getNotesWithTodosByUserIdAndCategoryId(
        userId: String?,
        search: String,
        categoryId: String
    ): Flow<List<NoteWithTodosModel>>

    suspend fun getAllNotesToUpload(): List<Note>
}