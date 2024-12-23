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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notesapp.R
import com.example.notesapp.ui.components.EditInputField
import com.example.notesapp.ui.components.NotesButton
import com.example.notesapp.ui.components.TopNavBarWithScreenTitle
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.notesapp.ui.theme.inputFormHeight

/**
 * composable components for the Note Category screen
 * */

@Composable
fun NoteCategoryScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
    onAddNewCategoryClicked: (category: String) -> Unit = {}
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

        // form section
        CategoryForm(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(0.7f)
        )

        // bottom section
        CategoryButtonSection(
            modifier = modifier.fillMaxSize(),
            onAddCategoryClicked = onAddNewCategoryClicked
        )
    }
}

/**
 * category form
 * */
@Composable
fun CategoryForm(
    modifier: Modifier = Modifier,
){

    Column(
        modifier = modifier.verticalScroll(
            state = rememberScrollState()
        )
    ) {

        // todo move this to viewmodel
        var category by remember {
            mutableStateOf("")
        }

        Spacer(modifier = Modifier.height(30.dp))

        EditInputField(
            modifier = Modifier
                .height(inputFormHeight)
                .fillMaxWidth(),
            text = category,
            onValueChange = {
                category = it
            },
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
 * */
@Composable
fun CategoryButtonSection(
    modifier: Modifier = Modifier,
    onAddCategoryClicked: (category: String) -> Unit = { _ -> },
) {
    Box(modifier = modifier) {
        val buttonWidthModifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 16.dp)
        NotesButton(
            modifier = buttonWidthModifier.align(Alignment.BottomStart),
            onClick = {
                onAddCategoryClicked("")
            },
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