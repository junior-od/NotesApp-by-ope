package com.example.notesapp.ui.notecategory

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notesapp.R
import com.example.notesapp.ui.components.EditInputField
import com.example.notesapp.ui.components.NotesButton
import com.example.notesapp.ui.components.TopNavBarWithScreenTitle
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.notesapp.ui.theme.inputFormHeight
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

/**
 * composable components for the Note Category screen
 * */

@Composable
fun NoteCategoryScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
    onAddNewCategoryClicked: () -> Unit = {},
    noteCategoryViewModel: NoteCategoryViewModel = koinViewModel()
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(WindowInsets.ime.asPaddingValues())
            .background(
                color = MaterialTheme.colorScheme.background
            )
    ) {

        // top section
        TopNavBarWithScreenTitle(
            screenTitle = stringResource(R.string.add_category),
            onBackClicked = onBackClicked
        )

        val coroutineScope = rememberCoroutineScope()

        val noteCategoryUiState by noteCategoryViewModel.noteCategoryUiState.collectAsStateWithLifecycle()
        val noteCategory by noteCategoryViewModel.noteCategory.collectAsStateWithLifecycle()


        // observe ui state changes
        LaunchedEffect(key1 = noteCategoryUiState) {
            if (noteCategoryUiState is NoteCategoryViewModel.NoteCategoryUiState.Success) {
                onAddNewCategoryClicked()
                noteCategoryViewModel.resetNoteCategoryUiState()
            }
        }

        // form section
        CategoryForm(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(0.7f),
            category = noteCategory.name ?: "",
            onCategoryChange = {
                noteCategoryViewModel.updateCategoryName(it)
            }
        )

        // bottom section
        CategoryButtonSection(
            modifier = modifier.fillMaxSize(),
            onAddCategoryClicked = {
                coroutineScope.launch {
                    noteCategoryViewModel.saveNoteCategory()
                }
            }
        )
    }
}

/**
 * category form
 *
 * @param category expects category name to be in the input box
 * @param onCategoryChange observes category name changes in the input box
 * */
@Composable
fun CategoryForm(
    modifier: Modifier = Modifier,
    category: String = "",
    onCategoryChange: (text: String) -> Unit = {_ ->}
){

    Column(
        modifier = modifier.verticalScroll(
            state = rememberScrollState()
        )
    ) {



        Spacer(modifier = Modifier.height(30.dp))

        EditInputField(
            modifier = Modifier
                .height(inputFormHeight)
                .fillMaxWidth(),
            text = category,
            onValueChange = onCategoryChange,
            placeholder = stringResource(R.string.category),
            keyboardOptions = KeyboardOptions
                .Default.copy(
                    imeAction = ImeAction.Next
                )
        )

        Spacer(modifier = Modifier.height(16.dp))
    }

}

/**
 * category button section
 *
 * @param onAddCategoryClicked triggers function when on add category is clicked
 * */
@Composable
fun CategoryButtonSection(
    modifier: Modifier = Modifier,
    onAddCategoryClicked: () -> Unit = {  },
) {
    Box(modifier = modifier) {
        val buttonWidthModifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 16.dp)
        NotesButton(
            modifier = buttonWidthModifier.align(Alignment.BottomStart),
            onClick = onAddCategoryClicked,
            buttonText = stringResource(R.string.add_category)
        )
        Spacer(modifier = Modifier.height(8.dp))
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
private fun NoteCategoryScreenPreview(){
    NotesAppTheme {
        NoteCategoryScreen()
    }
}