package com.example.notesapp.data.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * db operations on user table
 * */

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(user: List<User>)

    /**
     * get users data to upload
     * */
    @Query(
        "SELECT * FROM ${UserTable.TABLE_NAME} WHERE ${UserTable.SYNC_FLAG} = 0"
    )
    suspend fun getAllUsersDataToUpload(): List<User>

}