package com.example.notesapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.notesapp.R
import com.example.notesapp.ui.theme.NotesAppTheme

/**
 * Nav bars composables to be used across the app
 */

/**
 * top nav bar with screen title and
 * optional back button
 *
 * @param screenTitle expects title of the screen
 * @param showBackIcon expects boolean if you want the nav icon or not
 * @param onBackIcon expects Image vector of nav icon
 * @param onBackClicked call back function when nav icon is clicked on
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBarWithScreenTitle(
    modifier: Modifier = Modifier,
    screenTitle: String = "",
    showBackIcon: Boolean = true,
    onBackIcon: ImageVector = Icons.Filled.KeyboardArrowLeft,
    onBackClicked: () -> Unit = {}
){

    CenterAlignedTopAppBar(
        title = {
           Text(
               text = screenTitle,
               textAlign = TextAlign.Center,
               style = MaterialTheme.typography.titleLarge.copy(
                     color = MaterialTheme.colorScheme.onSurface
                  )
               )
        },
        modifier = modifier,
        navigationIcon = {
            // if nav icon is opted in
            if (showBackIcon) {
                IconButton(
                    onClick = onBackClicked,
                ) {
                    Icon(
                        imageVector = onBackIcon,
                        contentDescription = stringResource(
                            R.string.navigation_back_icon
                        )
                    )
                }
            }
        }
    )
}

/**
 * HomeTop NavBar WithScreenTitledIcon
 *
 * @param screenTitle expects composable title of the screen
 * @param showBackIcon expects boolean if you want the nav icon or not
 * @param onBackIcon expects Image vector of nav icon
 * @param onBackClicked call back function when nav icon is clicked on
 * @param actions the actions displayed at the end of the top app bar.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBarWithScreenTitledIcon(
    modifier: Modifier = Modifier,
    screenTitle:  @Composable () -> Unit = {},
    showBackIcon: Boolean = true,
    onBackIcon: ImageVector = Icons.Filled.KeyboardArrowLeft,
    onBackClicked: () -> Unit = {},
    actions: @Composable() (RowScope.() -> Unit) = {}
){

    TopAppBar(
        title = screenTitle,
        modifier = modifier,
        navigationIcon = {
            // if nav icon is opted in
            if (showBackIcon) {
                IconButton(
                    onClick = onBackClicked,
                ) {
                    Icon(
                        imageVector = onBackIcon,
                        contentDescription = stringResource(
                            R.string.navigation_back_icon
                        )
                    )
                }
            }
        },
        actions = actions
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
private fun TopNavBarWithScreenTitlePreview(){
    NotesAppTheme {
        TopNavBarWithScreenTitle(
            screenTitle = "Log in"
        )
    }
}