package com.example.notesapp.data.notecategory

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
class NoteCategoryRepositoryTest{

    //rule basically to allow tasks(coroutine tasks) to be run synchronously
    // and immediately on same thread, to observe and get immediate results
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var notesDatabase: NotesDatabase
    private lateinit var noteCategoryDao: NoteCategoryDao
    private lateinit var noteCategoryRepo: NoteCategoryRepo

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

        noteCategoryDao = notesDatabase.noteCategoryDao()
        noteCategoryRepo = NoteCategoryRepository(noteCategoryDao)
    }

    @After
    fun teardown() {
        notesDatabase.close()
    }

    /**
     * test that a note category is inserted in the db
     * */
    @Test
    fun testInsertNoteCategory_Returns_True() = runTest{
        val note = NoteCategory(
            id = "id",
            createdBy = "user",
            syncFlag = 0
        )

        noteCategoryRepo.insertNoteCategory(note)

        val getNote = noteCategoryRepo.getAllNoteCategoryToUpload()

        Truth.assertThat(note).isEqualTo(getNote[0])
    }


    /**
     * test that GetAllNoteCategoriesByUserId returns empty if user has no category created
     * */
    @Test
    fun testGetAllNoteCategoriesByUserId_Returns_empty() = runTest{
        val note = listOf(
            NoteCategory(
                id = "id",
                createdBy = "user3",
                syncFlag = 0
            ),
            NoteCategory(
                id = "id4",
                createdBy = "user3",
                syncFlag = 0
            )
        )

        note.forEach {
            noteCategoryRepo.insertNoteCategory(it)
        }


        val getNote = noteCategoryRepo.getAllNoteCategoriesByUserId("user").asLiveData().getOrAwaitValue()

        Truth.assertThat(getNote).isEmpty()
    }

    /**
     * test that GetAllNoteCategoriesByUserId returns data if user has category created
     * */
    @Test
    fun testGetAllNoteCategoriesByUserId_Returns_datacreatedbyuser() = runTest{
        val note = listOf(
            NoteCategory(
                id = "id",
                createdBy = "user",
                syncFlag = 0
            ),
            NoteCategory(
                id = "id4",
                createdBy = "user3",
                syncFlag = 0
            )
        )

        note.forEach {
            noteCategoryRepo.insertNoteCategory(it)
        }

        val getNote = noteCategoryRepo.getAllNoteCategoriesByUserId("user").asLiveData().getOrAwaitValue()

        Truth.assertThat(getNote).isNotEmpty()
    }

    /**
     * test that get all note category to upload returns
     * data when record with sync flag 0 exists in the db
     * */
    @Test
    fun testGetAllNotesToUpload_Returns_DataToUpload() = runTest{
        val note = listOf(
            NoteCategory(
                id = "id",
                createdBy = "user",
                syncFlag = 1,
            ),
            NoteCategory(
                id = "id3",
                createdBy = "user",
                syncFlag = 0,
            ),
            NoteCategory(
                id = "id43",
                createdBy = "user",
                syncFlag = 1,
            )
        )

        // filter for expected result
        val expectedResult = note.filter {  it.syncFlag == 0 }

        note.forEach {
            noteCategoryRepo.insertNoteCategory(it)
        }

        val getNote = noteCategoryRepo.getAllNoteCategoryToUpload()

        Truth.assertThat(getNote).isEqualTo(expectedResult)
    }

}