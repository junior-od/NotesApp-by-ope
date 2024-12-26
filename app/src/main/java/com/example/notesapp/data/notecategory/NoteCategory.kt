package com.example.notesapp.data.notecategory

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * NoteCategory Entity or table
 *
 * @param id Unique identifier of user record
 * @param name name of note category
 * @param createdBy user id of the category creator
 * @param createdAt time user record was created
 * @param updatedAt time user record was updated
 * */

@Entity(tableName = NoteCategoryTable.TABLE_NAME)
data class NoteCategory(
    @ColumnInfo(name = NoteCategoryTable.ID)
    @PrimaryKey
    var id: String,

    @ColumnInfo(name = NoteCategoryTable.NAME)
    var name: String? = null,

    @ColumnInfo(name = NoteCategoryTable.CREATED_BY)
    var createdBy: String? = null,

    @ColumnInfo(name = NoteCategoryTable.CREATED_AT)
    var createdAt: String? = null,

    @ColumnInfo(name = NoteCategoryTable.UPDATED_AT)
    var updatedAt: String? = null,

    @ColumnInfo(name = NoteCategoryTable.SYNC_FLAG)
    var syncFlag: Int = 0,
)

object NoteCategoryTable {
    const val TABLE_NAME = "notes_category"

    // columns
    const val ID = "id"

    const val NAME = "name"
    const val CREATED_BY = "created_by"
    const val CREATED_AT = "created_at"
    const val UPDATED_AT = "updated_at"
    const val SYNC_FLAG = "sync_flag"
}
