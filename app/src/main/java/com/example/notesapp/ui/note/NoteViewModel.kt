package com.example.notesapp.ui.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.note.Note
import com.example.notesapp.data.todos.NoteTodo
import com.example.notesapp.domain.note.GetNoteUseCase
import com.example.notesapp.domain.notecategory.GetAllNoteCategoryUseCase
import com.example.notesapp.domain.todos.CreateNoteTodosUseCase
import com.example.notesapp.domain.todos.EditNoteTodosUseCase
import com.example.notesapp.domain.user.UserLoggedInIdUseCase
import com.example.notesapp.utils.UniqueIdGeneratorUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteViewModel(
    private val getAllNoteCategoryUseCase: GetAllNoteCategoryUseCase,
    private val createNoteTodosUseCase: CreateNoteTodosUseCase,
    private val editNoteTodosUseCase: EditNoteTodosUseCase,
    private val userLoggedInIdUseCase: UserLoggedInIdUseCase,
    private val getNoteUseCase: GetNoteUseCase
): ViewModel() {

    private var _noteUiState = MutableStateFlow<NoteUiState?>(null)
    val noteUiState get() = _noteUiState

    private var _note = MutableStateFlow<Note?>(
        null
    )
    private val note get() = _note

    private var _todos = MutableStateFlow<List<NoteTodo>>(
        emptyList()
    )
    private val todos get() = _todos


    private val _allNoteCategories = getAllNoteCategoryUseCase()
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed()
        )
    val allNoteCategories get() = _allNoteCategories

    private var _showTagBottomSheet = MutableStateFlow(false)
    val showTagBottomSheet get() = _showTagBottomSheet

    /**
     * initialize note
     *
     * @param noteId if note Id record is provided
     * */
    suspend fun initializeNote(noteId: String? = null){
        viewModelScope.launch {

            // get note or create new

            val getNote = withContext(Dispatchers.IO) { getNoteUseCase(noteId) }
            _note.value = getNote ?: Note(
                id = UniqueIdGeneratorUtils.uniqueIdGenerator("N",userLoggedInIdUseCase())
            )

            // get todos of the note if available or set as empty

        }

    }

    /**
     * Note  Ui State
     * */
    sealed class NoteUiState{
        data object AddNoteSuccess: NoteUiState()
        data object DeleteNoteSuccess: NoteUiState()
    }

    /**
     * reset Note Ui State
     * */
    fun resetNoteUiState(){
        _noteUiState.value = null
    }

    /**
     * update bottom sheet state
     *
     * @param state determine if bottom sheet should be visible or not
     * */
    fun updateCategoryBottomSheetState(state: Boolean){
        _showTagBottomSheet.value = state
    }

}