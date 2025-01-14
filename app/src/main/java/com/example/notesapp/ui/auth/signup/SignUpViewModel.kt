package com.example.notesapp.ui.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.constants.user.UserConstants
import com.example.notesapp.data.user.User
import com.example.notesapp.domain.user.CreateUserUseCase
import com.example.notesapp.domain.user.SignUpUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Sign up view model to act
 * as a middle man communication between
 * ui and data needed for the sign up flow
 * */
class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val createUserUseCase: CreateUserUseCase
): ViewModel() {

    /**
     * to observe ui states
     * */
    sealed class SignUpUi {
        data object Loading: SignUpUi()
        data object Success: SignUpUi()
        data class Error(val message: String) : SignUpUi()

    }

    private var _signUpUiState = MutableStateFlow<SignUpUi?>(null)
    val signUpUiState get() = _signUpUiState

    private var _newUser = MutableStateFlow(
        User(
            id = ""
        )
    )

    val newUser get() = _newUser

    private var _password = MutableStateFlow(
        ""
    )

    val password get() = _password

    private var _passwordVisible = MutableStateFlow(
        false
    )

    val passwordVisible get() = _passwordVisible

    /**
     * reset signupUi State
     * */
    fun resetSignUpUiState() {
        _signUpUiState.value = null
    }

    /**
     * update password
     *
     * @param password: expects password
     * */
    fun updatePassword(password: String) {
        _password.value = password
    }

    /**
     * update password visibility
     * */
    fun updatePasswordVisible() {
        _passwordVisible.value = !_passwordVisible.value
    }


    /**
     * update first name
     *
     * @param firstName: expects name
     * */
    fun updateFirstName(firstName: String) {
        _newUser.value = _newUser.value.copy(
            firstName = firstName.ifEmpty { null }
        )
    }

    /**
     * update last name
     *
     * @param lastName: expects name
     * */
    fun updateLastName(lastName: String) {
        _newUser.value = _newUser.value.copy(
            lastName = lastName.ifEmpty { null }
        )
    }

    /**
     * update email
     *
     * @param email: expects email
     * */
    fun updateEmail(email: String) {
        _newUser.value = _newUser.value.copy(
            email = email.ifEmpty { null }
        )
    }

    /**
     * sign up user
     * */
    suspend fun signUpUser() {
        viewModelScope.launch {

            // update state as loading
            _signUpUiState.value = SignUpUi.Loading

            signUpUseCase(
                email = _newUser.value.email.toString(),
                password = _password.value,
                onResultCallback = {
                    result, isSuccessful ->

                    if (isSuccessful) {
                        viewModelScope.launch {
                            // save user info to db
                            saveUser(result)

                            // observe changes and send to ui
                            // update state as sign up successful
                            _signUpUiState.value = SignUpUi.Success
                        }
                    } else {
                        // observe changes and send to ui
                        // update state as Error occurred
                        _signUpUiState.value = SignUpUi.Error(result)
                    }

                }
            )
        }
    }

    /**
     * save user into db
     *
     * @param userId expects user id generated by firebase on sign up successful
     * */
    private suspend fun saveUser(userId: String){
        withContext(Dispatchers.IO){
            createUserUseCase(
                userId = userId,
                user = _newUser.value,
                signUpMethod = UserConstants.SignUpMethods.EMAIL_PASSWORD
            )
        }

    }
}