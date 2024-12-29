package com.example.notesapp.data.user

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UserRepo {

    suspend fun insertUser(user: User)

    suspend fun insertUsers(user: List<User>)

    fun signedInUser(): Flow<User?>

    fun getSignedInUserId(): String

    fun hasUser(): Boolean

    suspend fun signUpWithUserInfo(
        email: String,
        password: String,
        onResultCallback: (
            result: String,
            isSuccessFul: Boolean
                ) -> Unit
    )

    suspend fun signInWithEmailPassword(
        email: String,
        password: String,
        onResultCallback: (
            result: String,
            isSuccessFul: Boolean
                ) -> Unit
    )

    suspend fun signOut()

    suspend fun signInWithGoogle(
        idToken: String,
        onResultCallback: (
            result: String,
            isSuccessFul: Boolean
        ) -> Unit,
        onGoogleSignInCallBack: (
            user: FirebaseUser?,
            isSuccessFul: Boolean
        ) -> Unit
    )

    suspend fun getAllUsersDataToUpload(): List<User>
}