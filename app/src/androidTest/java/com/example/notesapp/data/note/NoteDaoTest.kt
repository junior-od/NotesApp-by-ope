package com.example.notesapp.data.note

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.notesapp.data.db.NotesDatabase
import com.example.notesapp.getOrAwaitValue
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
class NoteDaoTest{

    //rule basically to allow tasks(coroutine tasks) to be run synchronously
    // and immediately on same thread, to observe and get immediate results
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var notesDatabase: NotesDatabase
    private lateinit var noteDao: NoteDao

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

        noteDao.insertNote(note)

        val getNote = noteDao.getAllNotesToUpload()

        Truth.assertThat(note).isEqualTo(getNote[0])
    }

}