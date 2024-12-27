package com.example.notesapp.domain.notecategory

import com.example.notesapp.data.notecategory.NoteCategory
import com.example.notesapp.data.notecategory.NoteCategoryRepo
import com.example.notesapp.data.user.UserRepo
import kotlinx.coroutines.flow.Flow

/**
 * GetAllNoteCategory UseCase
 * */
class GetAllNoteCategoryUseCase(
    private val noteCategoryRepo: NoteCategoryRepo,
    private val userRepo: UserRepo
) {

    /**
     * get all categories
     * */
    operator fun invoke(
    ): Flow<List<NoteCategory>> {
        return noteCategoryRepo
            .getAllNoteCategoriesByUserId(
            userId = userRepo.getSignedInUserId()
        )
    }
}