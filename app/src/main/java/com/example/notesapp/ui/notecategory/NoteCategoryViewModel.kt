package com.example.notesapp.ui.notecategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.notecategory.NoteCategory
import com.example.notesapp.domain.notecategory.CreateNoteCategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * NoteCategory View Model to act
 * as a middle man communication between
 * ui and data needed for the Note Category flow
 * */
class NoteCategoryViewModel(
    private val createNoteCategoryUseCase: CreateNoteCategoryUseCase
): ViewModel() {

    private var _noteCategoryUiState = MutableStateFlow<NoteCategoryUiState?>(null)
    val noteCategoryUiState get() = _noteCategoryUiState

    private var _noteCategory = MutableStateFlow(NoteCategory(id = ""))
    val noteCategory get() = _noteCategory


    /**
     * Note Category Ui State
     * */
    sealed class NoteCategoryUiState{
        data object Success: NoteCategoryUiState()
    }

    /**
     * reset Note Category Ui State
     * */
    fun resetNoteCategoryUiState(){
        _noteCategoryUiState.value = null
    }


    /**
     * update category name
     *
     * @param name category name expected
     * */
    fun updateCategoryName(name: String) {
        _noteCategory.value = _noteCategory.value.copy(
            name = name
        )
    }

    /**
     * save note category
     * */
    suspend fun saveNoteCategory() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                createNoteCategoryUseCase(
                    noteCategory = _noteCategory.value
                )


            }
            _noteCategoryUiState.value = NoteCategoryUiState.Success
        }
    }

}