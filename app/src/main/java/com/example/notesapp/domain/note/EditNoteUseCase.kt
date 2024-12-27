package com.example.notesapp.domain.note

import com.example.notesapp.data.note.Note
import com.example.notesapp.data.note.NoteRepo
import com.example.notesapp.utils.DateTimeUtils

/**
 * Edit Note use case
 * */

class EditNoteUseCase(
    private val noteRepo: NoteRepo
) {

    /**
     * @param note expects note record
     * */
    suspend operator fun invoke(
        note: Note?
    ){
        note?.let {
            val updateNote = note.copy(
                updatedAt = DateTimeUtils.getCurrentDateTimeInFullDateTimeFormat(),
                syncFlag = 0
            )

            noteRepo.insertNote(updateNote)
        }
    }
}