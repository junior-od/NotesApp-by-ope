package com.example.notesapp.domain.todos

import com.example.notesapp.data.todos.NoteTodo
import com.example.notesapp.data.todos.NoteTodoRepo
import com.example.notesapp.data.user.UserRepo
import com.example.notesapp.utils.DateTimeUtils

/**
 * Edit Note To-dos use case
 * */
class EditNoteTodosUseCase(
    private val noteTodoRepo: NoteTodoRepo,
    private val userRepo: UserRepo
) {

    /**
     * @param noteTodos expects list of to-dos
     * @param noteId expects note id the to-do is under
     * */
    suspend operator fun invoke(
        noteTodos: List<NoteTodo>,
        noteId: String,
    ){
        if (noteTodos.isNotEmpty()){
            val updatedTodos = noteTodos.map {
                it.copy(
                    noteId = if(it.noteId == null) noteId else it.noteId,
                    createdBy = if(it.createdBy == null) userRepo.getSignedInUserId() else it.createdBy,
                    createdAt = if(it.createdAt == null) DateTimeUtils.getCurrentDateTimeInFullDateTimeFormat() else it.createdAt,
                    updatedAt = DateTimeUtils.getCurrentDateTimeInFullDateTimeFormat(),
                    syncFlag = 0
                )
            }

            noteTodoRepo.insertNoteTodos(updatedTodos)
        }
    }
}