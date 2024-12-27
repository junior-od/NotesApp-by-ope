package com.example.notesapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.todos.NoteTodo
import com.example.notesapp.domain.note.GetNotesWithTodosUseCase
import com.example.notesapp.domain.todos.UpdateNoteTodoUseCase
import com.example.notesapp.domain.user.SignOutUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * HomeViewModel to act
 * as a middle man communication between
 * ui and data needed for the home flow
 * */

class HomeViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val getNotesWithTodosUseCase: GetNotesWithTodosUseCase,
    private val updateNoteTodoUseCase: UpdateNoteTodoUseCase
): ViewModel() {

    private var _homeStateUi = MutableStateFlow<HomeStateUi?>(null)
    val homeStateUi get() = _homeStateUi

    private val _allNotes = getNotesWithTodosUseCase()
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed()
        )
    val allNotes get() = _allNotes

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

    /**
     * update to-do status
     *
     * @param to-do expects to do record
     * */
    suspend fun updateTodo(todo: NoteTodo) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                updateNoteTodoUseCase(noteTodo = todo)
            }
        }
    }
}