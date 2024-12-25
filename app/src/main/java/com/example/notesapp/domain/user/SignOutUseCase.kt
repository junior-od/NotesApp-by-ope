package com.example.notesapp.domain.user

import com.example.notesapp.data.user.UserRepo

/**
 * Sign Out use case
 * */
class SignOutUseCase(
    private val userRepo: UserRepo
) {

    suspend operator fun invoke(){
        return userRepo.signOut()
    }
}