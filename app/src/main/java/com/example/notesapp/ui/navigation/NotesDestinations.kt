package com.example.notesapp.ui.navigation

import com.example.notesapp.R
import kotlinx.serialization.Serializable


/**
 * Possible destinations that can be navigated to within the app
 * */

@Serializable
sealed interface NotesDestinationsInfo {
    val title: Int?
}

@Serializable
sealed class NotesDestinations: NotesDestinationsInfo {

    /** onboarding screen pointer */
    @Serializable
    data object Onboarding: NotesDestinations() {
        override val title: Int
            get() = R.string.onboarding_screen
    }

    /** Sign in screen pointer */
    @Serializable
    data object SignIn: NotesDestinations() {
        override val title: Int
            get() = R.string.sign_in_screen
    }

    /** Sign up screen pointer */
    @Serializable
    data object SignUp: NotesDestinations() {
        override val title: Int
            get() = R.string.sign_up_screen
    }

    /** Home screen pointer */
    @Serializable
    data object Home: NotesDestinations() {
        override val title: Int
            get() = R.string.home_screen
    }

    /** Note Category screen pointer */
    @Serializable
    data object NoteCategory: NotesDestinations() {
        override val title: Int
            get() = R.string.note_category_screen
    }

    /** Note screen pointer */
    @Serializable
    data object Note: NotesDestinations() {
        override val title: Int
            get() = R.string.note_screen
    }

}