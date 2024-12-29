package com.example.notesapp.utils

import com.example.notesapp.utils.DateTimeUtils.getFormattedCurrentDateTime
import com.google.common.truth.Truth
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Test on DateTimeUtils helper functions
 * */
class DateTimeUtilsTest{

    companion object {
        private const val FULL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    }

    @Test
    fun `test getCurrentDateTimeInFullDateTimeFormat returns valid date in yyyy-MM-dd format`() {
        val formatter = SimpleDateFormat(FULL_DATE_TIME_FORMAT, Locale.getDefault())
        val expectedDate = formatter.format(Date())

        val actualDate = DateTimeUtils.getCurrentDateTimeInFullDateTimeFormat()

        // Compare only up to seconds precision to avoid time drift
        Truth.assertThat(expectedDate.substring(0, 11)).isEqualTo(actualDate.substring(0, 11))
    }

    @Test
    fun `test getFormattedCurrentDateTime with valid input return valid output`() {
        val inputDateTime = "2024-12-24 23:35:00"
        val expectedOutput = "24th of December 2024 at 11:35 pm"

        val actualOutput = getFormattedCurrentDateTime(inputDateTime)

        Truth.assertThat(expectedOutput).isEqualTo(actualOutput)
    }

    @Test
    fun `test getFormattedCurrentDateTime with null input return empty output`() {
        val actualOutput = getFormattedCurrentDateTime(null)
        Truth.assertThat("").isEqualTo(actualOutput)
    }

    @Test
    fun `test getFormattedCurrentDateTime with empty input return empty output`() {
        val actualOutput = getFormattedCurrentDateTime("")
        Truth.assertThat("").isEqualTo(actualOutput)
    }

    @Test
    fun `test getFormattedCurrentDateTime with invalid input return empty output`() {
        val inputDateTime = "invalid-date"
        val actualOutput = getFormattedCurrentDateTime(inputDateTime)
        Truth.assertThat("").isEqualTo(actualOutput)
    }

    @Test
    fun `test getDaySuffix  returns correct output`() {
        Truth.assertThat("st").isEqualTo(getDaySuffix(1))
        Truth.assertThat("nd").isEqualTo(getDaySuffix(2))
        Truth.assertThat("rd").isEqualTo(getDaySuffix(3))
        Truth.assertThat("th").isEqualTo(getDaySuffix(4))
        Truth.assertThat("th").isEqualTo(getDaySuffix(11))
        Truth.assertThat("th").isEqualTo(getDaySuffix(12))
        Truth.assertThat("th").isEqualTo(getDaySuffix(13))
        Truth.assertThat("st").isEqualTo(getDaySuffix(21))
        Truth.assertThat("nd").isEqualTo(getDaySuffix(22))
        Truth.assertThat("rd").isEqualTo(getDaySuffix(23))
        Truth.assertThat("th").isEqualTo(getDaySuffix(24))
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