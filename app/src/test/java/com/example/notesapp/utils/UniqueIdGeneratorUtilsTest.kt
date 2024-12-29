package com.example.notesapp.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import org.junit.Rule
import org.junit.Test

/**
 * Tests on the UniqueIdGeneratorUtils helper functions
 * */

class UniqueIdGeneratorUtilsTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    /**
     * test that the uniqueIdGenerator returns valid format structure
     * */
    @Test
    fun `test uniqueIdGenerator generates valid unique ID format`() {
        // Arrange
        val prefix = "TES"
        val userId = "user123"

        // generate unique id
        val uniqueId = UniqueIdGeneratorUtils.uniqueIdGenerator(prefix, userId)

        val parts = uniqueId.split("_")

        Truth.assertThat(parts.size).isEqualTo(3)
    }

    /**
     * test that the uniqueIdGenerator returns valid format structure
     * */
    @Test
    fun `test uniqueIdGenerator generates different unique ids`()  {
        // Arrange
        val prefix = "TES"
        val userId = "user123"

        // generate unique id 1
        val uniqueId1 = UniqueIdGeneratorUtils.uniqueIdGenerator(prefix, userId)

        // sleep for a millisecond
        Thread.sleep(1)

        // generate unique id 2
        val uniqueId2 = UniqueIdGeneratorUtils.uniqueIdGenerator(prefix, userId)


        Truth.assertThat(uniqueId1).isNotEqualTo(uniqueId2)
    }
}