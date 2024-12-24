package com.example.notesapp.data.user

import kotlinx.coroutines.flow.Flow

interface UserRepo {

    suspend fun insertUser(user: User)

    fun signedInUser(): Flow<User?>

    suspend fun signUpWithUserInfo(
        email: String,
        password: String,
        onResultCallback: (
            result: String,
            isSuccessFul: Boolean
                ) -> Unit
    )

    suspend fun signInWithEmailPassword(email: String, password: String)

    suspend fun signOut()

    suspend fun signInWithGoogle()
}