package com.example.notesapp.ui.home

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ChipElevation
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChipDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notesapp.R
import com.example.notesapp.data.note.Note
import com.example.notesapp.data.note.NoteWithTodosModel
import com.example.notesapp.data.todos.NoteTodo
import com.example.notesapp.ui.components.EditInputFieldWitTrailingIcon
import com.example.notesapp.ui.components.TopNavBarWithScreenTitledIcon
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.notesapp.ui.theme.rolledEdgeShape
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

/**
 * composable components for the home screen
 * */

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onLogOutClicked: () -> Unit = {},
    onNoteItemClicked: (item: Note) -> Unit = {_ -> },
    onNewNoteClicked: () -> Unit = { },
    onAddNewCategoryClicked: () -> Unit = {},
    homeViewModel: HomeViewModel = koinViewModel()
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(WindowInsets.ime.asPaddingValues())
            .background(
                color = MaterialTheme.colorScheme.background
            )
    ){
        val homeState by homeViewModel.homeStateUi.collectAsStateWithLifecycle()
        val coroutineScope = rememberCoroutineScope()

        // observe ui state changes
        LaunchedEffect(key1 = homeState) {
            if (homeState is HomeViewModel.HomeStateUi.SignedOut) {
                onLogOutClicked()
                homeViewModel.resetHomeStateUi()
            }
        }

        // top section
        HomeTopNav(
            modifier = Modifier.fillMaxWidth(),
            onLogOutClicked = {
                coroutineScope.launch {
                    homeViewModel.signOutUser()
                }
            },
            onNewNoteClicked = onNewNoteClicked
        )

        // todo move to viewmodel
        var findNote by remember {
            mutableStateOf("")
        }

        // find notes
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            findNote = findNote,
            onFindNoteChange = {
                findNote = it
            },
            onCloseSearchClicked = {
                findNote = ""
            }
        )

        // todo move to viewmodel
        var selectedCategoryName by remember {
            mutableStateOf("All")
        }

        // todo move to viewmodel
        val categoryNames by remember {
            mutableStateOf(listOf("All", "Work", "Important"))
        }
        // category sections
        CategoriesSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            listOfCategories = categoryNames,
            selectedCategoryName = selectedCategoryName,
            onCategoryClicked = {
                selectedCategoryName = it
            },
            onAddCategoryClicked = onAddNewCategoryClicked
        )


        val allNotes by homeViewModel.allNotes.collectAsStateWithLifecycle(initialValue = emptyList())
        // staggered notes
        NotesStaggeredList(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            notesList = allNotes,
            onNotesItemClicked = onNoteItemClicked,
            onTodoItemClicked = {
                todo ->
                coroutineScope.launch {
                    homeViewModel.updateTodo(
                        todo = todo
                    )
                }
            }
        )
    }
}

/**
 * HomeTopNav of home screen
 * @param onLogOutClicked : listens for when log out icon is clicked
 * @param onNewNoteClicked : listens for when new note is clicked
 * */
