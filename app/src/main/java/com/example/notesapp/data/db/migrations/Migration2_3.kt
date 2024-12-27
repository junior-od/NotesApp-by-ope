package com.example.notesapp.data.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notesapp.data.note.NoteTable
import com.example.notesapp.data.todos.NoteTodoTable

/**
 * migration to add Note Table and Todos table
 * */
val MIGRATION_2_3 = object: Migration(2,3){
    override fun migrate(db: SupportSQLiteDatabase) {

        // note table
        db.execSQL(
            "CREATE TABLE ${NoteTable.TABLE_NAME}" +
                    "(`${NoteTable.ID}` TEXT NOT NULL , " +
                    "`${NoteTable.NOTE_CATEGORY_ID}` TEXT , " +
                    "`${NoteTable.NOTE_TITLE}` TEXT , " +
                    "`${NoteTable.NOTE_INFO}` TEXT , " +
                    "`${NoteTable.DELETE_FLAG}` INTEGER NOT NULL , " +
                    "`${NoteTable.CREATED_BY}` TEXT , " +
                    "`${NoteTable.CREATED_AT}` TEXT , " +
                    "`${NoteTable.UPDATED_AT}` TEXT , " +
                    "`${NoteTable.SYNC_FLAG}` INTEGER NOT NULL , " +
                    "PRIMARY KEY(`${NoteTable.ID}`))"
        )

        // note to-do table
        db.execSQL(
            "CREATE TABLE ${NoteTodoTable.TABLE_NAME}" +
                    "(`${NoteTodoTable.ID}` TEXT NOT NULL , " +
                    "`${NoteTodoTable.NOTE_ID}` TEXT , " +
                    "`${NoteTodoTable.TODO}` TEXT , " +
                    "`${NoteTodoTable.TODO_COMPLETED}` INTEGER NOT NULL , " +
                    "`${NoteTodoTable.DELETE_FLAG}` INTEGER NOT NULL , " +
                    "`${NoteTodoTable.CREATED_BY}` TEXT , " +
                    "`${NoteTodoTable.CREATED_AT}` TEXT , " +
                    "`${NoteTodoTable.UPDATED_AT}` TEXT , " +
                    "`${NoteTodoTable.SYNC_FLAG}` INTEGER NOT NULL , " +
                    "PRIMARY KEY(`${NoteTodoTable.ID}`))"
        )

    }

}