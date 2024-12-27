package com.example.notesapp.domain.note

import com.example.notesapp.data.note.Note
import com.example.notesapp.data.note.NoteRepo
import com.example.notesapp.data.user.UserRepo
import com.example.notesapp.utils.DateTimeUtils
import com.example.notesapp.utils.UniqueIdGeneratorUtils


/**
 * Create Note use case
 * */

class CreateNoteUseCase(
    private val noteRepo: NoteRepo,
    private val userRepo: UserRepo
) {

    /**
     * @param note expects note record
     * */
    suspend operator fun invoke(
        note: Note
    ){

        val updateNote = note.copy(
            createdBy = userRepo.getSignedInUserId(),
            createdAt = DateTimeUtils.getCurrentDateTimeInFullDateTimeFormat(),
            updatedAt = DateTimeUtils.getCurrentDateTimeInFullDateTimeFormat()
        )

        noteRepo.insertNote(updateNote)

    }

}