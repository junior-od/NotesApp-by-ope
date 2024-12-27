package com.example.notesapp.domain.todos

import com.example.notesapp.data.todos.NoteTodo
import com.example.notesapp.data.todos.NoteTodoRepo
import com.example.notesapp.utils.DateTimeUtils

/**
 * Update a Note To-do completed status use case
 * */
class UpdateNoteTodoUseCase(
    private val noteTodoRepo: NoteTodoRepo
) {

    /**
     * @param noteTodo expects a note to-do record
     * */
    suspend operator fun invoke(
        noteTodo: NoteTodo,
    ){
        val updatedTodo = noteTodo.copy(
            todoCompleted = if (noteTodo.todoCompleted == 0) 1 else 0,
            updatedAt = DateTimeUtils.getCurrentDateTimeInFullDateTimeFormat(),
            syncFlag = 0
        )
        noteTodoRepo.insertNoteTodo(updatedTodo)
    }

}