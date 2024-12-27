package com.example.notesapp.domain.user

import com.example.notesapp.data.user.UserRepo

/**
 * UserLoggedInId UseCase
 * */
class UserLoggedInIdUseCase(
    private val userRepo: UserRepo
) {

    operator fun invoke(): String {
        return userRepo.getSignedInUserId()
    }
}