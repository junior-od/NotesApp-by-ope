package com.example.notesapp.data.note

import kotlinx.coroutines.flow.Flow

/**
 * note repository to communicate with remote
 * server and local db to fetch
 * and save note data
 * */
class NoteRepository(
    private val noteDao: NoteDao
): NoteRepo{


    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override suspend fun getNoteById(id: String?): Note? {
        return noteDao.getNoteById(id)
    }

    /**
     * get notes with todos
     *
     * @param userId user id that created the notes
     * @param search expects search data
     * */
    override fun getNotesWithTodosByUserId(
        userId: String?,
        search: String
    ): Flow<List<NoteWithTodosModel>> {
        return noteDao.getNotesWithTodosByUserId(
            userId = userId,
            search = search
        )
    }

    /**
     * get notes with todos by user id and category id
     *
     * @param userId user id that created the notes
     * @param search expects search data
     * @param categoryId expects note category id
     * */
    override fun getNotesWithTodosByUserIdAndCategoryId(
        userId: String?,
        search: String,
        categoryId: String
    ): Flow<List<NoteWithTodosModel>> {
        return noteDao.getNotesWithTodosByUserIdAndCategoryId(
            userId = userId,
            search = search,
            categoryId = categoryId
        )
    }

    /**
     * get all notes to be uploaded
     * */
    override suspend fun getAllNotesToUpload(): List<Note> {
        return noteDao.getAllNotesToUpload()
    }
}