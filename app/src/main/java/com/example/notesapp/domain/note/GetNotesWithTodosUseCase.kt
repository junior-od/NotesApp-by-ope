package com.example.notesapp.domain.note

import com.example.notesapp.data.note.NoteRepo
import com.example.notesapp.data.note.NoteWithTodosModel
import com.example.notesapp.data.user.UserRepo
import kotlinx.coroutines.flow.Flow

/**
 * GetNotesWithTodos UseCase with filters
 * */
class GetNotesWithTodosUseCase(
    private val noteRepo: NoteRepo,
    private val userRepo: UserRepo
) {

    /**
     * @param search expects search data
     * @param categoryId expects note category id
     */

    operator fun invoke(
        search: String = "",
        categoryId: String = "1"
    ): Flow<List<NoteWithTodosModel>> {

        return if(categoryId == "1"){
            noteRepo.getNotesWithTodosByUserId(
                userId = userRepo.getSignedInUserId(),
                search = "%$search%"
            )
        } else {
            noteRepo.getNotesWithTodosByUserIdAndCategoryId(
                userId = userRepo.getSignedInUserId(),
                search = "%$search%",
                categoryId = categoryId
            )
        }
    }
}