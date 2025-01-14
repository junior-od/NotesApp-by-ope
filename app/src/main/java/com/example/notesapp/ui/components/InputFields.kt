package com.example.notesapp.ui.components


import android.content.res.Configuration
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notesapp.R
import com.example.notesapp.ui.theme.NotesAppTheme

/**
 * Input Fields composables to be used across the app
 */

/**
 * reusable modifier to dismiss
 * keyboard on touch outside input area
 *
 * @param keyboardController expects local keyboard controller current
 * @param focusManager expects local focus manager current
 * */

fun Modifier.dismissKeyboardOnTouchOutsideInputArea(
    keyboardController : SoftwareKeyboardController?,
    focusManager: FocusManager
): Modifier = this.pointerInput(Unit){


       detectTapGestures(onTap = {
        focusManager.clearFocus() // Clear focus
        keyboardController?.hide() // Hide keyboard
    })

}

/**
 * input field for forms on the app
 *
 * @param text: expects data to be displayed in the input box
 * @param onValueChange: observes changes in the input field
 *                       and return current data in the input field
 *  @param placeholder: expects placeholder of input field
 *  @param placeholderColor: expects placeholderColor of the input field
 *  @param singleLine: expects boolean if you want input on a single line or not
 *  @param focusedContainerColor: expects container color of the input field when in focus mode
 *  @param unFocusedContainerColor: expects container color of the input field when out of focus mode
 *  @param unfocusedIndicatorColor: expects border color of the input field field when out of focus mode
 *  @param keyboardOptions: expects keyboard specific configuration to be used for the input field
 *  @param keyboardActions expects keyboard specific configurations to listen to for the input field
 * */
@Composable
fun EditInputField(
    modifier: Modifier = Modifier,
    text: String = "",
    onValueChange: (text: String) -> Unit = {_ -> },
    placeholder: String = "",
    placeholderColor: Color = MaterialTheme.colorScheme.outline,
    singleLine: Boolean = true,
    focusedContainerColor: Color = MaterialTheme.colorScheme.onPrimary,
    unFocusedContainerColor: Color = MaterialTheme.colorScheme.onPrimary,
    unfocusedIndicatorColor: Color = MaterialTheme.colorScheme.onPrimary,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    Surface(
        modifier = modifier,
        tonalElevation = 10.dp,
        shadowElevation = 20.dp
    ) {
        TextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = MaterialTheme.typography.bodyMedium,
            label = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = placeholderColor
                    )
                )
            },
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = focusedContainerColor,
                unfocusedContainerColor =  unFocusedContainerColor,
                unfocusedIndicatorColor = unfocusedIndicatorColor
            ),
            shape = MaterialTheme.shapes.small,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )
    }
}

/**
 * input field for forms on the app
 *
 * @param text: expects data to be displayed in the input box
 * @param onValueChange: observes changes in the input field
 *                       and return current data in the input field
 *  @param placeholder: expects placeholder of input field
 *  @param placeholderColor: expects placeholderColor of the input field
 *  @param singleLine: expects boolean if you want input on a single line or not
 *  @param focusedContainerColor: expects container color of the input field when in focus mode
 *  @param unFocusedContainerColor: expects container color of the input field when out of focus mode
 *  @param unfocusedIndicatorColor: expects border color of the input field field when out of focus mode
 *  @param isPasswordVisible: expects boolean to know whether to mask tha password or not
 *  @param trailingIconClicked: triggers function when trailing icon is clicked
 *  @param keyboardOptions: expects keyboard specific configuration to be used for the input field
 * */
