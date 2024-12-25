package com.example.notesapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Date time utils
 */

object DateTimeUtils {
    private const val FULL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

    /**
     * get current date time in YYYY-MM-DD HH:MM:SS format
     * */
    fun getCurrentDateTimeInFullDateTimeFormat(): String {
        val date = Date() // Get current date and time
        val formatter = SimpleDateFormat(FULL_DATE_TIME_FORMAT, Locale.getDefault()) // Define the format
        return formatter.format(date) // Format the date and time
    }

    /**
     * get current date time in "24th of December 2024 at 11:35 PM" example format
     * */
    fun getFormattedCurrentDateTime(): String {
        val date = Date()
        val dayFormat = SimpleDateFormat("d", Locale.getDefault()) // Extract day
        val suffix = getDaySuffix(dayFormat.format(date).toInt()) // Get suffix
        val dateFormat = SimpleDateFormat("d'$suffix' 'of' MMMM yyyy 'at' hh:mm a", Locale.getDefault()) // Full format
        return dateFormat.format(date)
    }

    /** Helper function to determine the
     *  suffix for the day
     */
    private fun getDaySuffix(day: Int): String {
        return when {
            day in 11..13 -> "th"
            day % 10 == 1 -> "st"
            day % 10 == 2 -> "nd"
            day % 10 == 3 -> "rd"
            else -> "th"
        }
    }
}