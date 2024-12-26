package com.example.notesapp.data.notecategory

interface NoteCategoryRepo {
    suspend fun insertNoteCategory(noteCategory: NoteCategory)
}