package com.example.notesapp.domain.user

import com.example.notesapp.data.user.UserRepo

/**
 * Is User Logged In use case
 * */
class UserLoggedInUseCase(
    private val userRepo: UserRepo
) {

    operator fun invoke(): Boolean{
        return userRepo.hasUser()
    }
}