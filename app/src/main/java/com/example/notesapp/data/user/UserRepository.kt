package com.example.notesapp.data.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
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

    /**
     *  sign in with user provided information
     *
     *  @param email expects email
     *  @param password expects password
     *  @param onResultCallback callback triggered on success or failure
     * */
    override suspend fun signInWithEmailPassword(
        email: String, password: String,onResultCallback: (result: String, isSuccessFul: Boolean) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(
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

    /**
     * sign out logged in user
     * */

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    /**
     *  sign in with user google provided information
     *
     *  @param idToken expects user google credential idToken
     *  @param onResultCallback callback triggered on success or failure
     *  @param onGoogleSignInCallBack callback triggered on success google sign in
     * */
    override suspend fun signInWithGoogle(
        idToken: String,
        onResultCallback: (result: String, isSuccessFul: Boolean) -> Unit,
        onGoogleSignInCallBack: (user: FirebaseUser?, isSuccessFul: Boolean) -> Unit
    ) {
       val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth
            .signInWithCredential(firebaseCredential)
            .addOnCompleteListener {  result ->
            if (result.isSuccessful) {

                onGoogleSignInCallBack(result.result.user, true)
            } else {

                onResultCallback(
                    result.exception?.message ?: "Authentication failed.",
                    false
                )
            }
        }
    }
}