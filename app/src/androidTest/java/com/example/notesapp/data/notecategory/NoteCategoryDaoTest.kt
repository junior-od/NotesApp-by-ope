package com.example.notesapp.data.notecategory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.notesapp.data.db.NotesDatabase
import com.example.notesapp.data.note.Note
import com.example.notesapp.data.note.NoteDao
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
class NoteCategoryDaoTest{
    //rule basically to allow tasks(coroutine tasks) to be run synchronously
    // and immediately on same thread, to observe and get immediate results
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var notesDatabase: NotesDatabase
    private lateinit var noteCategoryDao: NoteCategoryDao

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

        noteCategoryDao.insertNoteCategory(note)

        val getNote = noteCategoryDao.getAllNoteCategoryToUpload()

        Truth.assertThat(note).isEqualTo(getNote[0])
    }
}