package com.example.notesapp.ui.auth.signin

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.Credential
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notesapp.R
import com.example.notesapp.ui.components.EditInputField
import com.example.notesapp.ui.components.EditInputFieldPassword
import com.example.notesapp.ui.components.ErrorMessage
import com.example.notesapp.ui.components.GoogleLoginButton
import com.example.notesapp.ui.components.NotesButton
import com.example.notesapp.ui.components.TopNavBarWithScreenTitle
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.notesapp.ui.theme.inputFormHeight
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

/**
 * composable components for the signin screen
 * */

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
    onSignInClicked: () -> Unit = { },
    onGoogleSignInClicked: () -> Unit = {},
    signInViewModel: SignInViewModel = koinViewModel()
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
        val email by signInViewModel.email.collectAsStateWithLifecycle()
        val password by signInViewModel.password.collectAsStateWithLifecycle()
        val passwordVisible by signInViewModel.passwordVisible.collectAsStateWithLifecycle()
        val signInState by signInViewModel.signInUiState.collectAsStateWithLifecycle()

        // top section
        TopNavBarWithScreenTitle(
            screenTitle = stringResource(id = R.string.log_in),
            onBackClicked = onBackClicked
        )

        var showError by remember {
            mutableStateOf(false)
        }

        // observe ui state changes
        LaunchedEffect(key1 = signInState) {
            if (signInState is SignInViewModel.SignInUi.Success) {
                onSignInClicked()
                signInViewModel.resetSignInUiState()
            } else if(signInState is SignInViewModel.SignInUi.Error) {
                showError = true
            }
        }

        // hide or show error here
        AnimatedVisibility(visible = showError) {
            val errorMessage = if(signInState is SignInViewModel.SignInUi.Error)  (signInState as SignInViewModel.SignInUi.Error).message else ""
            ErrorMessage(
                message = errorMessage,
                onRemoveMessageClicked = {
                    showError = false
                }
            )
        }

        // form section
        SignInForm(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(0.7f),
            email = email,
            onEmailChange = {
                signInViewModel.updateEmail(it)
            },
            password = password,
            onPasswordChange = {
                signInViewModel.updatePassword(it)
            },
            passwordVisible = passwordVisible,
            onPasswordIconClicked = {
                signInViewModel.updatePasswordVisible()
            },
            onGoogleSignInClicked = {
                credential ->
                coroutineScope.launch {
                    signInViewModel.signInUserWithGoogle(credential)
                }
            },
            onErrorOccurred = {
                error ->
                signInViewModel.updateErrorSignInUiState(error)
            }
        )

        // bottom section
        LoginButtonSection(
            modifier = modifier.fillMaxSize(),
            onSignInClicked = {
                coroutineScope.launch {
                    signInViewModel.signInUser()
                }
            }
        )
    }
}

/**
 * sign in form
 *
 * @param email expects email data
 * @param onEmailChange observes when email input changes
 * @param password expects password data
 * @param onPasswordChange observes when password input changes
 * @param passwordVisible whether password input info should be visible or masked
 * @param onPasswordIconClicked triggers the function when password icon is clicked
 * @param onGoogleSignInClicked triggers the function when the sign in with google is clicked
 * @param onErrorOccurred function listens for error occurred
 * */
@Composable
fun SignInForm(
    modifier: Modifier = Modifier,
    email: String = "",
    onEmailChange: (text: String) -> Unit = {_ -> },
    password: String = "",
    onPasswordChange: (text: String) -> Unit = {_ -> },
    passwordVisible: Boolean = false,
    onPasswordIconClicked: () -> Unit = {},
    onGoogleSignInClicked: (Credential) -> Unit,
    onErrorOccurred: (String) -> Unit = {}
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
                .fillMaxWidth()
                .paddingFromBaseline(top = 8.dp, bottom = 8.dp),
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

        Spacer(modifier = Modifier.height(24.dp))

        // sigin in with google
        val buttonWidthModifier = Modifier
            .fillMaxWidth(1f)
        GoogleLoginButton (
            modifier = buttonWidthModifier,
            buttonText = stringResource(id = R.string.sign_in_with_google),
            onGetCredentialResponse = onGoogleSignInClicked,
            onErrorOccurred = onErrorOccurred
        )
        Spacer(modifier = Modifier.height(16.dp))
    }

}

/**
 * login button section
 *
 * @param onSignInClicked triggers function when log in button is clicked
 * */
@Composable
fun LoginButtonSection(
    modifier: Modifier = Modifier,
    onSignInClicked: () -> Unit = { },
) {
    Box(modifier = modifier) {
        val buttonWidthModifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 16.dp)
        NotesButton(
            modifier = buttonWidthModifier.align(Alignment.BottomStart),
            onClick = onSignInClicked,
            buttonText = stringResource(R.string.log_in)
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
private fun SignInScreenPreview(){
    NotesAppTheme {

        SignInScreen()
    }
}
