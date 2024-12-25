package com.example.notesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.notesapp.ui.auth.signin.SignInScreen
import com.example.notesapp.ui.auth.signup.SignUpScreen
import com.example.notesapp.ui.home.HomeScreen
import com.example.notesapp.ui.note.NotesScreen
import com.example.notesapp.ui.notecategory.NoteCategoryScreen
import com.example.notesapp.ui.onboarding.OnboardingScreen

/**
 * Composables to handle screen navigation across the app
 * */

/**
 * @param navHostController expects the navController for this host
 * @param startDestination expects start destination when the app opens from start
 * */
@Composable
fun NotesNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: NotesDestinations = NotesDestinations.Onboarding
) {

    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestination
    ) {

        composable<NotesDestinations.Onboarding> {
            OnboardingScreen(
                onCreateAccountClicked = {
                    navHostController.goToScreen(NotesDestinations.SignUp)
                },
                onSignInClicked = {
                    navHostController.goToScreen(NotesDestinations.SignIn)
                }
            )
        }
        composable<NotesDestinations.SignUp> {
            SignUpScreen(
                onBackClicked = {
                    navHostController.navigateUp()
                },
                onSignUpClicked = {
                    navHostController.popToScreen(NotesDestinations.Home)
                }
            )
        }
        composable<NotesDestinations.SignIn> {
            SignInScreen(
                onBackClicked = {
                    navHostController.navigateUp()
                },
                onSignInClicked = { email, password ->
                    navHostController.popToScreen(NotesDestinations.Home)
                }
            )
        }
        composable<NotesDestinations.Home> {
            HomeScreen(
                onLogOutClicked = {
                    navHostController.popToScreen(NotesDestinations.Onboarding)
                },
                onNoteItemClicked = {
                    navHostController.goToScreen(NotesDestinations.Note)
                },
                onAddNewCategoryClicked = {
                    navHostController.goToScreen(NotesDestinations.NoteCategory)
                },
                onNewNoteClicked = {
                    navHostController.goToScreen(NotesDestinations.Note)
                }
            )
        }
        composable<NotesDestinations.NoteCategory> {
            NoteCategoryScreen(
                onBackClicked = {
                    navHostController.navigateUp()
                },
                onAddNewCategoryClicked = {
                    navHostController.popToScreenInBackStack(NotesDestinations.Home)
                }
            )
        }
        composable<NotesDestinations.Note> {
            NotesScreen(
                onBackClicked = {
                    navHostController.navigateUp()
                },
                onDeleteNoteClicked = {
                    navHostController.popToScreenInBackStack(NotesDestinations.Home)
                },
                onSaveClicked = {
                    navHostController.popToScreenInBackStack(NotesDestinations.Home)
                }
            )
        }
    }
}

/**
 * Extention funciton to navigate to a screen
 *
 * @param screen expects screen as NotesDestinations
 * */
fun NavController.goToScreen(screen: NotesDestinations) {
    return this.navigate(screen){
        launchSingleTop = true

        restoreState = true
        popBackStack(
            screen,
            inclusive = false,
            saveState = true
        )
    }
}

/**
 * Extention function to navigate to a screen and
 * clear all destinations in back stack
 *
 * @param screen expects screen as NotesDestinations
 * */

fun NavController.popToScreen(screen: NotesDestinations) {
    return this.navigate(screen) {
        popUpTo(0) { inclusive = true }
    }

}

/**
 * Extention function to navigate to a screen in back stack
 *
 * @param screen expects screen as NotesDestinations
 * */

fun NavController.popToScreenInBackStack(screen: NotesDestinations) {
    return this.navigate(screen) {
        popUpTo(screen) { inclusive = true }
    }

}
