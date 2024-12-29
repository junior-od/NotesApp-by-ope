package com.example.notesapp.data.notecategory

import kotlinx.coroutines.flow.Flow

interface NoteCategoryRepo {
    suspend fun insertNoteCategory(noteCategory: NoteCategory)

    fun getAllNoteCategoriesByUserId(userId: String): Flow<List<NoteCategory>>

    suspend fun getAllNoteCategoryToUpload(): List<NoteCategory>
}