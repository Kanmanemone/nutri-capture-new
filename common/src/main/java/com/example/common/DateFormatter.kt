package com.example.common

import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

object DateFormatter {
    fun formatDateForNutrientScreen(date: LocalDate): String {
        val month = date.monthValue
        val day = date.dayOfMonth
        val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN)

        return "${month}월 ${day}일 $dayOfWeek"
    }
}