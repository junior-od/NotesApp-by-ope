package com.example.notesapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.domain.user.SignOutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * HomeViewModel to act
 * as a middle man communication between
 * ui and data needed for the home flow
 * */

class HomeViewModel(
    private val signOutUseCase: SignOutUseCase
): ViewModel() {

    private var _homeStateUi = MutableStateFlow<HomeStateUi?>(null)
    val homeStateUi get() = _homeStateUi

    sealed class HomeStateUi {
        data object SignedOut : HomeStateUi()
    }

    /**
     * reset homestateui
     * */
    fun resetHomeStateUi(){
        _homeStateUi.value = null
    }

    /**
     * sign out user
     * */
    suspend fun signOutUser(){
        viewModelScope.launch {
            signOutUseCase()

            _homeStateUi.value = HomeStateUi.SignedOut

        }
    }
}