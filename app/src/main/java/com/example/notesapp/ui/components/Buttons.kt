package com.example.notesapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.example.notesapp.R
import com.example.notesapp.ui.theme.NotesAppTheme
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.launch

/**
 * Buttons composables to be used across the app
 *
 * @param isEnabled whether the button is enabled or not
 * @param backgroundColor background color of the button
 * @param contentColor content color of the button
 * @param disabledBackgroundColor disabled background color of the button
 * @param disabledContentColor disabled content color of the button
 * @param buttonText text on the button
 * @param onClick triggers the function when the button is clicked
 * @param hasIcon whether the button has a suffix icon or not
 * @param icon suffix icon of the button
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
    onClick: () -> Unit = {},
    hasIcon: Boolean = false,
    icon: Int = R.drawable.ic_check,
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (hasIcon) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "Google Logo",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = buttonText,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

/**
 * Sign with google button
 *
 * @param buttonText expects text of the button
 * @param onGetCredentialResponse function listens for the user credential that can be used to authenticate to your app
 * @param onErrorOccurred function listens for error occurred
 * */
@Composable
fun GoogleLoginButton(
    modifier: Modifier = Modifier,
    buttonText: String = "",
    onGetCredentialResponse: (Credential) -> Unit,
    onErrorOccurred: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)

    NotesButton(
        modifier = modifier,
        buttonText = buttonText,
        onClick = {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)// set to false so users can see all available accounts not just the one used previously on the app
                .setServerClientId(context.getString(R.string.default_web_client_id))
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            coroutineScope.launch {
                try{
                    val result = credentialManager.getCredential(
                        context = context,
                        request = request
                    )
                    onGetCredentialResponse(result.credential)
                } catch (e: GetCredentialException){
                    onErrorOccurred(e.message.toString())
                }
            }
        },
        hasIcon = true,
        icon = R.drawable.ic_google_logo
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
private fun NotesButtonPreview(){
    NotesAppTheme {
        NotesButton()
    }
}