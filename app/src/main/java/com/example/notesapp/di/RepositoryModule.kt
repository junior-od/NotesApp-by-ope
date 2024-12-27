package com.example.notesapp.di

import com.example.notesapp.data.note.NoteRepo
import com.example.notesapp.data.note.NoteRepository
import com.example.notesapp.data.notecategory.NoteCategoryRepo
import com.example.notesapp.data.notecategory.NoteCategoryRepository
import com.example.notesapp.data.todos.NoteTodoRepo
import com.example.notesapp.data.todos.NoteTodoRepository
import com.example.notesapp.data.user.UserRepo
import com.example.notesapp.data.user.UserRepository
import org.koin.dsl.module

/**
 * this is the container to define how repository dependencies are to be provided across the app
 * */

val repositoryModule = module {
    // provide a single instance of user repository
    single<UserRepo> {
        UserRepository(get(), get())
    }

    // provide a single instance of note category repository
    single<NoteCategoryRepo> {
        NoteCategoryRepository(get())
    }

    // provide a single instance of note repository
    single<NoteRepo> {
        NoteRepository(get())
    }

    // provide a single instance of note to-do repository
    single<NoteTodoRepo> {
        NoteTodoRepository(get())
    }

}