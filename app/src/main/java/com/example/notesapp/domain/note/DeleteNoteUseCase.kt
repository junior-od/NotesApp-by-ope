package com.example.notesapp.domain.note

import com.example.notesapp.data.note.Note
import com.example.notesapp.data.note.NoteRepo
import com.example.notesapp.utils.DateTimeUtils

/**
 * Delete Note use case
 * */
class DeleteNoteUseCase(
    private val noteRepo: NoteRepo
) {

    /**
     * @param note expects note record
     * */
    suspend operator fun invoke(
        note: Note
    ){
        val updateNote = note.copy(
            deleteFlag = 0,
            updatedAt = DateTimeUtils.getCurrentDateTimeInFullDateTimeFormat(),
            syncFlag = 0
        )

        noteRepo.insertNote(updateNote)
    }
}