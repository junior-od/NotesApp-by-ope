package com.example.notesapp.ui.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.constants.note.NoteConstants
import com.example.notesapp.data.note.Note
import com.example.notesapp.data.todos.NoteTodo
import com.example.notesapp.domain.note.CreateNoteUseCase
import com.example.notesapp.domain.note.DeleteNoteUseCase
import com.example.notesapp.domain.note.EditNoteUseCase
import com.example.notesapp.domain.note.GetNoteUseCase
import com.example.notesapp.domain.notecategory.GetAllNoteCategoryUseCase
import com.example.notesapp.domain.todos.CreateNoteTodosUseCase
import com.example.notesapp.domain.todos.EditNoteTodosUseCase
import com.example.notesapp.domain.todos.GetTodosByNoteIdUseCase
import com.example.notesapp.domain.user.UserLoggedInIdUseCase
import com.example.notesapp.domain.util.FormatFullDateTimeUseCase
import com.example.notesapp.utils.UniqueIdGeneratorUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteViewModel(
    private val getAllNoteCategoryUseCase: GetAllNoteCategoryUseCase,
    private val createNoteUseCase: CreateNoteUseCase,
    private val createNoteTodosUseCase: CreateNoteTodosUseCase,
    private val editNoteUseCase: EditNoteUseCase,
    private val editNoteTodosUseCase: EditNoteTodosUseCase,
    private val userLoggedInIdUseCase: UserLoggedInIdUseCase,
    private val getNoteUseCase: GetNoteUseCase,
    private val getTodosByNoteIdUseCase: GetTodosByNoteIdUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val formatFullDateTimeUseCase: FormatFullDateTimeUseCase
): ViewModel() {

    private var _noteUiState = MutableStateFlow<NoteUiState?>(null)
    val noteUiState get() = _noteUiState

    private var _note = MutableStateFlow<Note?>(
        null
    )
    val note get() = _note

    private var _todos = MutableStateFlow<MutableList<NoteTodo>>(
        mutableListOf()
    )
    val todos get() = _todos.map {
        list -> list.filter { it.deleteFlag == 0 }
    }

    private var _addNewTodo = MutableStateFlow(false)
    val addNewTodo get() = _addNewTodo

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
            _todos.value = withContext(Dispatchers.IO) { getTodosByNoteIdUseCase(noteId) }.toMutableList()


        }

    }

    /**
     * update add new to-do state
     * */
    fun updateAddNewTodoState() {
        _addNewTodo.value = !_addNewTodo.value
    }

    /**
     * Note  Ui State
     * */
    sealed class NoteUiState{
        data object Loading: NoteUiState()
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
     * update note title
     *
     * @param title note title
     * */
    fun updateNoteTitle(title: String){
        _note.value = _note.value?.copy(
            noteTitle = title
        )
    }

    /**
     * update note body
     *
     * @param body note body
     * */
    fun updateNoteBody(body: String){
        _note.value = _note.value?.copy(
            noteInfo = body
        )
    }

    /**
     * add a new to-do
     *
     * @param to-do to-do info
     * */
    fun addNewTodo(todo: String = ""){
        if(todo.isEmpty()) {
            return
        }

        val newTodo = NoteTodo(
            id = UniqueIdGeneratorUtils.uniqueIdGenerator("NTD",userLoggedInIdUseCase()),
            todo = todo,
        )

        _todos.value.add(newTodo)
    }

    /**
     * modify to-do completed state
     *
     * @param todoId to-do id
     * */
    fun modifyTodoCompletedState(todoId: String ){
        _todos.value =  _todos.value.map {
                        if (it.id == todoId) {
                            it.copy(
                                todoCompleted = if (it.todoCompleted == 0) 1 else 0,
                                syncFlag = 0
                            )
                        } else {
                            it
                        }
       }.toMutableList()
    }

    /**
     * delete to-do
     *
     * @param todoId to-do id
     * */
    fun deleteTodo(todoId: String ){
        _todos.value =  _todos.value.map {
            if (it.id == todoId) {
                it.copy(
                    deleteFlag = 1,
                    syncFlag = 0
                )
            } else {
                it
            }
        }.toMutableList()
    }

    /**
     * update note tag
     *
     * @param tagId expects tag id
     * */
    fun updateNoteTag(tagId: String){
        _note.value = _note.value?.copy(
            noteCategoryId = tagId
        )
    }

    /**
     * save note or update note
     *
     * @param entryType expects entry type
     * */
    suspend fun saveNoteData(entryType: NoteConstants.NoteEntryTypes) {
        if (_note.value?.noteTitle.isNullOrEmpty() || _note.value?.noteInfo.isNullOrEmpty()){
            return
        }

        viewModelScope.launch {
            _noteUiState.value = NoteUiState.Loading

            when (entryType) {
                NoteConstants.NoteEntryTypes.NEW_NOTE -> {
                    // save note
                    createNoteUseCase(
                        note = _note.value
                    )

                    // save note todos
                    createNoteTodosUseCase(
                        noteTodos = _todos.value ,
                        noteId = _note.value?.id.toString()
                    )

                    _noteUiState.value = NoteUiState.AddNoteSuccess

                }

                NoteConstants.NoteEntryTypes.EDIT_NOTE -> {
                    // update note
                    editNoteUseCase(
                        note = _note.value
                    )


                    // edit note todos and save new todos if any added
                    editNoteTodosUseCase(
                        noteTodos = _todos.value ,
                        noteId = _note.value?.id.toString()
                    )

                    _noteUiState.value = NoteUiState.AddNoteSuccess
                }
            }

        }
    }

    /**
     * delete note
     *
     * */
    suspend fun deleteNote(){
        viewModelScope.launch {
            _noteUiState.value = NoteUiState.Loading
            withContext(Dispatchers.IO){
                deleteNoteUseCase(
                    note = _note.value
                )
            }

            _noteUiState.value = NoteUiState.DeleteNoteSuccess
        }
    }

    /**
     * update bottom sheet state
     *
     * @param state determine if bottom sheet should be visible or not
     * */
    fun updateCategoryBottomSheetState(state: Boolean){
        _showTagBottomSheet.value = state
    }

    /**
     * get note last updated date
     * */
    fun getNoteLastUpdatedDate(): String{
        return formatFullDateTimeUseCase(
            date = _note.value?.updatedAt
        )
    }

}