package com.example.notesapp.domain.notecategory

import com.example.notesapp.data.notecategory.NoteCategory
import com.example.notesapp.data.notecategory.NoteCategoryRepo
import com.example.notesapp.data.user.UserRepo
import com.example.notesapp.utils.DateTimeUtils
import com.example.notesapp.utils.UniqueIdGeneratorUtils

/**
 * Create Note Category use case
 * */
class CreateNoteCategoryUseCase(
    private val noteCategoryRepo: NoteCategoryRepo,
    private val userRepo: UserRepo
) {

    /**
     * @param noteCategory expects note category record
     * */
    suspend operator fun invoke(
        noteCategory: NoteCategory
    ){

        val updateNoteCategory = noteCategory.copy(
            id = UniqueIdGeneratorUtils.uniqueIdGenerator("NC", userRepo.getSignedInUserId()),
            createdBy = userRepo.getSignedInUserId(),
            createdAt = DateTimeUtils.getCurrentDateTimeInFullDateTimeFormat(),
            updatedAt = DateTimeUtils.getCurrentDateTimeInFullDateTimeFormat()
        )

        noteCategoryRepo.insertNoteCategory(updateNoteCategory)
    }

}