package com.example.notesapp.data.notecategory

import kotlinx.coroutines.flow.Flow

/**
 * note category repository to communicate with remote
 * server and local db to fetch
 * and save note category data
 * */
class NoteCategoryRepository(
    private val noteCategoryDao: NoteCategoryDao
): NoteCategoryRepo {

    /**
     * insert note category record
     *
     * @param noteCategory expects note category record to be inserted
     * */
    override suspend fun insertNoteCategory(noteCategory: NoteCategory) {
        return noteCategoryDao.insertNoteCategory(noteCategory)
    }

    /**
     * get all categories
     *
     * @param userId expects user id that created the category
     * */
    override fun getAllNoteCategoriesByUserId(userId: String): Flow<List<NoteCategory>> {
        return noteCategoryDao.getAllNoteCategoriesByUserId(userId)
    }

    /**
     * get all note category to be uploaded
     * */
    override suspend fun getAllNoteCategoryToUpload(): List<NoteCategory> {
        return noteCategoryDao.getAllNoteCategoryToUpload()
    }
}