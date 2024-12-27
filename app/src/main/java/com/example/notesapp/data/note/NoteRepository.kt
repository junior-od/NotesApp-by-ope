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
     * */
    override fun getNotesWithTodosByUserId(userId: String?): Flow<List<NoteWithTodosModel>> {
        return noteDao.getNotesWithTodosByUserId(
            userId = userId
        )
    }
}