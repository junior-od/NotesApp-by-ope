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
        val updatedTodos = noteTodos.map {
            it.copy(
                noteId = it.noteId?.ifEmpty { noteId },
                createdBy = it.createdBy?.ifEmpty { userRepo.getSignedInUserId() },
                createdAt = it.createdAt?.ifEmpty { DateTimeUtils.getCurrentDateTimeInFullDateTimeFormat() },
                updatedAt = DateTimeUtils.getCurrentDateTimeInFullDateTimeFormat(),
                syncFlag = 0
            )
        }

        noteTodoRepo.insertNoteTodos(updatedTodos)
    }
}