package com.example.notesapp.data.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * User Entity or table
 *
 * @param id Unique identifier of user record
 * @param firstName first name of user
 * @param lastName last name of user
 * @param email email of user
 * @param signUpMethod method which user accessed notes app
 * @param createdAt time user record was created
 * @param updatedAt time user record was updated
 * */

@Entity(tableName = UserTable.TABLE_NAME)
data class User(
    @ColumnInfo(name = UserTable.ID)
    @PrimaryKey
    var id: String,

    @ColumnInfo(name = UserTable.FIRST_NAME)
    var firstName: String? = null,

    @ColumnInfo(name = UserTable.LAST_NAME)
    var lastName: String? = null,

    @ColumnInfo(name = UserTable.EMAIL)
    var email: String? = null,

    @ColumnInfo(name = UserTable.SIGN_UP_METHOD)
    var signUpMethod: String? = null,

    @ColumnInfo(name = UserTable.CREATED_AT)
    var createdAt: String? = null,

    @ColumnInfo(name = UserTable.UPDATED_AT)
    var updatedAt: String? = null,

    @ColumnInfo(name = UserTable.SYNC_FLAG)
    var syncFlag: Int = 0,
)


object UserTable {
    const val TABLE_NAME = "users"

    // columns
    const val ID = "id"

    const val FIRST_NAME = "first_name"
    const val LAST_NAME = "last_name"
    const val EMAIL = "email"
    const val SIGN_UP_METHOD = "sign_up_method"
    const val CREATED_AT = "created_at"
    const val UPDATED_AT = "updated_at"
    const val SYNC_FLAG = "sync_flag"
}
