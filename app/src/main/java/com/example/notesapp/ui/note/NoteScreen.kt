package com.example.notesapp.ui.note

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notesapp.R
import com.example.notesapp.data.constants.note.NoteConstants
import com.example.notesapp.data.todos.NoteTodo
import com.example.notesapp.ui.components.EditInputField
import com.example.notesapp.ui.components.NotesEditInputField
import com.example.notesapp.ui.components.TagBottomSheet
import com.example.notesapp.ui.components.TopNavBarWithScreenTitledIcon
import com.example.notesapp.ui.components.dismissKeyboardOnTouchOutsideInputArea
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.notesapp.ui.theme.calculateLineForOffset
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

/**
 * composable components for the Note screen
 * */

/**
 * @param entryType expects whether it is a new note mode or edit note mode
 * */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    entryType: NoteConstants.NoteEntryTypes = NoteConstants.NoteEntryTypes.NEW_NOTE,
    noteId: String? = null,
    onBackClicked: () -> Unit = {},
    onSaveClicked: () -> Unit = {},
    onDeleteNoteClicked: () -> Unit = {},
    noteViewModel: NoteViewModel = koinViewModel()
){

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(WindowInsets.ime.asPaddingValues())
            .background(
                color = MaterialTheme.colorScheme.background
            )
    ) {
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(key1 = Unit) {
            noteViewModel.initializeNote(noteId = noteId)
        }

        val noteUiState by noteViewModel.noteUiState.collectAsStateWithLifecycle()
        val getCategories by noteViewModel.allNoteCategories.collectAsStateWithLifecycle(
            initialValue = emptyList()
        )

        val showTagBottomSheet by noteViewModel.showTagBottomSheet.collectAsStateWithLifecycle()

        // observe ui state changes
        LaunchedEffect(key1 = noteUiState) {
            when (noteUiState) {
                is NoteViewModel.NoteUiState.AddNoteSuccess -> {
                    onSaveClicked()
                    noteViewModel.resetNoteUiState()
                }

                is NoteViewModel.NoteUiState.DeleteNoteSuccess -> {
                    onDeleteNoteClicked()
                    noteViewModel.resetNoteUiState()
                }

                else -> {
                    // do nothing
                }
            }
        }

        // top section
        NoteNav(
            isNewNote = entryType == NoteConstants.NoteEntryTypes.NEW_NOTE,
            onBackClicked = onBackClicked,
            onSaveClicked = {
                coroutineScope.launch {
                    noteViewModel.saveNoteData(
                        entryType = entryType
                    )
                }
            },
            onDeleteNoteClicked = {
                coroutineScope.launch {
                    noteViewModel.deleteNote()
                }
            }
        )

        Box(modifier = Modifier.fillMaxSize()) {


            NavBody(
               onLabelNoteClicked = {
                   noteViewModel.updateCategoryBottomSheetState(true)
               },
                noteViewModel = noteViewModel
            )

            if (showTagBottomSheet) {

                TagBottomSheet(
                    listOfTags = getCategories,
                    onDismissBottomSheetRequest = {
                        noteViewModel.updateCategoryBottomSheetState( false)
                    },
                    onTagClicked = {
                        tag ->
                        noteViewModel.updateNoteTag(tag.id)
                        noteViewModel.updateCategoryBottomSheetState(false)

                    }
                )
            }

        }

    }
}

