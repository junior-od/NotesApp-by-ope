package com.example.notesapp.di

import com.example.notesapp.data.db.NotesDatabase
import com.example.notesapp.data.note.NoteDao
import com.example.notesapp.data.notecategory.NoteCategoryDao
import com.example.notesapp.data.todos.NoteTodoDao
import com.example.notesapp.data.user.UserDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * this is the container to define how database dependencies are to be provided across the app
 * */

val databaseModule = module {

    // provide a single instance of notes database
    single<NotesDatabase> {
        NotesDatabase.getInstance(androidApplication())
    }

    // provide single instance of user dao
    single<UserDao> {
        val database = get<NotesDatabase>() // retrieve single instance of db provided
        database.userDao() // return userDao
    }

    // provide single instance of note category dao
    single<NoteCategoryDao>{
        val database = get<NotesDatabase>() // retrieve single instance of db provided
        database.noteCategoryDao()
    }

    // provide single instance of note dao
    single<NoteDao>{
        val database = get<NotesDatabase>() // retrieve single instance of db provided
        database.noteDao()
    }

    // provide single instance of note to-do dao
    single<NoteTodoDao>{
        val database = get<NotesDatabase>() // retrieve single instance of db provided
        database.noteTodoDao()
    }

}