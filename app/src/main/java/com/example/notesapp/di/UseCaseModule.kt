package com.example.notesapp.di

import com.example.notesapp.domain.user.CreateUserUseCase
import com.example.notesapp.domain.user.SignUpUseCase
import org.koin.dsl.module

/**
 * this is the container to define how usecases dependencies are to be provided across the app
 * */


val useCaseModule  = module {
    // provide a single instance of create user use case
    single<CreateUserUseCase> {
        CreateUserUseCase(get())
    }

    // provide a single instance of sign up use case
    single<SignUpUseCase> {
        SignUpUseCase(get())
    }
}