@Composable
fun NavBody(
    modifier: Modifier = Modifier,
    onLabelNoteClicked: () -> Unit = {},
    noteViewModel: NoteViewModel
){
    Column(
        modifier = modifier
    ) {


        val screenScrollState = rememberScrollState()

        val coroutineScope = rememberCoroutineScope()

        val titleFocusRequester = remember { FocusRequester() }

        val bodyFocusRequester = remember { FocusRequester() }

        val addNewTodo by noteViewModel.addNewTodo.collectAsStateWithLifecycle()
        val note by noteViewModel.note.collectAsStateWithLifecycle()
        val todos by noteViewModel.todos.collectAsStateWithLifecycle(
            initialValue = emptyList()
        )

        Column(
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .verticalScroll(
                    state = screenScrollState
                )
        ) {
            // last date edited
            LastEditedSection(
                modifier = Modifier.fillMaxWidth(),
                lastEditedInfo = noteViewModel.getNoteLastUpdatedDate()
            )

            val title = TextFieldValue(
                note?.noteTitle ?: "",
                selection = TextRange((note?.noteTitle?.length ?: 0))
            )

            // title section
            TitleSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(titleFocusRequester)
                    .padding(horizontal = 12.dp),
                noteTitle = title,
                onNoteTitleChanged = { newValue ->

                    noteViewModel.updateNoteTitle(newValue.text)

                    // scroll to bottom as title grows

                    coroutineScope.launch {
                        // Adjust scroll only if the cursor moves outside the visible area
                        val cursorOffset = newValue.selection.start
                        val newCursorLine = calculateLineForOffset(newValue.text, cursorOffset)
                        val oldCursorLine = calculateLineForOffset(
                            title.text,
                            title.selection.start
                        )

                        if (newCursorLine != oldCursorLine) {
                            val scrollTarget = screenScrollState.value + (newCursorLine - oldCursorLine) * 110 // Adjust for line height
                            screenScrollState.scrollTo(scrollTarget.coerceIn(0, screenScrollState.maxValue))
                        }
                    }

                }
            )


            val body = TextFieldValue(
                note?.noteInfo ?: "",
                selection = TextRange((note?.noteInfo?.length ?: 0))
            )

            // body section
            BodySection(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(bodyFocusRequester)
                    .padding(horizontal = 16.dp),
                noteBody = body,
                onNoteBodyChanged = { newValue ->

                    noteViewModel.updateNoteBody(newValue.text)

                    // scroll to bottom as body grows

                    coroutineScope.launch {
                        // Adjust scroll only if the cursor moves outside the visible area
                        val cursorOffset = newValue.selection.start
                        val newCursorLine = calculateLineForOffset(newValue.text, cursorOffset)
                        val oldCursorLine = calculateLineForOffset(
                            body.text,
                            body.selection.start
                        )

                        if (newCursorLine != oldCursorLine) {
                            val scrollTarget = screenScrollState.value + (newCursorLine - oldCursorLine) * 50 // Adjust for line height
                            screenScrollState.scrollTo(scrollTarget.coerceIn(0, screenScrollState.maxValue))
                        }
                    }
                }
            )



            // to-do section
            TodoSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                newTodo = addNewTodo,
                todosList = todos,
                onNoteTodoItemClicked = { item ->
                    noteViewModel.modifyTodoCompletedState(item.id)
                },
                onDeleteTodoClicked = { item ->
                    noteViewModel.deleteTodo(item.id)
                },
                onAddNewTodoClicked = { newTodo ->
                    noteViewModel.addNewTodo(todo = newTodo)
                    noteViewModel.updateAddNewTodoState()
                }

            )
        }
        // bottom nav note features
        BottomNavNote(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            onAddNewTodoClicked = {
                noteViewModel.updateAddNewTodoState()
            },
            onAttachFileClicked = {},
            onLabelNoteClicked = onLabelNoteClicked
        )

        LaunchedEffect(key1 = addNewTodo) {
            delay(100)
            // scroll to bottom
            coroutineScope.launch {
                screenScrollState.animateScrollTo(
                    screenScrollState.maxValue + 50
                )
            }

        }
    }
}

/**
 * note nav
 *
 * @param isNewNote expects boolean whether it is a new note mode or edit note mode
 * @param onBackClicked triggers when back button is clicked
 * @param onSaveClicked triggers when the save button is clicked
 * @param onDeleteNoteClicked triggers when the delete button is clicked
 * */
