package com.example.notesapp.domain.user

import com.example.notesapp.data.user.UserRepo

/**
 * Sign up use case
 * */
class SignUpUseCase(
    private val userRepo: UserRepo
) {
    /**
     * @param email expects email
     * @param password expects password
     * @param onResultCallback callback triggered on success or failure
     * */
    suspend operator fun invoke(
        email: String,
        password: String,
        onResultCallback: (result: String, isSuccessFul: Boolean) -> Unit
    ) {
        return userRepo.signUpWithUserInfo(
            email = email,
            password = password,
            onResultCallback = onResultCallback
        )
    }
}