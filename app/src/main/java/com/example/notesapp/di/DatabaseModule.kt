package com.example.notesapp.di

import com.example.notesapp.data.db.NotesDatabase
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

}