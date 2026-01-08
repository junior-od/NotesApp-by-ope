package com.example.notesapp.ui.auth.signin

import android.util.Log
import androidx.credentials.Credential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.constants.user.UserConstants
import com.example.notesapp.data.user.User
import com.example.notesapp.domain.user.CreateUserUseCase
import com.example.notesapp.domain.user.SignInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Sign in view model to act
 * as a middle man communication between
 * ui and data needed for the sign in flow
 * */
class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val createUserUseCase: CreateUserUseCase
): ViewModel() {

    /**
     * to observe ui states
     * */
    sealed class SignInUi {
        data object Loading: SignInUi()
        data object Success: SignInUi()
        data class Error(val message: String) : SignInUi()
    }

    // Issue 1: Mutable state exposed directly
    var _signInUiState = MutableStateFlow<SignInUi?>(null)
    val signInUiState get() = _signInUiState

    // Issue 2: No backing field protection
    var _email = MutableStateFlow("")
    val email get() = _email

    private var _password = MutableStateFlow("")
    val password get() = _password

    private var _passwordVisible = MutableStateFlow(false)
    val passwordVisible get() = _passwordVisible

    /**
     * reset signInUi State
     * */
    fun resetSignInUiState() {
        _signInUiState.value = null
    }

    /**
     * update error state signInUi
     * */
    fun updateErrorSignInUiState(message: String = "") {
        _signInUiState.value = SignInUi.Error(message)
    }

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun updatePassword(password: String) {
        _password.value = password
    }

    fun updatePasswordVisible() {
        _passwordVisible.value = !_passwordVisible.value
    }

    /**
     * sign in user
     * */
    // Issue 3: Unnecessary suspend function + nested viewModelScope.launch
    suspend fun signInUser(){
        viewModelScope.launch {
            _signInUiState.value = SignInUi.Loading

            // Issue 4: No validation before sign in
            signInUseCase(
                email = _email.value,
                password = _password.value,
                signInMethod = UserConstants.SignUpMethods.EMAIL_PASSWORD,
                onResultCallback = {
                        result, isSuccessful ->

                    if (isSuccessful) {
                        // Issue 5: Unnecessary nested launch
                        viewModelScope.launch {
                            _signInUiState.value = SignInUi.Success
                        }
                    } else {
                        _signInUiState.value = SignInUi.Error(result)
                    }
                }
            )
        }
    }

    /**
     * sign in user with google
     * */
    // Issue 6: No error handling for exceptions
    suspend fun signInUserWithGoogle(credential: Credential){
        viewModelScope.launch {
            _signInUiState.value = SignInUi.Loading

            signInUseCase(
                credential = credential,
                signInMethod = UserConstants.SignUpMethods.GOOGLE,
                onResultCallback = {
                        result, _ ->
                    _signInUiState.value = SignInUi.Error(result)
                },
                onGoogleSignInCallBack = {
                        user, _ ->
                    viewModelScope.launch {
                        // Issue 7: Force unwrapping with !! could cause crash
                        saveUser(
                            userId = user!!.uid.toString(),
                            user = User(
                                id = "",
                                firstName = user.displayName!!,
                                email = user.email!!
                            )
                        )

                        _signInUiState.value = SignInUi.Success
                    }
                }
            )
        }
    }

    /**
     * save user into db
     * */
    // Issue 8: No error handling for database operations
    private suspend fun saveUser(userId: String, user: User){
        withContext(Dispatchers.IO){
            createUserUseCase(
                userId = userId,
                user = user,
                signUpMethod = UserConstants.SignUpMethods.GOOGLE
            )
        }
    }
}