@Composable
fun NoteNav(
    modifier: Modifier = Modifier,
    isNewNote: Boolean = true,
    onBackClicked: () -> Unit = {},
    onSaveClicked: () -> Unit = {},
    onDeleteNoteClicked: () -> Unit = {}
) {

    TopNavBarWithScreenTitledIcon(
        modifier = modifier,
        screenTitle = {
            Row(
                modifier = Modifier
                    .clickable(
                        onClick = onBackClicked
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = stringResource(
                        id = R.string.app_icon
                    )
                )

                Text(
                    text = stringResource(R.string.home),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        showBackIcon = false ,
        actions = {
            if (!isNewNote) {
                // render delete icon button for edit mode
                IconButton(
                    modifier = Modifier.size(30.dp),
                    onClick = onDeleteNoteClicked
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        tint = MaterialTheme.colorScheme.errorContainer,
                        contentDescription = stringResource(R.string.delete_icon)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))
            }


            IconButton(
                modifier = Modifier.size(40.dp),
                onClick = onSaveClicked
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = stringResource(R.string.save_icon)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
        }
    )



}

/**
 * last edited section

 * @param lastEditedInfo expects info about last date time the note was modified
 * */
@Composable
fun LastEditedSection(
    modifier: Modifier = Modifier,
    lastEditedInfo: String = ""
){

    Text(
        modifier = modifier,
        text = lastEditedInfo,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.outline,
            fontSize = 10.sp
        )
    )
}

/**
 * title section
 *
 * @param placeholder expects placeholder of note here
 * @param noteTitle expects title of the note
 * @param onNoteTitleChanged observes new changes on note title
 * */
@Composable
fun TitleSection(
    modifier: Modifier = Modifier,
    placeholder: String = "Title",
    noteTitle: TextFieldValue = TextFieldValue(""),
    onNoteTitleChanged: (text: TextFieldValue) -> Unit = {_ -> }
){
    NotesEditInputField(
        modifier = modifier,
        placeholder = if (noteTitle.text.isEmpty()) placeholder else "",
        text = noteTitle,
        textStyle = MaterialTheme.typography.displayMedium.copy(
           color = MaterialTheme.colorScheme.onSurface
        ),
        placeholderTextStyle = MaterialTheme.typography.displayMedium.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        onValueChange = onNoteTitleChanged,
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Words
        )
    )
}

/**
 * note section
 *
 * @param placeholder expects placeholder body of note here
 * @param noteBody expects body of the note
 * @param onNoteBodyChanged observes new changes on note body
 * */
@Composable
fun BodySection(
    modifier: Modifier = Modifier,
    placeholder: String = "Your Text",
    noteBody: TextFieldValue = TextFieldValue(""),
    onNoteBodyChanged: (text: TextFieldValue) -> Unit = {_ -> }
){
    NotesEditInputField(
        modifier = modifier,
        placeholder = if (noteBody.text.isEmpty()) placeholder else "",
        text = noteBody,
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        placeholderTextStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        onValueChange = onNoteBodyChanged,
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Sentences
        )
    )
}

/**
 * To-do section
 *
 * @param newTodo: to display new to-do input field for new to-do or remove input field if already visible
 * @param todosList: list of todos for the note
 * @param onNoteTodoItemClicked: trigger an update status action when a to-do item is clicked
 * @param onDeleteTodoClicked: trigger delete action when
 * */
@Composable
fun TodoSection(
    modifier: Modifier = Modifier,
    newTodo: Boolean = true,
    todosList: List<NoteTodo> = emptyList(),
    onNoteTodoItemClicked: (item: NoteTodo) -> Unit = {_ ->},
    onDeleteTodoClicked: (item: NoteTodo) -> Unit = {_ ->},
    onAddNewTodoClicked: (newTodo: String) -> Unit
) {

    Column(
        modifier = modifier
    ) {
        todosList.forEach {
            SingleNoteToDoItem(
                todoInfo = it,
                onNoteTodoItemClicked = onNoteTodoItemClicked,
                onDeleteTodoItemClicked = onDeleteTodoClicked
            )
        }

        AnimatedVisibility(
            visible = newTodo
            ) {
            // move to viewmodel
            var newTodoInfo by remember {
                mutableStateOf("")
            }
            NewTodoItemInputField(
                modifier = Modifier.fillMaxWidth(),
                newTodo = newTodoInfo,
                onNewTodoChange = {
                    newTodoInfo = it
                },
                onAddNewTodoClicked = onAddNewTodoClicked
            )
        }
    }
}

