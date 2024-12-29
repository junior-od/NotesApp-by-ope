package com.example.notesapp.data.todos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.notesapp.data.db.NotesDatabase
import com.example.notesapp.data.note.NoteDao
import com.example.notesapp.data.notecategory.NoteCategory
import com.example.notesapp.data.notecategory.NoteCategoryDao
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
class NoteTodoDaoTest{

    //rule basically to allow tasks(coroutine tasks) to be run synchronously
    // and immediately on same thread, to observe and get immediate results
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var notesDatabase: NotesDatabase
    private lateinit var noteTodoDao: NoteTodoDao

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

        noteTodoDao.insertNoteTodo(note)

        val getNote = noteTodoDao.getAllNoteTodoToUpload()

        Truth.assertThat(note).isEqualTo(getNote[0])
    }

}