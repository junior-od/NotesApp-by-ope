package com.example.notesapp.data.todos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.notesapp.data.db.NotesDatabase
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
class NoteTodoRepositoryTest{

    //rule basically to allow tasks(coroutine tasks) to be run synchronously
    // and immediately on same thread, to observe and get immediate results
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var notesDatabase: NotesDatabase
    private lateinit var noteTodoDao: NoteTodoDao
    private lateinit var noteTodoRepo: NoteTodoRepo

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        //allowMainThreadQueries() allow queries on main thread just for test
        //in memory creates a temporary db for when the app runs, when app is closed
        // it is cleared and destroyed
        notesDatabase = Room.inMemoryDatabaseBuilder(
            appContext,
            NotesDatabase::class.java
        ).allowMainThreadQueries().build()

        noteTodoDao = notesDatabase.noteTodoDao()
        noteTodoRepo = NoteTodoRepository(noteTodoDao)
    }

    @After
    fun teardown() {
        notesDatabase.close()
    }

    /**
     * test that a note to-do is inserted in the db
     * */
    @Test
    fun testInsertNoteTodo_Returns_True() = runTest{
        val note = NoteTodo(
            id = "id",
            createdBy = "user",
            syncFlag = 0
        )

        noteTodoRepo.insertNoteTodo(note)

        val getNote = noteTodoRepo.getAllNoteTodoToUpload()

        Truth.assertThat(note).isEqualTo(getNote[0])
    }

    /**
     * test that getTodosByNoteId returns empty if to-do with noteid doesn't exist in the db
     * */
    @Test
    fun testGetTodosByNoteId_Returns_empty() = runTest{
        val note = listOf(
            NoteTodo(
                id = "id",
                noteId = "note3",
                syncFlag = 0
            ),
            NoteTodo(
                id = "idewew",
                noteId = "note4",
                syncFlag = 0
            ),
        )

        noteTodoRepo.insertNoteTodos(note)

        val getNote = noteTodoRepo.getTodosByNoteId("note")

        Truth.assertThat(getNote).isEmpty()
    }

    /**
     * test that getTodosByNoteId returns empty if to-do with noteid does exist in the db
     * */
    @Test
    fun testGetTodosByNoteId_Returns_dataWithNoteId() = runTest{
        val note = listOf(
            NoteTodo(
                id = "id",
                noteId = "note",
                syncFlag = 0
            ),
            NoteTodo(
                id = "idwwe",
                noteId = "note4",
                syncFlag = 0
            ),
        )

        noteTodoRepo.insertNoteTodos(note)

        val getNote = noteTodoRepo.getTodosByNoteId("note")

        Truth.assertThat(getNote).isNotEmpty()
    }

    /**
     * test that get all note to-do to upload returns
     * data when record with sync flag 0 exists in the db
     * */
    @Test
    fun testGetAllNoteTodosToUpload_Returns_DataToUpload() = runTest{
        val note = listOf(
            NoteTodo(
                id = "id",
                noteId = "note",
                syncFlag = 0
            ),
            NoteTodo(
                id = "ired",
                noteId = "note4",
                syncFlag = 0
            ),
            NoteTodo(
                id = "idrre",
                noteId = "note4",
                syncFlag = 1
            ),
        )

        // filter for expected result
        val expectedResult = note.filter {  it.syncFlag == 0 }

        noteTodoRepo.insertNoteTodos(note)

        val getNote = noteTodoRepo.getAllNoteTodoToUpload()

        Truth.assertThat(getNote).isEqualTo(expectedResult)
    }

}