package com.example.notesapp.data.note

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.notesapp.data.db.NotesDatabase
import com.example.notesapp.getOrAwaitValue
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
class NoteRepositoryTest{
    //rule basically to allow tasks(coroutine tasks) to be run synchronously
    // and immediately on same thread, to observe and get immediate results
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var notesDatabase: NotesDatabase
    private lateinit var noteDao: NoteDao
    private lateinit var noteRepo: NoteRepo

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

        noteDao = notesDatabase.noteDao()
        noteRepo = NoteRepository(noteDao)
    }

    @After
    fun teardown() {
        notesDatabase.close()
    }

    /**
     * test that a note is inserted in the db
     * */
    @Test
    fun testInsertNote_Returns_True() = runTest{
        val note = Note(
            id = "id",
            createdBy = "user",
            syncFlag = 0
        )

        noteRepo.insertNote(note)

        val getNote = noteRepo.getAllNotesToUpload()

        Truth.assertThat(note).isEqualTo(getNote[0])
    }

    /**
     * test that get note by id in the db retuns null if it doesn't exist
     * */
    @Test
    fun testGetNoteById_Returns_Null() = runTest{

        val getNote = noteRepo.getNoteById("ids")

        Truth.assertThat(getNote).isNull()
    }

    /**
     * test that get note by id in the db returns note that exists
     * */
    @Test
    fun testGetNoteById_Returns_Note() = runTest{
        val note = Note(
            id = "id",
            createdBy = "user",
            syncFlag = 0
        )

        noteRepo.insertNote(note)

        val getNote = noteRepo.getNoteById("id")

        Truth.assertThat(getNote).isEqualTo(note)
    }

    /**
     * test get Notes With Todos By UserId returns only notes created by user id
     * */
    @Test
    fun testGetNotesWithTodosByUserId_Returns_NotesCreatedByUserIdOnly() = runTest{
        val note = listOf(
            Note(
                id = "id",
                noteTitle = "ew",
                createdBy = "user",
                deleteFlag = 0,
                syncFlag = 0
            ),
            Note(
                id = "id1",
                noteTitle = "ewew",
                createdBy = "user",
                syncFlag = 0
            ),
            Note(
                id = "id12",
                noteTitle = "eeweww",
                createdBy = "user2",
                syncFlag = 0
            ),
        )

        // filter for expected result
        val expectedResult = note.filter { it.createdBy.equals("user") }

        note.forEach {
            noteRepo.insertNote(it)
        }

        val getNote = noteRepo.getNotesWithTodosByUserId("user", "%%").asLiveData().getOrAwaitValue()

        Truth.assertThat(getNote.size).isEqualTo(expectedResult.size)
    }

    /**
     * test get Notes With Todos By UserId and categoryId returns only notes created by user id
     * */
    @Test
    fun testGetNotesWithTodosByUserIdAndCategoryId_Returns_NotesCreatedByUserIdOnly() = runTest{
        val note = listOf(
            Note(
                id = "id",
                noteTitle = "ew",
                createdBy = "user",
                noteCategoryId = "1",
                deleteFlag = 0,
                syncFlag = 0
            ),
            Note(
                id = "id1",
                noteTitle = "ewew",
                createdBy = "user",
                syncFlag = 0
            ),
            Note(
                id = "id12",
                noteTitle = "eeweww",
                createdBy = "user2",
                syncFlag = 0
            ),
        )

        // filter for expected result
        val expectedResult = note.filter { it.createdBy.equals("user") && it.noteCategoryId.equals("1") }

        note.forEach {
            noteRepo.insertNote(it)
        }

        val getNote = noteRepo.getNotesWithTodosByUserIdAndCategoryId("user", "%%", "1").asLiveData().getOrAwaitValue()

        Truth.assertThat(getNote.size).isEqualTo(expectedResult.size)
    }

    /**
     * test that get all notes to upload returns
     * data when record with sync flag 0 exists in the db
     * */
    @Test
    fun testGetAllNotesToUpload_Returns_DataToUpload() = runTest{
        val note = listOf(
            Note(
                id = "id",
                createdBy = "user",
                syncFlag = 1,
            ),
            Note(
                id = "id3",
                createdBy = "user",
                syncFlag = 0,
            ),
            Note(
                id = "id43",
                createdBy = "user",
                syncFlag = 1,
            )
        )

        // filter for expected result
        val expectedResult = note.filter {  it.syncFlag == 0 }

        note.forEach {
            noteRepo.insertNote(it)
        }


        val getNote = noteRepo.getAllNotesToUpload()

        Truth.assertThat(getNote).isEqualTo(expectedResult)
    }

}