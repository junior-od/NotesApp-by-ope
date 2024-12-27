package com.example.notesapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.notecategory.NoteCategory
import com.example.notesapp.data.todos.NoteTodo
import com.example.notesapp.domain.note.GetNotesWithTodosUseCase
import com.example.notesapp.domain.notecategory.GetAllNoteCategoryUseCase
import com.example.notesapp.domain.todos.UpdateNoteTodoUseCase
import com.example.notesapp.domain.user.SignOutUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.transform
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
    private val updateNoteTodoUseCase: UpdateNoteTodoUseCase,
    private val getAllNoteCategoryUseCase: GetAllNoteCategoryUseCase
): ViewModel() {

    private var _homeStateUi = MutableStateFlow<HomeStateUi?>(null)
    val homeStateUi get() = _homeStateUi

    private var _allNotes = getNotesWithTodosUseCase()
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed()
        )
    val allNotes get() = _allNotes

    val defaultNoteCategory = NoteCategory(
        id = "1",
        name = "All"
    )

    private val _allNoteCategories = getAllNoteCategoryUseCase()
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed()
        )
    val allNoteCategories = _allNoteCategories

    private var _selectedCategoryId = MutableStateFlow("1")
    val selectedCategoryId get() = _selectedCategoryId

    private var _findNote = MutableStateFlow("")
    val findNote get () = _findNote

    sealed class HomeStateUi {
        data object SignedOut : HomeStateUi()
    }

    /**
     * update find note
     *
     * @param noteTitle expects note title
     * */
    fun updateFindNote(noteTitle: String) {
        _findNote.value = noteTitle
        updateNoteQuery()
    }

    /**
     * update selected category id
     *
     * @param id expects selected category id
     * */
    fun updateSelectedCategoryId(id: String){
        _selectedCategoryId.value = id
        updateNoteQuery()
    }

    private fun updateNoteQuery(){
        _allNotes = getNotesWithTodosUseCase(
            search = _findNote.value,
            categoryId = _selectedCategoryId.value
        )
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed()
            )
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