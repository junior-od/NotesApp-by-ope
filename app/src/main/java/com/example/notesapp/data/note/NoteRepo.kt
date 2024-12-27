package com.example.notesapp.data.note

interface NoteRepo {
    suspend fun insertNote(note: Note)

    suspend fun getNoteById(id: String?): Note?
}