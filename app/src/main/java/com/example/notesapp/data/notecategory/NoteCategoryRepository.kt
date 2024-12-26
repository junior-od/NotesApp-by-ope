package com.example.notesapp.data.notecategory

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
}