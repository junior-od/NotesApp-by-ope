package com.example.notesapp.data.notecategory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


/**
 * db operations on note category table
 * */

@Dao
interface NoteCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteCategory(noteCategory: NoteCategory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteCategories(noteCategory: List<NoteCategory>)

    /**
     * get all categories
     *
     * @param userId expects user id that created the category
     * */
    @Query(
        "SELECT * FROM ${NoteCategoryTable.TABLE_NAME} " +
        "WHERE ${NoteCategoryTable.CREATED_BY} = :userId"
    )
    fun getAllNoteCategoriesByUserId(
        userId: String?
    ): Flow<List<NoteCategory>>

    /**
     * get all note category to be uploaded
     * */
    @Query(
        "SELECT * FROM ${NoteCategoryTable.TABLE_NAME} WHERE ${NoteCategoryTable.SYNC_FLAG} = 0"
    )
    suspend fun getAllNoteCategoryToUpload(): List<NoteCategory>

}