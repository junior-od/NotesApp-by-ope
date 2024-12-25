package com.example.notesapp

import androidx.lifecycle.ViewModel
import com.example.notesapp.domain.user.UserLoggedInUseCase

/**
 * MainActivityViewModel to act
 * as a middle man communication between
 * ui and data needed for the main activity
 * */
class MainActivityViewModel(
    private val userLoggedInUseCase: UserLoggedInUseCase
): ViewModel() {

    /**
     * is user logged in
     * */
    fun isUserLoggedIn(): Boolean{
        return userLoggedInUseCase()
    }

}