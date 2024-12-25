package com.example.notesapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notesapp.R
import com.example.notesapp.ui.theme.NotesAppTheme

/**
 * Error composables to be used across the app
 */

/**
 *
 * @param message expects error message
 * @param onRemoveMessageClicked triggers the function when the close icon is clicked
 * */
@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    message: String = "",
    onRemoveMessageClicked: () -> Unit =  {}
)  {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.errorContainer
            )
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.8f),
            text = message,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onError
            )
        )

        IconButton(
            onClick = onRemoveMessageClicked
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Filled.Close,
                tint = MaterialTheme.colorScheme.onError,
                contentDescription = stringResource(R.string.remove_to_do_item_icon)
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
private fun ErrorMessagePreview(){
    NotesAppTheme {
        ErrorMessage(
            message = "Errohjfhdfj hjdf  fjdhfjdfh jfhjdhf jhfjhfjdhf jdhfjfh jdhfj dfhjdr Message"
        )
    }
}