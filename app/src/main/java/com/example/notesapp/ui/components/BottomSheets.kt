package com.example.notesapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notesapp.R
import com.example.notesapp.ui.theme.NotesAppTheme

/**
 * Bottom Sheet composables to be used across the app
 */

/**
 *
 * @param listOfTags expect list of tags created by user
 * @param onDismissBottomSheetRequest triggers a function when bottom sheet modal is dismissed
 * @param onTagClicked triggers a function when a tag is clicked on
 * **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagBottomSheet(
    modifier: Modifier = Modifier,
    listOfTags: List<String> = emptyList(),
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismissBottomSheetRequest: () -> Unit = {},
    onTagClicked: (tag: String) -> Unit = {}
){
    ModalBottomSheet(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onSurface,
        sheetState = sheetState,
        onDismissRequest = onDismissBottomSheetRequest
    ) {

        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.tags),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )

        LazyColumn(
            state = rememberLazyListState(),
            contentPadding = PaddingValues(16.dp)
        ) {

            items(listOfTags) {
                item ->
                    TagItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        tag = item,
                        onTagClicked = {
                            onTagClicked(item)
                        }
                    )
            }
        }

    }
}

/**
 * Single tag item
 *
 * @param tag: expects info of tag
 * @param onTagClicked triggers a function when a tag item is clicked
 * */
@Composable
fun TagItem(
    modifier: Modifier = Modifier,
    tag: String,
    onTagClicked: () -> Unit = { }
) {
    Surface(
        modifier = modifier,
        onClick = onTagClicked
    ) {

        Text(
            modifier = Modifier.fillMaxSize(),
            text = tag,
            style = MaterialTheme.typography.labelLarge
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TagBottomSheetPreview(){
    NotesAppTheme {
        val listOfTags by remember {
            mutableStateOf(listOf("Work", "Home", "School"))
        }
        TagBottomSheet(
            listOfTags = listOfTags
        )

    }
}