package com.example.notesapp.domain.user

import com.example.notesapp.data.constants.user.UserConstants
import com.example.notesapp.data.user.User
import com.example.notesapp.data.user.UserRepo
import com.example.notesapp.utils.DateTimeUtils

/**
 * Create User use case
 * */
class CreateUserUseCase(
    private val userRepo: UserRepo
) {
    /**
     * @param userId expects userId provided by firebase on sucessful sign up
     * @param user expects prefilled info of user info filled at sign up
     * @param signUpMethod expects signup method
     * */
    suspend operator fun invoke(
        userId: String,
        user: User,
        signUpMethod: UserConstants.SignUpMethods = UserConstants.SignUpMethods.EMAIL_PASSWORD
    ){

        // todo later add some check to save from google sign in if user record does not exist

        val updateUserInfo = user.copy(
            id = userId,
            signUpMethod = signUpMethod.name,
            createdAt = DateTimeUtils.getCurrentDateTimeInFullDateTimeFormat(),
            updatedAt = DateTimeUtils.getCurrentDateTimeInFullDateTimeFormat()
        )
        return userRepo.insertUser(updateUserInfo)
    }
}