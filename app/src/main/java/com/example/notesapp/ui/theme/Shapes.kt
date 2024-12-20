package com.example.notesapp.ui.theme

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

/**
 * Shapes and borders across the app
 * */
val shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

/**
 * convert dp to px
 * */
fun dpToPx(dp: Int, density: Density): Float {
    return with(density) { dp.dp.toPx() }
}

/**
 * bottom left slashed effect shape
 * */
fun rolledEdgeShape(density: Density) = GenericShape { size, _ ->
    val cornerRadius = dpToPx(24, density) // Adjust for the "rolled" effect
    val foldSize = dpToPx(20, density)

    moveTo(0f, cornerRadius)
    cubicTo(0f, 0f, 0f, 0f, cornerRadius, 0f)
    lineTo(size.width - cornerRadius, 0f)
    cubicTo(size.width, 0f, size.width, 0f, size.width, cornerRadius)
    lineTo(size.width, size.height - cornerRadius)
    cubicTo(size.width, size.height, size.width, size.height, size.width - cornerRadius, size.height)
    lineTo(foldSize, size.height)
    lineTo(0f, size.height - foldSize)
    close()
}