package com.example.notesapp.data.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.notesapp.data.db.NotesDatabase
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserDaoTest{
    //rule basically to allow tasks(coroutine tasks) to be run synchronously
    // and immediately on same thread, to observe and get immediate results
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var notesDatabase: NotesDatabase
    private lateinit var userDao: UserDao

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

       userDao = notesDatabase.userDao()
    }

    @After
    fun teardown() {
        notesDatabase.close()
    }

    /**
     * test that a user is inserted in the db
     * */
    @Test
    fun testInsertUser_Returns_True() = runTest{
        val note = User(
            id = "id",
            syncFlag = 0
        )

        userDao.insertUser(note)

        val getNote = userDao.getAllUsersDataToUpload()

        Truth.assertThat(note).isEqualTo(getNote[0])
    }
}