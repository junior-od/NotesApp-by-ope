package com.example.notesapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notesapp.ui.theme.NotesAppTheme

/**
 * Buttons composables to be used across the app
 */
@Composable
fun NotesButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.background,
    disabledBackgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    disabledContentColor: Color = MaterialTheme.colorScheme.background,
    buttonText: String = "",
    onClick: () -> Unit = {}
) {

    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = isEnabled,
        shape = MaterialTheme.shapes.small,
        colors = ButtonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContentColor = disabledBackgroundColor,
            disabledContainerColor = disabledContentColor
        ),
        border = BorderStroke(width = 1.dp, color = backgroundColor)
    ) {

        Text(
            text = buttonText,
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
@Composable
private fun NotesButtonPreview(){
    NotesAppTheme {
        NotesButton()
    }
}