package com.example.notesapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.data.db.migrations.MIGRATION_1_2
import com.example.notesapp.data.notecategory.NoteCategory
import com.example.notesapp.data.notecategory.NoteCategoryDao
import com.example.notesapp.data.user.User
import com.example.notesapp.data.user.UserDao

/**
 * Note database
 *  where all entities will be declared as well as table migrations
 * */

@Database(
    entities = [
        NoteCategory::class,
        User::class
               ],
    version = 2
)
abstract class NotesDatabase: RoomDatabase() {

    /**
     * dao for the note category table
     * */
    abstract fun noteCategoryDao(): NoteCategoryDao

    /**
     * dao for the user table
     * */
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var notesDbInstance: NotesDatabase? = null
        private const val DATABASE_NAME = "notes.db"

        // singleton pattern
        fun getInstance(context: Context): NotesDatabase {
            if (notesDbInstance == null) {
                synchronized(NotesDatabase::class) {

                    notesDbInstance = Room.databaseBuilder(
                        context = context.applicationContext,
                        klass = NotesDatabase::class.java,
                        name = DATABASE_NAME
                    )
                        .addMigrations(
                            MIGRATION_1_2
                        )
                        .build()
                }
            }
            return notesDbInstance!!
        }

    }

}