package com.example.notesapp.data.note

import kotlinx.coroutines.flow.Flow

interface NoteRepo {
    suspend fun insertNote(note: Note)

    suspend fun getNoteById(id: String?): Note?

    fun getNotesWithTodosByUserId(
        userId: String?
    ): Flow<List<NoteWithTodosModel>>
}