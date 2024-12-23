package com.example.notesapp.ui.theme

import androidx.compose.ui.unit.dp

/**
 * sizes across the app
 * */

val inputFormHeight = 50.dp

/**
 * The function calculateLineForOffset calculates the
 * line number where the cursor resides by counting newline (\n)
 * characters up to the cursor position.
 * */
fun calculateLineForOffset(text: String, offset: Int): Int {
    return text.substring(0, offset).count { it == '\n' }
}