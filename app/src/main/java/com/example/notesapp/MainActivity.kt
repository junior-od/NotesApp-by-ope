package com.example.notesapp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.lifecycle.lifecycleScope
import com.example.notesapp.ui.theme.NotesAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var keepScreen = true
        installSplashScreen().apply {
            // check the value after every frame while
            // it is true the splash screen still be visible else its gone
            setKeepOnScreenCondition{
                // todo check if user is logged in to know what to display
               keepScreen
            }

            //to zoom out the icon
            setOnExitAnimationListener{
                 screen -> splashScreenIconAnimation(screen)

            }

            lifecycleScope.launch {
                delay(3000)
                keepScreen = false
            }
        }

        enableEdgeToEdge()
        setContent {
            NotesAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

//todo move to utils file later
/**
 * apply animation on icon
 * before splash screen goes away
 * */
private fun splashScreenIconAnimation(screem: SplashScreenViewProvider){
    val zoomX = ObjectAnimator.ofFloat(
        screem.iconView,
        View.SCALE_X,
        0.4f,
        0.0f
    )

    zoomX.interpolator = OvershootInterpolator()
    zoomX.duration = 500L
    zoomX.doOnEnd { screem.remove() }

    val zoomY = ObjectAnimator.ofFloat(
        screem.iconView,
        View.SCALE_Y,
        0.4f,
        0.0f
    )

    zoomY.interpolator = OvershootInterpolator()
    zoomY.duration = 500L
    zoomY.doOnEnd { screem.remove() }

    zoomX.start()
    zoomY.start()
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        style = MaterialTheme.typography.titleLarge
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotesAppTheme {
        Greeting("Android")
    }
}