/**
 * New Item To-do
 *
 * @param newTodo: expects new to-do info in the input
 * @param onNewTodoChange : observe the changes in the new to-do input
 * @param onAddNewTodoClicked: triggers a function to create a new to-do ticket in the note
 * */
@Composable
fun NewTodoItemInputField(
    modifier: Modifier = Modifier,
    newTodo: String = "",
    placeholder: String = stringResource(R.string.what_to_do),
    onNewTodoChange: (text: String) -> Unit = {_ ->},
    onAddNewTodoClicked: (newTodo: String) -> Unit = {_ ->}
){
    val keyboardController = LocalSoftwareKeyboardController.current

    EditInputField(
        modifier = modifier,
        text = newTodo,
        placeholder = if (newTodo.isEmpty()) placeholder else "",
        onValueChange = onNewTodoChange,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onAddNewTodoClicked(newTodo)

                //dismiss keyboard at this point
                keyboardController?.hide()
            }
        )
    )
}

/**
 * Single Note To-Do Item
 *
 * @param todoInfo: expects to-do information
 * @param onDeleteTodoItemClicked: triggers function to delete to-do from the list
 * @param onNoteTodoItemClicked: triggers function on to-do item clicked to update status
 * @param onDeleteTodoItemClicked: triggers function on delete to-do item clicked
 * */
@Composable
fun SingleNoteToDoItem(
    modifier: Modifier = Modifier,
    todoInfo: NoteTodo,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    onNoteTodoItemClicked: (item: NoteTodo) -> Unit = {},
    onDeleteTodoItemClicked: (item: NoteTodo) -> Unit = {}
){

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val iconToShow = if (todoInfo.todoCompleted == 1)
            painterResource(
                id = R.drawable.ic_check_circle
            )
        else painterResource(
            id = R.drawable.ic_unchecked
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .clickable(
                    onClick = {
                        onNoteTodoItemClicked(todoInfo)
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = iconToShow,
                tint = contentColor,
                contentDescription = stringResource(
                    R.string.todo_status_icon
                )
            )

            Text(
                text = todoInfo.todo ?: "",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = contentColor,
                    textDecoration = if (todoInfo.todoCompleted == 1) TextDecoration.LineThrough else TextDecoration.None
                ),
            )
        }

        IconButton(
            onClick = {
                onDeleteTodoItemClicked(todoInfo)
            }
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Filled.Close,
                tint = contentColor,
                contentDescription = stringResource(R.string.remove_to_do_item_icon)
            )
        }

    }

}

/**
 * Bottom Nav Note to contain all
 * additional note functionality
 *
 * @param onAddNewTodoClicked triggers the function when onAddNewTodo icon is clicked
 * @param onLabelNoteClicked triggers the function when onLabel note icon is clicked
 * @param onAttachFileClicked triggers the function when onAttach file icon is clicked
 *
 * */
@Composable
fun BottomNavNote(
    modifier: Modifier = Modifier,
    onAddNewTodoClicked: () -> Unit = {},
    onLabelNoteClicked: () -> Unit = {},
    onAttachFileClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onAddNewTodoClicked
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.ic_list),
                contentDescription = stringResource(R.string.add_todo_icon)
            )
        }

        IconButton(
            onClick = onLabelNoteClicked
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.ic_tag),
                contentDescription = stringResource(R.string.add_todo_icon)
            )
        }

        IconButton(
            onClick = onAttachFileClicked
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.ic_attach_file),
                contentDescription = stringResource(R.string.add_todo_icon)
            )
        }
    }

}



@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showSystemUi = true, showBackground = true, apiLevel = 29
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showSystemUi = true, showBackground = true, apiLevel = 29,
)
@Composable
private fun NotesScreenPreview(){
    NotesAppTheme {
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current
        NotesScreen(
            modifier = Modifier
                .dismissKeyboardOnTouchOutsideInputArea(
                    keyboardController,
                    focusManager
                )
        )
    }
}