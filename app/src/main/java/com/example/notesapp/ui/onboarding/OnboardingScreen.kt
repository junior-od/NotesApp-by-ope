package com.example.notesapp.ui.onboarding

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notesapp.R
import com.example.notesapp.ui.components.NotesButton
import com.example.notesapp.ui.theme.NotesAppTheme
import org.koin.androidx.compose.koinViewModel

/**
 * composable components for the onboarding screen
 * */


/**
 * onboarding root
 * @param onSignInClicked function triggered when sign in button is clicked
 * @param onCreateAccountClicked function triggered when create account button is clicked
 * */
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onCreateAccountClicked: () -> Unit = {},
    onSignInClicked: () -> Unit = {},
    onboardingViewModel: OnboardingViewModel = koinViewModel()
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = buildAnnotatedString {
                append(stringResource(R.string.note_ta))
                withStyle(
                    SpanStyle(color = MaterialTheme.colorScheme.onSurface)
                ) {
                    append(stringResource(R.string.king_app))
                }
            },
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )


        val onboardingFeaturesList by onboardingViewModel.onboardingFeatures.collectAsStateWithLifecycle()

        // carousel section
        OnboardingCarousel(
            modifier = Modifier.fillMaxHeight(0.8f),
            itemsToDisplay = onboardingFeaturesList
        )

        // auth section
        AuthSection(
            onCreateAccountClicked = onCreateAccountClicked,
            onSignInClicked = onSignInClicked
        )
    }

}

/**
 * Onboarding carousel with indicators
 *
 * @param itemsToDisplay : list of items
 * to be displayed in the carousel
 * */
@Composable
fun OnboardingCarousel(
    modifier: Modifier = Modifier,
    itemsToDisplay: List<Int> = emptyList()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val listState = rememberLazyListState(0)
        val flingBehavior = rememberSnapFlingBehavior(
            lazyListState = listState
        )

        // current index
        val currentIndex by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex
            }
        }

        // Carousel with (LazyRow)
        OnboardingPictureTextLazyRow(
            itemsToDisplay = itemsToDisplay,
            listState = listState,
            flingBehavior = flingBehavior
        )

        // carousel indicators with (LazyRow)
        CarouselIndicators(
            listSize = itemsToDisplay,
            currentIndex = currentIndex
        )

    }
}

/**
 * OnboardingPictureTextLazyRow to display list of
 * onboarding picture and text items in a row
 * @param itemsToDisplay :  onboarding item info as a list
 * @param listState : lazy list state to remember where it is on the screen
 * @param flingBehavior: for fling gesture functionality
 */
@Composable
fun OnboardingPictureTextLazyRow(
    modifier: Modifier = Modifier,
    itemsToDisplay: List<Int> = emptyList(),
    listState: LazyListState = rememberLazyListState(0),
    flingBehavior: FlingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
) {

    // get screen width
    val screenWidth = (
            LocalConfiguration
                .current
                .screenWidthDp / 1.08
            ).dp

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentPadding = PaddingValues(
            end = 16.dp
        ),
        state = listState,
        flingBehavior = flingBehavior
    ) {
        items(
            itemsToDisplay
        ) { item ->
            OnboardingPictureText(
                modifier = Modifier
                    .size(screenWidth)
                    .padding(8.dp),
                item = stringResource(id = item)
            )
        }
    }

}

/**
 * single onboarding
 * picture and text item
 * @param item : single onboarding item info
 */
@Composable
fun OnboardingPictureText(
    modifier: Modifier = Modifier,
    item: String = ""
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter =  painterResource(id = R.drawable.notes_logo),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(0.6f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = item,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

    }

}

/**
 * Carousel indicators for carousel slider
 *
 * @param listSize:  carousel list
 * @param currentIndex: current visible item on carousel
 * */
@Composable
fun CarouselIndicators(
    modifier: Modifier = Modifier,
    listSize: List<Int> = emptyList(),
    currentIndex: Int = 0,
) {

    // Indicators
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier

    ) {
        listSize.indices.forEach { index ->
            Indicator(isActive = index == currentIndex)
        }
    }


}

/**
 * single indicator item for carousel
 * */
@Composable
fun Indicator(isActive: Boolean) {
    // animate color change between active and inactive states
    val color by animateColorAsState(
        targetValue = if (isActive) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.surfaceContainer,
        label = "animate indicator color"
    )
    Box(
        modifier = Modifier
            .size(20.dp)
            .padding(5.dp)
            .background(
                color,
                shape = CircleShape
            )
    )
}


/**
 * Auth section to display create account or sign in
 *
 * @param onSignInClicked triggers a function when log in button is clicked
 * @param onCreateAccountClicked triggers a function when create account button is clicked
 * */
@Composable
fun AuthSection(
    modifier: Modifier = Modifier,
    onCreateAccountClicked: () -> Unit = {},
    onSignInClicked: () -> Unit = {}
){
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        val buttonWidthModifier = Modifier.fillMaxWidth(1f)
        NotesButton(
            modifier = buttonWidthModifier,
            onClick = onCreateAccountClicked,
            buttonText = stringResource(R.string.create_account)
        )
        Spacer(modifier = Modifier.height(8.dp))
        NotesButton(
            modifier = buttonWidthModifier,
            onClick = onSignInClicked,
            buttonText = stringResource(R.string.log_in),
            backgroundColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}


@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO, apiLevel = 31,
    showSystemUi = true, showBackground = true
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true, showSystemUi = true, apiLevel = 31,
)
@Composable
private fun OnboardingScreenPreview() {
    NotesAppTheme {
        OnboardingScreen()
    }
}