@Composable
fun EditInputFieldPassword(
    modifier: Modifier = Modifier,
    text: String = "",
    onValueChange: (text: String) -> Unit = {_ -> },
    placeholder: String = "",
    placeholderColor: Color = MaterialTheme.colorScheme.outline,
    singleLine: Boolean = true,
    focusedContainerColor: Color = MaterialTheme.colorScheme.onPrimary,
    unFocusedContainerColor: Color = MaterialTheme.colorScheme.onPrimary,
    unfocusedIndicatorColor: Color = MaterialTheme.colorScheme.onPrimary,
    isPasswordVisible: Boolean = false,
    trailingIconClicked: () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
) {
    Surface(
        modifier = modifier,
        tonalElevation = 10.dp,
        shadowElevation = 20.dp
    ) {
        TextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = MaterialTheme.typography.bodyMedium,
            label = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = placeholderColor
                    )
                )
            },
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = focusedContainerColor,
                unfocusedContainerColor =  unFocusedContainerColor,
                unfocusedIndicatorColor = unfocusedIndicatorColor
            ),
            shape = MaterialTheme.shapes.small,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = keyboardOptions,
            trailingIcon = {
                val image = if (isPasswordVisible)
                    painterResource(id = R.drawable.ic_eye_off)
                else painterResource(id = R.drawable.ic_eye)

                IconButton(
                    modifier = Modifier.size(20.dp),
                    onClick = trailingIconClicked
                ) {
                    Icon(
                        painter = image,
                        contentDescription = if (isPasswordVisible) stringResource(R.string.hide_password)
                                                 else stringResource(R.string.show_password)
                    )
                }
            }
        )
    }
}


/**
 * EditInputFieldWitTrailingIcon
 *
 * @param text: expects data to be displayed in the input box
 * @param onValueChange: observes changes in the input field
 *                       and return current data in the input field
 *  @param placeholder: expects placeholder of input field
 *  @param placeholderColor: expects placeholderColor of the input field
 *  @param singleLine: expects boolean if you want input on a single line or not
 *  @param focusedContainerColor: expects container color of the input field when in focus mode
 *  @param unFocusedContainerColor: expects container color of the input field when out of focus mode
 *  @param unfocusedIndicatorColor: expects border color of the input field field when out of focus mode
 *  @param focusedIndicatorColor: expects border color of the input field field when in of focus mode
 *  @param keyboardOptions: expects keyboard specific configuration to be used for the input field
 *  @param trailingIcon: expects icon drawable at the end of the input field
 *  @param trailingIconClicked: triggers function when trailing icon is clicked
 *  @param showTrailingIcon: expects boolean if you want show trailing icon or not
 * */
@Composable
fun EditInputFieldWitTrailingIcon(
    modifier: Modifier = Modifier,
    text: String = "",
    onValueChange: (text: String) -> Unit = {_ -> },
    placeholder: String = "",
    placeholderColor: Color = MaterialTheme.colorScheme.outline,
    singleLine: Boolean = true,
    focusedContainerColor: Color = MaterialTheme.colorScheme.onPrimary,
    unFocusedContainerColor: Color = MaterialTheme.colorScheme.onPrimary,
    unfocusedIndicatorColor: Color = MaterialTheme.colorScheme.onPrimary,
    focusedIndicatorColor: Color = MaterialTheme.colorScheme.onPrimary,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIconClicked: () -> Unit = {},
    trailingIcon: Painter,
    showTrailingIcon: Boolean = true,
) {
    Surface(
        modifier = modifier,
        tonalElevation = 10.dp,
        shadowElevation = 20.dp
    ) {
        TextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = MaterialTheme.typography.bodyMedium,
            label = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = placeholderColor
                    )
                )
            },
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = focusedContainerColor,
                unfocusedContainerColor =  unFocusedContainerColor,
                unfocusedIndicatorColor = unfocusedIndicatorColor,
                focusedIndicatorColor = focusedIndicatorColor
            ),
            shape = MaterialTheme.shapes.small,
            keyboardOptions = keyboardOptions,
            trailingIcon = {
                if(showTrailingIcon) {
                    IconButton(
                        modifier = Modifier.size(20.dp),
                        onClick = trailingIconClicked
                    ) {
                        Icon(
                            painter = trailingIcon,
                            contentDescription = stringResource(
                                R.string.icon,
                                placeholder
                            )
                        )
                    }
                }
            }
        )
    }

}


