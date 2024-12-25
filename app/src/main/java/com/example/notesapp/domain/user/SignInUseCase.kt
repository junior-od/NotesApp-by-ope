package com.example.notesapp.domain.user

import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import com.example.notesapp.data.constants.user.UserConstants
import com.example.notesapp.data.user.UserRepo
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseUser

/**
 * Sign in use case
 * */
class SignInUseCase(
    private val userRepo: UserRepo
) {

    /**
     * @param email expects email
     * @param password expects password
     * @param credential user credential from google
     * @param onResultCallback callback triggered on success or failure
     * @param onGoogleSignInCallBack callback triggered on success google sign in
     * */
    suspend operator fun invoke(
        email: String = "",
        password: String = "",
        credential: Credential? = null,
        signInMethod: UserConstants.SignUpMethods = UserConstants.SignUpMethods.EMAIL_PASSWORD,
        onResultCallback: (result: String, isSuccessFul: Boolean) -> Unit = {_,_ ->},
        onGoogleSignInCallBack: (
            user: FirebaseUser?,
            isSuccessFul: Boolean
        ) -> Unit = {_,_ ->}
    ) {
        return when (signInMethod){
            UserConstants.SignUpMethods.GOOGLE -> {
                if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    userRepo.signInWithGoogle(
                        idToken = googleIdTokenCredential.idToken,
                        onResultCallback = onResultCallback,
                        onGoogleSignInCallBack = onGoogleSignInCallBack
                    )
                } else {
                    onResultCallback("Invalid Google Credentials", false)
                }

            }
            else -> {
                userRepo.signInWithEmailPassword(
                    email = email,
                    password = password,
                    onResultCallback = onResultCallback
                )
            }
        }
    }
}