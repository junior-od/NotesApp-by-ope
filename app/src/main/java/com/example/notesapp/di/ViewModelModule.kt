package com.example.notesapp.di

import com.example.notesapp.MainActivityViewModel
import com.example.notesapp.ui.auth.signin.SignInViewModel
import com.example.notesapp.ui.auth.signup.SignUpViewModel
import com.example.notesapp.ui.home.HomeViewModel
import com.example.notesapp.ui.onboarding.OnboardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * this is the container to define how viewmodel dependencies are to be provided across the app
 * */

val viewModelModule = module {
    // provide a viewmodel instance of onboarding viewmodel
    viewModel {
        OnboardingViewModel()
    }

    // provide a viewmodel instance of sign up viewmodel
    viewModel {
        SignUpViewModel(
            get(),
            get()
        )
    }

    // provide a viewmodel instance of sign in viewmodel
    viewModel {
        SignInViewModel(
            get(),
            get()
        )
    }

    // provide a viewmodel instance of main activity viewmodel
    viewModel {
        MainActivityViewModel(
            get()
        )
    }

    // provide a viewmodel instance of home viewmodel
    viewModel {
        HomeViewModel(
            get()
        )
    }
}