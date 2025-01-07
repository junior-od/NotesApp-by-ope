package com.example.notesapp.data.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.notesapp.data.db.NotesDatabase
import com.google.common.truth.Truth
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

@SmallTest
class UserRepositoryTest{
    //rule basically to allow tasks(coroutine tasks) to be run synchronously
    // and immediately on same thread, to observe and get immediate results
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var notesDatabase: NotesDatabase
    private lateinit var userDao: UserDao
    private lateinit var userRepo: UserRepo
    private lateinit var firebaseAuth: FirebaseAuth

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
        firebaseAuth = mock(FirebaseAuth::class.java)
        userRepo = UserRepository(
            userDao,
            firebaseAuth
        )
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

        userRepo.insertUser(note)

        val getNote = userRepo.getAllUsersDataToUpload()

        Truth.assertThat(note).isEqualTo(getNote[0])
    }

    /**
     * test that get all users to upload returns
     * data when record with sync flag 0 exists in the db
     * */
    @Test
    fun testGetAllUsersDataToUpload_Returns_DataToUpload() = runTest{
        val users = listOf(
            User(
                id = "id",
                syncFlag = 0
            ),
            User(
                id = "ired",
                syncFlag = 0
            ),
            User(
                id = "idrre",
                syncFlag = 1
            ),
        )

        // filter for expected result
        val expectedResult = users.filter {  it.syncFlag == 0 }

        userRepo.insertUsers(users)

        val getUsers = userRepo.getAllUsersDataToUpload()

        Truth.assertThat(getUsers).isEqualTo(expectedResult)
    }
}