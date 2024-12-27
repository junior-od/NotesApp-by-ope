package com.example.notesapp.domain.todos

import com.example.notesapp.data.todos.NoteTodo
import com.example.notesapp.data.todos.NoteTodoRepo

/**
 * GetTodos By NoteId
 * */
class GetTodosByNoteIdUseCase(
    private val noteTodoRepo: NoteTodoRepo
) {

    /**
     * get all GetTodos By NoteId
     * */
    suspend operator fun invoke(
        noteId: String?
    ): List<NoteTodo> {
        return noteTodoRepo
            .getTodosByNoteId(
                noteId
            )
    }
}