@Composable
fun HomeTopNav(
    modifier: Modifier = Modifier,
    onLogOutClicked: () -> Unit = {},
    onNewNoteClicked: () -> Unit = { }
) {
    TopNavBarWithScreenTitledIcon(
        modifier = modifier,
        screenTitle = {
            Row(
                modifier = Modifier
                    .clickable(
                        onClick = onNewNoteClicked
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(
                        id = R.drawable.notes_logo
                    ),
                    contentDescription = stringResource(
                        id = R.string.app_icon
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = stringResource(R.string.add_note),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        showBackIcon = false,
        actions = {
            IconButton(
                modifier = Modifier.size(30.dp),
                onClick = onLogOutClicked
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_log_out),
                    contentDescription = stringResource(R.string.log_out_icon)
                )
            }
        }
    )
}

/**
 * Search bar of the home screen
 *
 * @param findNote expects search input
 * @param onFindNoteChange observes search input field and return the latest input
 * @param onCloseSearchClicked triggers function whent the search close icon is clicked
 * */
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    findNote: String = "",
    onFindNoteChange: (note: String) -> Unit = {_ ->},
    onCloseSearchClicked: () -> Unit = {}
){
    EditInputFieldWitTrailingIcon(
        modifier = modifier,
        focusedContainerColor = MaterialTheme.colorScheme.background,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
        focusedIndicatorColor = MaterialTheme.colorScheme.background,
        unFocusedContainerColor = MaterialTheme.colorScheme.background,
        placeholder = if (findNote.isEmpty())
                           stringResource(R.string.search_for_note)
                        else "",
        text = findNote,
        onValueChange = onFindNoteChange,
        trailingIconClicked = onCloseSearchClicked,
        trailingIcon = painterResource(id = R.drawable.ic_close),
        showTrailingIcon = findNote.isNotEmpty()
    )
}

/**
 * Categories sections
 *
 * @param selectedCategoryName expects category name selected from the category list displayed
 * @param listOfCategories expects a list of categries to be displayed
 * @param onCategoryClicked triggers function when the category is clicked
 * */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriesSection(
    modifier: Modifier = Modifier,
    selectedCategoryName: String = "",
    listOfCategories: List<String> = emptyList(),
    onCategoryClicked: (categoryName: String) -> Unit = {_ ->},
    onAddCategoryClicked: () -> Unit = {}
){
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOfCategories.forEach {
            SingleCategory(
                categoryName = it,
                isCategorySelected = selectedCategoryName == it,
                onCategoryClicked = onCategoryClicked
            )
        }

        // include the add category chip here
        AddCategoryChip(
            onAddCategoryClicked = onAddCategoryClicked
        )
    }

}

/**
 * Single Category
 *
 * @param categoryName expects category name to be displayed
 * @param isCategorySelected expects boolean if category name is currently selected
 * @param onCategoryClicked triggers function when the category is clicked
 * */
@Composable
fun SingleCategory(
    modifier: Modifier = Modifier,
    categoryName: String = "",
    isCategorySelected: Boolean = false,
    onCategoryClicked: (categoryName: String) -> Unit = {_ ->}
){

    val containerColor by animateColorAsState(
        targetValue = if(isCategorySelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
        label = "animate container color category chip",

    )
    val labelColor by animateColorAsState(
        targetValue = if(isCategorySelected)MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary,
        label = "animate label color category chip"
    )

    ElevatedSuggestionChip(
        modifier = modifier,
        onClick = { onCategoryClicked(categoryName) },
        elevation = ChipElevation(
            elevation =  if(isCategorySelected) 5.dp else 0.dp,
            pressedElevation = if(isCategorySelected) 5.dp else 0.dp,
            hoveredElevation = if(isCategorySelected) 5.dp else 0.dp,
            draggedElevation = if(isCategorySelected) 5.dp else 0.dp,
            focusedElevation = if(isCategorySelected) 5.dp else 0.dp,
            disabledElevation = SuggestionChipDefaults.elevatedSuggestionChipElevation().disabledElevation
        ),
        colors = SuggestionChipDefaults.elevatedSuggestionChipColors().copy(
            containerColor = containerColor,
            labelColor = labelColor
        ),
        label = {
           Text(
               text = stringResource(R.string.hashtag_category, categoryName),
               style = MaterialTheme.typography.labelLarge.copy(
                   fontWeight = FontWeight.Bold
               )
           )
        },
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
    )
}

/**
 * Add Category Chip
 *
 * @param onAddCategoryClicked triggers function when the add category icon is clicked
 * */
@Composable
fun AddCategoryChip(
    modifier: Modifier = Modifier,
    onAddCategoryClicked: () -> Unit = {}
){

    ElevatedSuggestionChip(
        modifier = modifier,
        onClick = onAddCategoryClicked,
        elevation = ChipElevation(
            elevation =  0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            draggedElevation = 0.dp,
            focusedElevation = 0.dp,
            disabledElevation = SuggestionChipDefaults.elevatedSuggestionChipElevation().disabledElevation
        ),
        colors = SuggestionChipDefaults.elevatedSuggestionChipColors().copy(
            containerColor = MaterialTheme.colorScheme.background,
            labelColor = MaterialTheme.colorScheme.primary
        ),
        label = {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = stringResource(R.string.add_category_icon)
            )
        },
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
    )
}

