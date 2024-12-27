package com.example.notesapp.domain.note

import com.example.notesapp.data.note.Note
import com.example.notesapp.data.note.NoteRepo

/**
 * Get note use case
 * */
class GetNoteUseCase(
    private val noteRepo: NoteRepo
) {

    /**
     * @param id expects note id record
     * */
    suspend operator fun invoke(
        id: String?
    ): Note?{
        return noteRepo.getNoteById(id)
    }
}