package com.example.notesapp.data.todos


/**
 * note to-do repository to communicate with remote
 * server and local db to fetch
 * and save note to-do data
 * */
class NoteTodoRepository(
    private val noteTodoDao: NoteTodoDao
): NoteTodoRepo {

    override suspend fun insertNoteTodo(noteTodo: NoteTodo) {
        noteTodoDao.insertNoteTodo(noteTodo)
    }

    override suspend fun insertNoteTodos(noteTodos: List<NoteTodo>) {
        noteTodoDao.insertNoteTodos(noteTodos)
    }

    /***
     * get todos by note id
     *
     * @param noteId expects note id
     */
    override suspend fun getTodosByNoteId(noteId: String?): List<NoteTodo> {
        return noteTodoDao.getTodosByNoteId(noteId)
    }
}