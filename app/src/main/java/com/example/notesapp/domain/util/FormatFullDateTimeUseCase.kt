package com.example.notesapp.domain.util

import com.example.notesapp.utils.DateTimeUtils

/**
 * FormatFullDateTime UseCase from YYYY-MM-DD HH:MM:SS format to "d 'of' MMMM yyyy 'at' hh:mm a"
 * */
class FormatFullDateTimeUseCase {

    /**
     * @param date expects date time string as YYYY-MM-DD HH:MM:SS format
     * */
    operator fun invoke(date: String?): String{
        return DateTimeUtils.getFormattedCurrentDateTime(date)
    }
}