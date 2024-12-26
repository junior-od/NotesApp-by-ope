package com.example.notesapp.di

import com.example.notesapp.domain.notecategory.CreateNoteCategoryUseCase
import com.example.notesapp.domain.user.CreateUserUseCase
import com.example.notesapp.domain.user.SignInUseCase
import com.example.notesapp.domain.user.SignOutUseCase
import com.example.notesapp.domain.user.SignUpUseCase
import com.example.notesapp.domain.user.UserLoggedInUseCase
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

    // provide a single instance on sign in use case
    single<SignInUseCase> {
        SignInUseCase(get())
    }

    // provide a single instance on UserLoggedIn UseCase
    single<UserLoggedInUseCase> {
        UserLoggedInUseCase(get())
    }

    // provide a single instance of SignOut UseCase
    single<SignOutUseCase>{
        SignOutUseCase(get())
    }

    // provide a single instance of CreateNoteCategory UseCase
    single<CreateNoteCategoryUseCase>{
        CreateNoteCategoryUseCase(
            get(), get()
        )
    }
}