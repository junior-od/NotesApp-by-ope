package com.example.notesapp.data.user

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow

/**
 * user repository to communicate with remote
 * server and local db to fetch
 * and save user data
 * */

class UserRepository(
    private val userDao: UserDao,
    private val firebaseAuth: FirebaseAuth
): UserRepo {
    override suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    override fun signedInUser(): Flow<User?> {
        TODO("Not yet implemented")
    }

    /**
     *  sign up with user provided information
     *
     *  @param email expects email
     *  @param password expects password
     *  @param onResultCallback callback triggered on success or failure
     * */
    override suspend fun signUpWithUserInfo(
        email: String,
        password: String,
        onResultCallback: (result: String, isSuccessFul: Boolean) -> Unit
    ) {

        firebaseAuth.createUserWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener {  result ->
            if (result.isSuccessful) {

                onResultCallback(result.result.user?.uid.toString(), true)
            } else {

                onResultCallback(
                    result.exception?.message ?: "Authentication failed.",
                    false
                )
            }
        }
    }

    override suspend fun signInWithEmailPassword(email: String, password: String) {
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithGoogle() {
        TODO("Not yet implemented")
    }
}