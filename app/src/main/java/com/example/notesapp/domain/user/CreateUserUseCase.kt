package com.example.notesapp.domain.user

import com.example.notesapp.data.user.User
import com.example.notesapp.data.user.UserRepo

/**
 * Create User use case
 * */
class CreateUserUseCase(
    private val userRepo: UserRepo
) {
    /**
     * @param userId expects userId provided by firebase on sucessful sign up
     * @param user expects prefilled info of user info filled at sign up
     * */
    suspend operator fun invoke(
        userId: String,
        user: User
    ){
        val updateUserInfo = user.copy(
            id = userId
        )
        return userRepo.insertUser(updateUserInfo)
    }
}