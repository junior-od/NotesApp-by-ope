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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notesapp.R
import com.example.notesapp.ui.components.EditInputField
import com.example.notesapp.ui.components.EditInputFieldPassword
import com.example.notesapp.ui.components.NotesButton
import com.example.notesapp.ui.components.TopNavBarWithScreenTitle
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.notesapp.ui.theme.inputFormHeight

/**
 * composable components for the signup screen
 * */

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
    onSignUpClicked: (email: String, password: String, firstName: String, lastName: String) -> Unit = { _, _,_,_ -> },
    onGoogleSignUpClicked: () -> Unit = {}
){

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(WindowInsets.ime.asPaddingValues())
            .background(
                color = MaterialTheme.colorScheme.background
            )
    ) {

        // top section
        TopNavBarWithScreenTitle(
            screenTitle = stringResource(R.string.sign_up),
            onBackClicked = onBackClicked
        )

        // form section
        SignUpForm(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(0.7f)
        )

        // bottom section
        SignUpButtonSection(
            modifier = modifier.fillMaxSize(),
            onSignUpClicked = onSignUpClicked
        )
    }
}

/**
 * sign up form
 * */
@Composable
fun SignUpForm(
    modifier: Modifier = Modifier,
){

    Column(
        modifier = modifier.verticalScroll(
            state = rememberScrollState()
        )
    ) {

        // todo move this to viewmodel
        var firstName by remember {
            mutableStateOf("")
        }

        // todo move this to viewmodel
        var lastName by remember {
            mutableStateOf("")
        }


        // todo move this to viewmodel
        var email by remember {
            mutableStateOf("")
        }

        // todo move this to viewmodel
        var password by remember {
            mutableStateOf("")
        }

        // todo move this to viewmodel
        var passwordVisible by remember {
            mutableStateOf(false)
        }

        // todo move this to viewmodel
        var confirmPassword by remember {
            mutableStateOf("")
        }

        // todo move this to viewmodel
        var confirmPasswordVisible by remember {
            mutableStateOf(false)
        }

        Spacer(modifier = Modifier.height(30.dp))

        EditInputField(
            modifier = Modifier
                .height(inputFormHeight)
                .fillMaxWidth(),
            text = firstName,
            onValueChange = {
                firstName = it
            },
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
            onValueChange = {
                lastName = it
            },
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
            onValueChange = {
                email = it
            },
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
            onValueChange = {
                password = it
            },
            placeholder = stringResource(R.string.password),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            isPasswordVisible = passwordVisible,
            trailingIconClicked = {
                passwordVisible = !passwordVisible
            },

            )

        Spacer(modifier = Modifier.height(16.dp))

        EditInputFieldPassword(
            modifier = Modifier
                .height(inputFormHeight)
                .fillMaxWidth(),
            text = confirmPassword,
            onValueChange = {
                confirmPassword = it
            },
            placeholder = stringResource(R.string.confirm_password),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            isPasswordVisible = confirmPasswordVisible,
            trailingIconClicked = {
                confirmPasswordVisible = !confirmPasswordVisible
            },

            )

        Spacer(modifier = Modifier.height(16.dp))
    }

}

/**
 * sign up button section
 * */
@Composable
fun SignUpButtonSection(
    modifier: Modifier = Modifier,
    onSignUpClicked: (email: String, password: String, firstName: String, lastName: String) -> Unit = { _, _,_,_ -> },
    ) {
    Box(modifier = modifier) {
        val buttonWidthModifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 16.dp)
        NotesButton(
            modifier = buttonWidthModifier.align(Alignment.BottomStart),
            onClick = {
                onSignUpClicked("","","","")
            },
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