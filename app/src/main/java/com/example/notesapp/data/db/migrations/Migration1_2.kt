package com.example.notesapp.data.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notesapp.data.notecategory.NoteCategoryTable

/**
 * migration to add Note Category Table
 * */
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE ${NoteCategoryTable.TABLE_NAME}" +
            "(`${NoteCategoryTable.ID}` TEXT NOT NULL , " +
            "`${NoteCategoryTable.NAME}` TEXT , " +
            "`${NoteCategoryTable.CREATED_BY}` TEXT , " +
            "`${NoteCategoryTable.CREATED_AT}` TEXT , " +
            "`${NoteCategoryTable.UPDATED_AT}` TEXT , " +
            "`${NoteCategoryTable.SYNC_FLAG}` INTEGER NOT NULL , " +
            "PRIMARY KEY(`${NoteCategoryTable.ID}`))"
        )
    }
}