/**
 * notes input field on the app
 *
 * @param text: expects data to be displayed in the input box
 * @param onValueChange: observes changes in the input field
 *                       and return current data in the input field
 *  @param placeholder: expects placeholder of input field
 *  @param placeholderColor: expects placeholderColor of the input field
 *  @param singleLine: expects boolean if you want input on a single line or not
 *  @param focusedContainerColor: expects container color of the input field when in focus mode
 *  @param focusedIndicatorColor: expects border color of the input field field when in of focus mode
 *  @param unFocusedContainerColor: expects container color of the input field when out of focus mode
 *  @param unfocusedIndicatorColor: expects border color of the input field field when out of focus mode
 *  @param keyboardOptions: expects keyboard specific configuration to be used for the input field
 *  @param textStyle expects style to be applied to the input text
 *  @param placeholderTextStyle expects style to be applied to the placeholder in input field
 * */
@Composable
fun NotesEditInputField(
    modifier: Modifier = Modifier,
    text: TextFieldValue = TextFieldValue(""),
    onValueChange: (text: TextFieldValue) -> Unit = {_ -> },
    placeholder: String = "",
    placeholderColor: Color = MaterialTheme.colorScheme.outline,
    singleLine: Boolean = false,
    focusedContainerColor: Color = MaterialTheme.colorScheme.background,
    unFocusedContainerColor: Color = MaterialTheme.colorScheme.background,
    unfocusedIndicatorColor: Color = MaterialTheme.colorScheme.background,
    focusedIndicatorColor: Color = MaterialTheme.colorScheme.background,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    placeholderTextStyle: TextStyle = MaterialTheme.typography.labelMedium.copy(
        color = placeholderColor
    ),
) {
    TextField(
            modifier = modifier,
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            label = {
                Text(
                    text = placeholder,
                    style = placeholderTextStyle
                )
            },
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = focusedContainerColor,
                unfocusedContainerColor =  unFocusedContainerColor,
                unfocusedIndicatorColor = unfocusedIndicatorColor,
                focusedIndicatorColor = focusedIndicatorColor
            ),
            keyboardOptions = keyboardOptions
    )

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
private fun EditInputFieldPreview(){
    NotesAppTheme {
        var inputSome by remember {
            mutableStateOf("fddfdffd")
        }
        EditInputField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = "jdksjdk",
            text = inputSome,
            onValueChange = {
                inputSome = it
            }

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
private fun EditInputFieldPasswordPreview(){
    NotesAppTheme {
        var inputSome by remember {
            mutableStateOf("fddfdffd")
        }
        var inputVisble by remember {
            mutableStateOf(false)
        }
        EditInputFieldPassword(
            modifier = Modifier.fillMaxWidth(),
            placeholder = "jdksjdk",
            text = inputSome,
            onValueChange = {
                inputSome = it
            },
            isPasswordVisible = inputVisble,
            trailingIconClicked = {
                inputVisble = !inputVisble
            }
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
private fun EditInputFieldWitTrailingIconPreview(){
    NotesAppTheme {
        var inputSome by remember {
            mutableStateOf("jk")
        }
      
        EditInputFieldWitTrailingIcon(
            modifier = Modifier.fillMaxWidth(),
            placeholder = if (inputSome.isEmpty()) { "Search for note ..." } else "",
            text = inputSome,
            onValueChange = {
                inputSome = it
            },
            trailingIconClicked = {
                inputSome = ""
            },
            trailingIcon = painterResource(id = R.drawable.ic_close)
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
private fun NotesEditInputFieldPreview(){
    NotesAppTheme {
        var inputSome by remember {
            mutableStateOf(TextFieldValue("jk"))
        }

        NotesEditInputField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = if (inputSome.text.isEmpty()) { "Search for note ..." } else "",
            text = inputSome,
            onValueChange = {
                inputSome = it
            }
        )
    }
}