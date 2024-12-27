package com.example.notesapp.di

import com.example.notesapp.domain.note.CreateNoteUseCase
import com.example.notesapp.domain.note.DeleteNoteUseCase
import com.example.notesapp.domain.note.EditNoteUseCase
import com.example.notesapp.domain.note.GetNoteUseCase
import com.example.notesapp.domain.note.GetNotesWithTodosUseCase
import com.example.notesapp.domain.notecategory.CreateNoteCategoryUseCase
import com.example.notesapp.domain.notecategory.GetAllNoteCategoryUseCase
import com.example.notesapp.domain.todos.CreateNoteTodosUseCase
import com.example.notesapp.domain.todos.EditNoteTodosUseCase
import com.example.notesapp.domain.todos.GetTodosByNoteIdUseCase
import com.example.notesapp.domain.todos.UpdateNoteTodoUseCase
import com.example.notesapp.domain.user.CreateUserUseCase
import com.example.notesapp.domain.user.SignInUseCase
import com.example.notesapp.domain.user.SignOutUseCase
import com.example.notesapp.domain.user.SignUpUseCase
import com.example.notesapp.domain.user.UserLoggedInIdUseCase
import com.example.notesapp.domain.user.UserLoggedInUseCase
import com.example.notesapp.domain.util.FormatFullDateTimeUseCase
import org.koin.dsl.module
import kotlin.math.sin

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

    // provide a single instance of UserLoggedInId UseCase
    single<UserLoggedInIdUseCase>{
        UserLoggedInIdUseCase(get())
    }

    // provide a single instance of CreateNoteCategory UseCase
    single<CreateNoteCategoryUseCase>{
        CreateNoteCategoryUseCase(
            get(), get()
        )
    }

    // provide a single instance of GetAllNoteCategory UseCase
    single<GetAllNoteCategoryUseCase> {
        GetAllNoteCategoryUseCase(
            get(),
            get()
        )
    }

    // provide a single instance of CreateNote UseCase
    single<CreateNoteUseCase> {
        CreateNoteUseCase(
            get(),
            get()
        )
    }

    // provide a single instance of EditNote UseCase
    single<EditNoteUseCase> {
        EditNoteUseCase(
            get()
        )
    }

    // provide a single instance of DeleteNote UseCase
    single<DeleteNoteUseCase> {
        DeleteNoteUseCase(
            get()
        )
    }

    // provide a single instance of GetNote UseCase
    single<GetNoteUseCase> {
        GetNoteUseCase(
            get()
        )
    }

    // provide a single instance of CreateNoteTodos UseCase
    single<CreateNoteTodosUseCase> {
        CreateNoteTodosUseCase(
            get(),
            get()
        )
    }

    // provide a single instance of EditNoteTodos UseCase
    single<EditNoteTodosUseCase> {
        EditNoteTodosUseCase(
            get(), get()
        )
    }

    // provide a single instance of UpdateNoteTodo UseCase
    single<UpdateNoteTodoUseCase> {
        UpdateNoteTodoUseCase(
            get()
        )
    }

    // provide a single instance of GetTodosByNoteId UseCase
    single<GetTodosByNoteIdUseCase>{
        GetTodosByNoteIdUseCase(
            get()
        )
    }

    // provide a single instance of GetNotesWithTodos UseCase
    single<GetNotesWithTodosUseCase> {
        GetNotesWithTodosUseCase(
            get(),
            get()
        )
    }

    // provide a single instance of FormatFullDateTimeUseCase
    single<FormatFullDateTimeUseCase>{
        FormatFullDateTimeUseCase()
    }
}