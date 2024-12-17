package com.example.nutri_capture_new

import com.google.common.truth.Truth
import org.junit.Test


class FindIndexToInsertTest {
    private data class TestCase(
        val oldItems: List<Long>, val newItem: Long, val correctInsertedIndex: Int
    )

    private val testCases = listOf(
        TestCase(
            oldItems = emptyList(),
            newItem = 1,
            correctInsertedIndex = 0
        ),
        TestCase(
            oldItems = listOf(1, 7, 13),
            newItem = 9,
            correctInsertedIndex = 2
        ),
        TestCase(
            oldItems = listOf(2, 6, 7, 8, 10),
            newItem = 11,
            correctInsertedIndex = 5
        ),
        TestCase(
            oldItems = listOf(1, 2, 3, 4),
            newItem = 2,
            correctInsertedIndex = 2
        ),
    )

    @Test
    fun findIndexToInsert_givenTestcases_returnsCorrectIndex() {
        for (testCase in testCases) {
            val result = findIndexToInsert(testCase.oldItems, testCase.newItem)
            Truth.assertThat(result).isEqualTo(testCase.correctInsertedIndex)
        }
    }
}

private fun findIndexToInsert(list: List<Long>, newItem: Long): Int {
    var min = 0
    var max = list.size - 1

    while (min <= max) {
        val mid = (min + max) / 2

        // up
        if (list[mid] < newItem) {
            min = mid + 1

            // down
        } else if (list[mid] > newItem) {
            max = mid - 1

            // equal
        } else { // list[mid] == newItem
            return mid + 1
        }
    }

    return max + 1
}