/**
 * Notes Staggered list
 *
 * @param notesList expects a list on notes items
 * @param onNotesItemClicked triggers function when a note item is clicked
 * @param onTodoItemClicked triggers function when a note TO-DO item is clicked
 * */
@Composable
fun NotesStaggeredList(
    modifier: Modifier = Modifier,
    notesList: List<NoteWithTodosModel> = emptyList(),
    onNotesItemClicked: (item: Note) -> Unit = { _ ->},
    onTodoItemClicked: (todoItem: NoteTodo) -> Unit = {_ -> }
){

    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 8.dp,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            notesList,
            key = {
                it.note.id
            }
        ) {
            NotesSubItem(
                modifier = Modifier,
                item = it,
                onNoteItemClicked = {
                    onNotesItemClicked(it.note)
                },
                onNoteTodoItemClicked = onTodoItemClicked
            )

        }
    }
}

/**
 * Single NotesSubItem
 *
 * @param item expects Notes Item to be displayed
 * @param onNoteItemClicked triggers function when the note item is clicked
 * @param onNoteTodoItemClicked triggers function when the note TO-DO item is clicked
 * */
@Composable
fun NotesSubItem(
    modifier: Modifier = Modifier,
    item: NoteWithTodosModel,
    onNoteItemClicked: () -> Unit,
    onNoteTodoItemClicked: (item: NoteTodo) -> Unit = {_ ->},
){


    // generate random index between 0 and 3
    val randomIndex = (0..3).random()

    // mapping background color and appropriate text color
    val itemBackgroundColorPicker = when(randomIndex) {
        0 -> MaterialTheme.colorScheme.errorContainer
        1 -> MaterialTheme.colorScheme.secondaryContainer
        2 -> MaterialTheme.colorScheme.surfaceVariant
        else -> MaterialTheme.colorScheme.tertiaryContainer
    }

    val contentColor = when(randomIndex) {
        0,1,3 -> MaterialTheme.colorScheme.background
        else -> MaterialTheme.colorScheme.onBackground
    }

    Box(
        modifier = modifier
            .background(
                color = itemBackgroundColorPicker,
                shape = rolledEdgeShape(LocalDensity.current)
            )
            .clickable(
                onClick = onNoteItemClicked
            )
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 16.dp
                )
        ) {

            // set title and note body
            Text(
                text = item.note.noteTitle ?: "",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = item.note.noteInfo ?: "",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = contentColor
                )
            )

            // display TO-DO if note contains todos

            if (item.todos.isNotEmpty()) {

                // display the todos not deleted in the note item
                val getTodos = item.todos.filter { it.deleteFlag == 0 }
                getTodos.forEach{
                    NotesTodoSubItem(
                        modifier = Modifier,
                        item = it,
                        contentColor = contentColor,
                        onNoteTodoItemClicked = onNoteTodoItemClicked
                    )
                }
            }
        }
    }

}

/**
 * Single NotesSubItem
 *
 * @param item expects Notes To-Do Item to be displayed
 * @param onNoteTodoItemClicked triggers function when the note TO-DO item is clicked
 * */
@Composable
fun NotesTodoSubItem(
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.background,
    item: NoteTodo,
    onNoteTodoItemClicked: (item: NoteTodo) -> Unit = {_ ->},
){
    Row(
        modifier = modifier
            .clickable(
                onClick = {
                    onNoteTodoItemClicked(item)
                }
            )
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val iconToShow = if (item.todoCompleted == 1)
                         painterResource(
                             id = R.drawable.ic_check_circle
                         )
                    else painterResource(
                        id = R.drawable.ic_unchecked
                    )

        Icon(
            modifier = Modifier.size(24.dp),
            painter = iconToShow,
            tint = contentColor,
            contentDescription = stringResource(
                R.string.todo_status_icon
            )
        )

        Text(
            text = item.todo ?: "",
            style = MaterialTheme.typography.bodySmall.copy(
                color = contentColor,
                fontWeight = FontWeight.Bold,
                textDecoration = if (item.todoCompleted == 1) TextDecoration.LineThrough else TextDecoration.None
            ),
            maxLines = 1,

        )


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
private fun HomeScreenPreview(){
    NotesAppTheme {
        HomeScreen()
    }
}