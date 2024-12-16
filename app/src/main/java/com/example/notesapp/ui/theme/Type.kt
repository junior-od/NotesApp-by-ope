package com.example.notesapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.notesapp.R


/**
 * set the matching fontweight to font family type
 * */
private val RobotoCondensed = FontFamily(
    Font(R.font.robotocondensed_black, FontWeight.Black),
    Font(R.font.robotocondensed_extrabold, FontWeight.ExtraBold),
    Font(R.font.robotocondensed_extralight, FontWeight.ExtraLight),
    Font(R.font.robotocondensed_medium, FontWeight.Medium),
    Font(R.font.robotocondensed_thin, FontWeight.Thin),
    Font(R.font.robotocondensed_semibold, FontWeight.SemiBold),
    Font(R.font.robotocondensed_regular, FontWeight.Normal),
    Font(R.font.robotocondensed_light, FontWeight.Light),
    Font(R.font.robotocondensed_bold, FontWeight.Bold),

)

// Default Material 3 typography values
val baseline = Typography()

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = RobotoCondensed),
    displayMedium = baseline.displayMedium.copy(fontFamily = RobotoCondensed),
    displaySmall = baseline.displaySmall.copy(fontFamily = RobotoCondensed),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = RobotoCondensed),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = RobotoCondensed),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = RobotoCondensed),
    titleLarge = baseline.titleLarge.copy(fontFamily = RobotoCondensed),
    titleMedium = baseline.titleMedium.copy(fontFamily = RobotoCondensed),
    titleSmall = baseline.titleSmall.copy(fontFamily = RobotoCondensed),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = RobotoCondensed),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = RobotoCondensed),
    bodySmall = baseline.bodySmall.copy(fontFamily = RobotoCondensed),
    labelLarge = baseline.labelLarge.copy(fontFamily = RobotoCondensed),
    labelMedium = baseline.labelMedium.copy(fontFamily = RobotoCondensed),
    labelSmall = baseline.labelSmall.copy(fontFamily = RobotoCondensed),
)