package com.example.notesapp.utils

/**
 * Unique id Generator for tables
 * */
object UniqueIdGeneratorUtils {

    /**
     * unique id generator
     * @param prefix expects id prefix
     * @param userId expects user id of user logged in
     * */
    fun uniqueIdGenerator(
        prefix: String,
        userId: String
    ): String {
        return "${prefix}_${System.currentTimeMillis()}_${userId}"
    }
}