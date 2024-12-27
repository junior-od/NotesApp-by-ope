package com.example.notesapp.data.todos

interface NoteTodoRepo {
    suspend fun insertNoteTodo(noteTodo: NoteTodo)
    suspend fun insertNoteTodos(noteTodos: List<NoteTodo>)
}