package com.example.notesapp.domain.note

import com.example.notesapp.data.note.NoteRepo
import com.example.notesapp.data.note.NoteWithTodosModel
import com.example.notesapp.data.user.UserRepo
import kotlinx.coroutines.flow.Flow

/**
 * GetNotesWithTodos UseCase
 * */
class GetNotesWithTodosUseCase(
    private val noteRepo: NoteRepo,
    private val userRepo: UserRepo
) {

    operator fun invoke(): Flow<List<NoteWithTodosModel>> {
        return noteRepo.getNotesWithTodosByUserId(
            userId = userRepo.getSignedInUserId()
        )
    }
}