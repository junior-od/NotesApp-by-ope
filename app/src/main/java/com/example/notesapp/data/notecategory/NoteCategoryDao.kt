package com.example.notesapp.data.notecategory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy


/**
 * db operations on note category table
 * */

@Dao
interface NoteCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteCategory(noteCategory: NoteCategory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteCategories(noteCategory: List<NoteCategory>)

}