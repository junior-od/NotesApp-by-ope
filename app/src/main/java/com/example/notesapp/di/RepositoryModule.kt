package com.example.notesapp.di

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

}