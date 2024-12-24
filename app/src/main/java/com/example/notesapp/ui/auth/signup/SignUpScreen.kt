package com.example.notesapp.ui.auth.signup

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notesapp.R
import com.example.notesapp.ui.components.EditInputField
import com.example.notesapp.ui.components.EditInputFieldPassword
import com.example.notesapp.ui.components.NotesButton
import com.example.notesapp.ui.components.TopNavBarWithScreenTitle
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.notesapp.ui.theme.inputFormHeight
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

/**
 * composable components for the signup screen
 * */

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
    onSignUpClicked: () -> Unit = { },
    signUpViewModel: SignUpViewModel = koinViewModel()
){

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(WindowInsets.ime.asPaddingValues())
            .background(
                color = MaterialTheme.colorScheme.background
            )
    ) {
        val coroutineScope = rememberCoroutineScope()

        val user by signUpViewModel.newUser.collectAsStateWithLifecycle()
        val password by signUpViewModel.password.collectAsStateWithLifecycle()
        val passwordVisible by signUpViewModel.passwordVisible.collectAsStateWithLifecycle()
        val signUpState by signUpViewModel.signUpUiState.collectAsStateWithLifecycle()

        // top section
        TopNavBarWithScreenTitle(
            screenTitle = stringResource(R.string.sign_up),
            onBackClicked = onBackClicked
        )

        // form section
        SignUpForm(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(0.7f),
            firstName = user.firstName ?: "",
            onFirstNameChange = {
                signUpViewModel.updateFirstName(it)
            },
            lastName = user.lastName ?: "",
            onLastNameChange = {
                signUpViewModel.updateLastName(it)
            },
            email = user.email ?: "",
            onEmailChange = {
                signUpViewModel.updateEmail(it)
            },
            password = password,
            onPasswordChange = {
                signUpViewModel.updatePassword(
                    it
                )
            },
            passwordVisible = passwordVisible,
            onPasswordIconClicked = {
                signUpViewModel.updatePasswordVisible()
            }

        )

        // bottom section
        SignUpButtonSection(
            modifier = modifier.fillMaxSize(),
            onSignUpClicked = {
                coroutineScope.launch {
                    signUpViewModel.signUpUser()
                }
            }
        )

        // observe ui state changes
        when (signUpState) {
            is SignUpViewModel.SignUpUi.Loading -> {
                // todo show loader compose here
            }

            is SignUpViewModel.SignUpUi.Success -> {
                onSignUpClicked()
            }

            is SignUpViewModel.SignUpUi.Error -> {
               val errorMessage = (signUpState as SignUpViewModel.SignUpUi.Error).message
               // todo display error message from request here
            }

            else -> {
                // do nothing
            }
        }
    }
}

/**
 * sign up form
 *
 * @param firstName expects first name data
 * @param onFirstNameChange observes when first name input changes
 * @param lastName expects last name data
 * @param onLastNameChange observes when last name input changes
 * @param email expects email data
 * @param onEmailChange observes when email input changes
 * @param password expects password data
 * @param onPasswordChange observes when password input changes
 * @param passwordVisible whether password input info should be visible or masked
 * @param onPasswordIconClicked triggers the function when password icon is clicked
 * */
@Composable
fun SignUpForm(
    modifier: Modifier = Modifier,
    firstName: String = "",
    onFirstNameChange: (text: String) -> Unit = {_ -> },
    lastName: String = "",
    onLastNameChange: (text: String) -> Unit = {_ -> },
    email: String = "",
    onEmailChange: (text: String) -> Unit = {_ -> },
    password: String = "",
    onPasswordChange: (text: String) -> Unit = {_ -> },
    passwordVisible: Boolean = false,
    onPasswordIconClicked: () -> Unit = {}
){

    Column(
        modifier = modifier.verticalScroll(
            state = rememberScrollState()
        )
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        EditInputField(
            modifier = Modifier
                .height(inputFormHeight)
                .fillMaxWidth(),
            text = firstName,
            onValueChange = onFirstNameChange,
            placeholder = stringResource(R.string.first_name),
            keyboardOptions = KeyboardOptions
                .Default.copy(
                    imeAction = ImeAction.Next
                )
        )

        Spacer(modifier = Modifier.height(16.dp))

        EditInputField(
            modifier = Modifier
                .height(inputFormHeight)
                .fillMaxWidth(),
            text = lastName,
            onValueChange = onLastNameChange,
            placeholder = stringResource(R.string.last_name),
            keyboardOptions = KeyboardOptions
                .Default.copy(
                    imeAction = ImeAction.Next
                )
        )

        Spacer(modifier = Modifier.height(16.dp))

        EditInputField(
            modifier = Modifier
                .height(inputFormHeight)
                .fillMaxWidth(),
            text = email,
            onValueChange = onEmailChange,
            placeholder = stringResource(R.string.email),
            keyboardOptions = KeyboardOptions
                .Default.copy(
                    imeAction = ImeAction.Next
                )
        )

        Spacer(modifier = Modifier.height(16.dp))

        EditInputFieldPassword(
            modifier = Modifier
                .height(inputFormHeight)
                .fillMaxWidth(),
            text = password,
            onValueChange = onPasswordChange,
            placeholder = stringResource(R.string.password),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            isPasswordVisible = passwordVisible,
            trailingIconClicked = onPasswordIconClicked,
            )

        Spacer(modifier = Modifier.height(16.dp))
    }

}

/**
 * sign up button section
 *
 * @param onSignUpClicked function triggered when sign up is clicked
 * */
@Composable
fun SignUpButtonSection(
    modifier: Modifier = Modifier,
    onSignUpClicked: () -> Unit = {},
    ) {
    Box(modifier = modifier) {
        val buttonWidthModifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 16.dp)
        NotesButton(
            modifier = buttonWidthModifier.align(Alignment.BottomStart),
            onClick = onSignUpClicked,
            buttonText = stringResource(R.string.sign_up)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showSystemUi = true, showBackground = true, apiLevel = 29
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showSystemUi = true, showBackground = true, apiLevel = 29,
)
@Composable
private fun SignUpScreenPreview(){
    NotesAppTheme {
        SignUpScreen()
    }
}