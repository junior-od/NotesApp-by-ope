package com.example.notesapp.data.todos

interface NoteTodoRepo {
    suspend fun insertNoteTodo(noteTodo: NoteTodo)
    suspend fun insertNoteTodos(noteTodos: List<NoteTodo>)
    suspend fun getTodosByNoteId(noteId: String?): List<NoteTodo>
    suspend fun getAllNoteTodoToUpload(): List<NoteTodo>
}