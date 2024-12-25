package com.example.notesapp.ui.onboarding

import androidx.lifecycle.ViewModel
import com.example.notesapp.R
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Sign up view model to act
 * as a middle man communication between
 * ui and data needed for the onboarding flow
 * */

class OnboardingViewModel: ViewModel() {

    private val _onboardingFeatures = MutableStateFlow(
        listOf(
            R.string.create_an_account_to_keep_your_thoughts_in_one_place,
            R.string.categorize_your_notes_for_easy_access,
            R.string.make_a_list_of_things_to_do
        )
    )

    val onboardingFeatures get() = _onboardingFeatures
}